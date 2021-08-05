package eu.sequence.tick;

import eu.sequence.Sequence;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

@Getter
public class TickProcessor implements Runnable {

    private int tick;
    private BukkitTask task;

    public void setup() {
        if (task == null) {
            task = Bukkit.getScheduler().runTaskTimer(Sequence.getInstance().getPlugin(), this, 0L, 1L);
        }
    }

    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public void run() {
        ++tick;
    }
}
