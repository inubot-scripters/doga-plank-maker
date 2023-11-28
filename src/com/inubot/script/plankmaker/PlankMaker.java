package com.inubot.script.plankmaker;

import com.inubot.script.plankmaker.data.LogType;
import com.inubot.script.plankmaker.data.Servant;
import com.inubot.script.plankmaker.task.ToggleRunTask;
import com.inubot.script.plankmaker.task.WaitTask;
import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.StopWatch;
import org.rspeer.event.ScriptService;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskScript;
import org.rspeer.game.script.meta.ScriptMeta;
import org.rspeer.game.script.meta.paint.PaintBinding;
import org.rspeer.game.script.meta.paint.PaintScheme;
import org.rspeer.game.script.meta.ui.ScriptOption;
import org.rspeer.game.script.meta.ui.ScriptUI;

import java.util.function.Supplier;

@ScriptMeta(
    name = "Rimmington Oak Chopper",
    regions = -3,
    paint = PaintScheme.class,
    desc = "Chops oak trees and uses a house servant to bank the logs or turn them into planks. Great for irons!",
    developer = "Doga",
    version = 1.01
)
@ScriptUI({
    @ScriptOption(name = "Servant", type = Servant.class),
    @ScriptOption(name = "Log", type = LogType.class),
})
@ScriptService(Domain.class)
public class PlankMaker extends TaskScript {

  @PaintBinding("Runtime")
  private final StopWatch runtime = StopWatch.start();

  @PaintBinding("Last task")
  private final Supplier<String> task = () -> manager.getLastTaskName();

  @Override
  public Class<? extends Task>[] tasks() {
    return ArrayUtils.getTypeSafeArray(
        WaitTask.class,
        ToggleRunTask.class
    );
  }
}
