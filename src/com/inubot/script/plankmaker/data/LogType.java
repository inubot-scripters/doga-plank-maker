package com.inubot.script.plankmaker.data;

public enum LogType {

  REGULAR("Logs", 100),
  OAK("Oak logs", 250),
  TEAK("Teak logs", 500),
  MAHOGANY("Mahogany logs", 1500);

  private final String name;
  private final int sawmillCost;

  LogType(String name, int sawmillCost) {
    this.name = name;
    this.sawmillCost = sawmillCost;
  }

  @Override
  public String toString() {
    return name;
  }

  public int getSawmillCost() {
    return sawmillCost;
  }
}
