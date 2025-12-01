package com.inubot.script.plankmaker.task;

import com.google.inject.Inject;
import com.inubot.script.plankmaker.Config;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Waiting for config", blocking = true)
public class WaitTask extends Task {

  private final Config config;

  @Inject
  public WaitTask(Config config) {
    this.config = config;
  }

  @Override
  public boolean execute() {
    return !config.isBound();
  }
}
