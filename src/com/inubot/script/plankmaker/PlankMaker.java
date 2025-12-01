package com.inubot.script.plankmaker;

import com.inubot.script.plankmaker.commons.RSPeerPaintScheme;
import com.inubot.script.plankmaker.task.*;
import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.StopWatch;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskScript;
import org.rspeer.game.script.meta.ScriptMeta;
import org.rspeer.game.script.meta.paint.PaintBinding;

import java.util.function.Supplier;

@ScriptMeta(
    name = "RSPeer Plank Maker",
    paint = RSPeerPaintScheme.class,
    model = Config.class,
    desc = "Start on PvP world. Requires a Demon Butler",
    developer = "Doga",
    version = 1.0
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
