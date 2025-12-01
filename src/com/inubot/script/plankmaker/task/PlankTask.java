package com.inubot.script.plankmaker.task;

import com.google.inject.Inject;
import com.inubot.script.plankmaker.Config;
import org.rspeer.game.adapter.component.inventory.Bank;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.component.*;
import org.rspeer.game.component.tdi.Magic;
import org.rspeer.game.component.tdi.Spell;
import org.rspeer.game.house.House;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Planking")
public class PlankTask extends Task {

  private final Config config;

  @Inject
  public PlankTask(Config config) {
    this.config = config;
  }

  @Override
  public boolean execute() {
    if (!Inventories.backpack().contains(iq -> iq.names(config.getLogType().toString()).results())) {
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
      sleepUntil(() -> !House.isInside(), 5);
      return true;
    }

    Npc butler = Npcs.query().names(config.getServant().toString()).results().first();
    if (butler != null && butler.distance() > 3) {
      if (!HouseOptions.isOpen()) {
        HouseOptions.open();
        return true;
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
        iq -> iq.names(config.getLogType().toString()).results().first(),
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

    EnterInput.initiate(config.getServant().getInventoryCapacity());

    Dialog.Quick.process(
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5), //Continue
        new InterfaceAddress(InterfaceComposite.CHAT_OPTIONS, 1).subComponent(1), //Yes, pay to convert them
        new InterfaceAddress(InterfaceComposite.NPC_DIALOG, 5) //Continue
    );
  }
}
