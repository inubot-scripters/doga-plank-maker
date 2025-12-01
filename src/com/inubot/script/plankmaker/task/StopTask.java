package com.inubot.script.plankmaker.task;

import com.google.inject.Inject;
import com.inubot.script.plankmaker.Config;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Stopping", stoppable = true)
public class StopTask extends Task {

  private final Config config;

  @Inject
  public StopTask(Config config) {
    this.config = config;
  }

  @Override
  public boolean execute() {
    return config.isStopping();
  }
}
