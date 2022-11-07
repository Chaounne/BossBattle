package me.chaounne.bossbattle.schedulers;

import me.chaounne.bossbattle.BossBattle;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class RepeatingTask {

    private BukkitTask task;

    private Runnable runnable;
    private int delay;
    private int period;

     public RepeatingTask(int delay, int period, Runnable runnable) {
        this.runnable = runnable;
        this.delay = delay;
        this.period = period;
    }

    public void start() {
        task = Bukkit.getScheduler().runTaskTimer(BossBattle.getInstance(), runnable, delay * 20L, period * 20L);
    }

    public void cancel() {
        this.task.cancel();
    }

}
