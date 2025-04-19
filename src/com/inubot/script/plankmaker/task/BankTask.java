package com.inubot.script.plankmaker.task;

import com.google.inject.Inject;
import com.inubot.script.plankmaker.Domain;
import com.inubot.script.plankmaker.data.LogType;
import com.inubot.script.plankmaker.data.Servant;
import org.rspeer.game.adapter.component.inventory.Bank;
import org.rspeer.game.adapter.component.inventory.Inventory;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Interfaces;
import org.rspeer.game.component.Inventories;
import org.rspeer.game.component.tdi.Magic;
import org.rspeer.game.component.tdi.Spell;
import org.rspeer.game.config.item.entry.builder.FuzzyItemEntryBuilder;
import org.rspeer.game.config.item.entry.builder.ItemEntryBuilder;
import org.rspeer.game.config.item.loadout.BackpackLoadout;
import org.rspeer.game.house.House;
import org.rspeer.game.scene.SceneObjects;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Banking")
public class BankTask extends Task {

  private final Domain domain;

  @Inject
  public BankTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public boolean execute() {
    if (Inventories.backpack().contains(iq -> iq.names(domain.getLogType().toString()).results())) {
      return false;
    }

    if (Interfaces.isSubActive(71)) {
      return false;
    }

    if (House.isInside()) {
      Magic.cast(Spell.Modern.CAMELOT_TELEPORT);
      sleepWhile(House::isInside, 5);
      return true;
    }

    SceneObject bank = SceneObjects.query()
        .names("Bank chest")
        .results()
        .nearest();
    if (bank == null) {
      return false;
    }

    BackpackLoadout loadout = buildLoadout();
    if (loadout.isBagged()) {
      if (Bank.isOpen()) {
        Interfaces.closeSubs();
      }

      return false;
    }

    if (!Bank.isOpen()) {
      Bank.open();
      return true;
    }

    loadout.withdraw(Inventories.bank());
    return true;
  }

  private BackpackLoadout buildLoadout() {
    BackpackLoadout loadout = new BackpackLoadout("plankmaking");
    if (domain.isUsingRunePouch()) {
      loadout.add(new FuzzyItemEntryBuilder()
          .key("rune pouch")
          .quantity(1)
          .stackable(false)
          .build());
    } else {
      loadout.add(new ItemEntryBuilder()
          .key("Law rune")
          .quantity(2)
          .stackable(true)
          .build());
      loadout.add(new ItemEntryBuilder()
          .key("Earth rune")
          .quantity(1)
          .stackable(true)
          .build());
      /*loadout.add(new ItemEntryBuilder()
          .key("Air rune")
          .quantity(6)
          .stackable(true)
          .build());*/ //already provided by staff
    }

    //27 instead of 28 because we still need to add coins, but we calculate that off planks
    int planks = Math.min(27 - loadout.getAllocated(), domain.getServant().getInventoryCapacity());
    LogType type = domain.getLogType();
    Servant slave = domain.getServant();
    int cashmoney = (planks * type.getSawmillCost()) + slave.getWage();

    loadout.add(new ItemEntryBuilder()
        .key("Coins")
        .quantity(cashmoney)
        .stackable(true)
        .build());
    loadout.add(new ItemEntryBuilder()
        .key(type.toString())
        .quantity(planks)
        .stackable(false)
        .build());

    loadout.setOutOfItemListener(e -> {
      if (!e.contained(Inventories.backpack())) {
        domain.setStopping(true);
      }
    });

    return loadout;
  }
}
