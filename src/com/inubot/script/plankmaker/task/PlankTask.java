package com.inubot.script.plankmaker.task;

import com.google.inject.Inject;
import com.inubot.script.plankmaker.Domain;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.component.inventory.Bank;
import org.rspeer.game.adapter.component.inventory.Inventory;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.component.*;
import org.rspeer.game.component.tdi.Magic;
import org.rspeer.game.component.tdi.Spell;
import org.rspeer.game.house.House;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Banking")
public class PlankTask extends Task {

  private final Domain domain;

  @Inject
  public PlankTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public boolean execute() {
    if (!Inventories.backpack().contains(iq -> iq.names(domain.getLogType().toString()).results())) {
      return false;
    }

    if (Interfaces.isSubActive(71)) {
      return false;
    }

    if (Bank.isOpen()) {
      Interfaces.closeSubs();
      return true;
    }

    if (!House.isInside()) {
      Magic.cast(Spell.Modern.TELEPORT_TO_HOUSE);
      sleepWhile(House::isInside, 5);
      return true;
    }

    Npc butler = Npcs.query().names(domain.getServant().toString()).results().first();
    //if (butler == null || butler.distance() > 3) {
    if (butler != null && butler.distance() > 3) {
      if (!HouseOptions.isOpen()) {
        //i fixed this in the api but needs a bot release
        InterfaceComponent component = Interfaces.query(InterfaceComposite.SETTINGS_TAB)
            .actions("View House Options")
            .results()
            .first();
        return component != null && component.interact("View House Options");
      }

      return HouseOptions.callButler();
    }


    if (Dialog.canContinue()) {
      if (Dialog.getText().contains("pay me") || Dialog.getText().contains("desirest")) {
        wage();
      } else {
        sawmill();
      }

      return true;
    }

    Inventories.backpack().use(
        iq -> iq.names(domain.getLogType().toString()).results().first(),
        butler
    );
    sleepUntil(Dialog::canContinue, 2);
    return true;
  }

  private void wage() {
    Dialog.Quick.process(
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5), //Continue
        new InterfaceAddress(InterfaceComposite.CHAT_OPTIONS, 1).subComponent(1) //Pay wage
    );
  }

  private void sawmill() {
    Dialog.Quick.process(
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5), //Continue
        new InterfaceAddress(InterfaceComposite.CHAT_OPTIONS, 1).subComponent(1) //Sawmill
    );

    EnterInput.initiate(domain.getServant().getInventoryCapacity());

    Dialog.Quick.process(
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5), //Continue
        new InterfaceAddress(InterfaceComposite.CHAT_OPTIONS, 1).subComponent(1), //Yes, pay to convert them
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5) //Continue
    );
  }
}
