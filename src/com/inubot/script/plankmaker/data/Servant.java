package com.inubot.script.plankmaker.data;

public enum Servant {

  BUTLER("Butler", 20, 5000),
  COOK("Cook", 16, 3000),
  DEMON_BUTLER("Demon butler", 26, 10000);

  private final String name;
  private final int inventoryCapacity;
  private final int wage;

  Servant(String name, int inventoryCapacity, int wage) {
    this.name = name;
    this.inventoryCapacity = inventoryCapacity;
    this.wage = wage;
  }

  @Override
  public String toString() {
    return name;
  }

  public int getInventoryCapacity() {
    return inventoryCapacity;
  }

  public int getWage() {
    return wage;
  }
}
