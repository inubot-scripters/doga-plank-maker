package com.inubot.script.plankmaker;

import com.google.inject.Singleton;
import com.inubot.script.plankmaker.data.LogType;
import com.inubot.script.plankmaker.data.Servant;
import org.rspeer.game.script.model.ConfigModel;
import org.rspeer.game.script.model.ui.schema.checkbox.CheckBoxComponent;
import org.rspeer.game.script.model.ui.schema.selector.SelectorComponent;

@Singleton
public class Config extends ConfigModel {

  @SelectorComponent(name = "Log", key = "log", type = LogType.class)
  private LogType logType;

  @CheckBoxComponent(name = "Rune pouch", key = "rune_pouch")
  private boolean runePouch;

  private boolean stopping;

  public LogType getLogType() {
    return logType;
  }

  public boolean isRunePouch() {
    return runePouch;
  }

  public Servant getServant() {
    return Servant.DEMON_BUTLER;
  }

  public boolean isStopping() {
    return stopping;
  }

  public void setStopping(boolean stopping) {
    this.stopping = stopping;
  }
}
