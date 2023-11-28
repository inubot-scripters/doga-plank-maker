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

  private boolean configured;

  @Subscribe
  public void notify(ScriptConfigEvent event) {
    this.servant = event.getSource().get("Servant");
    this.logType = event.getSource().get("Log");

    this.configured = true;
  }

  @Override
  public void onSubscribe() {

  }

  @Override
  public void onUnsubscribe() {

  }

  public Servant getServant() {
    return servant;
  }

  public LogType getLogType() {
    return logType;
  }

  public boolean isConfigured() {
    return configured;
  }
}