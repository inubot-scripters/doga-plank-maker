package com.inubot.script.plankmaker.task;

import com.google.inject.Inject;
import com.inubot.script.plankmaker.Domain;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

//make this blocking and add it as the first task, so it runs only this until the user fills in the UI
@TaskDescriptor(name = "Waiting for config", blocking = true)
public class WaitTask extends Task {

  private final Domain domain;

  @Inject
  public WaitTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public boolean execute() {
    return !domain.isConfigured();
  }
}
