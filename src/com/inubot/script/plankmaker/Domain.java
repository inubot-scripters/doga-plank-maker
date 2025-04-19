package com.inubot.script.plankmaker;

import com.google.inject.Singleton;
import com.inubot.script.plankmaker.data.LogType;
import com.inubot.script.plankmaker.data.Servant;
import org.rspeer.event.Service;
import org.rspeer.event.Subscribe;
import org.rspeer.game.script.event.ScriptConfigEvent;

@Singleton
public class Domain implements Service {

  private Servant servant;
  private LogType logType;
  private boolean usingRunePouch;
  private boolean stopping;

  private boolean configured;

  @Subscribe
  public void notify(ScriptConfigEvent event) {
    this.servant = Servant.DEMON_BUTLER; //event.getSource().get("Servant"); //other servants are simply not fast enough
    this.logType = event.getSource().get("Log");
    this.usingRunePouch = event.getSource().getBoolean("Rune Pouch");

    this.configured = true;
  }

  public Servant getServant() {
    return servant;
  }

  public LogType getLogType() {
    return logType;
  }

  public boolean isUsingRunePouch() {
    return usingRunePouch;
  }

  public boolean isStopping() {
    return stopping;
  }

  public void setStopping(boolean stopping) {
    this.stopping = stopping;
  }

  public boolean isConfigured() {
    return configured;
  }

  @Override
  public void onSubscribe() {

  }

  @Override
  public void onUnsubscribe() {

  }
}