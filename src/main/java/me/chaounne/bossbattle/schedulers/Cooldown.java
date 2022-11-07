package me.chaounne.bossbattle.schedulers;

import me.chaounne.bossbattle.BossBattle;
import org.bukkit.Bukkit;

public class Cooldown {

    private Runnable task;
    private int delay;

    public Cooldown(int delay, Runnable task) {
        this.task = task;
        this.delay = delay;
    }

    public void run() {
        Bukkit.getScheduler().runTaskLater(BossBattle.getInstance(), task, delay * 20L);
    }

}
