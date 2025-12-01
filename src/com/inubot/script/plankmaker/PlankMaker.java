package com.inubot.script.plankmaker;

import com.inubot.script.plankmaker.task.*;
import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.StopWatch;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskScript;
import org.rspeer.game.script.meta.ScriptMeta;
import org.rspeer.game.script.meta.paint.PaintBinding;
import org.rspeer.game.script.meta.paint.PaintScheme;

import java.util.function.Supplier;

@ScriptMeta(
    name = "Plank Maker",
    paint = PaintScheme.class,
    desc = "Makes planks on PvP world using the PoH method. Great for irons!",
    developer = "Doga",
    version = 1.02
)
public class PlankMaker extends TaskScript {

  @PaintBinding("Runtime")
  private final StopWatch runtime = StopWatch.start();

  @PaintBinding("Task")
  private final Supplier<String> task = () -> manager.getLastTaskExecutionSequence();

  @Override
  public Class<? extends Task>[] tasks() {
    return ArrayUtils.getTypeSafeArray(
        WaitTask.class,
        StopTask.class,
        ToggleRunTask.class,
        BankTask.class,
        PlankTask.class
    );
  }
}
