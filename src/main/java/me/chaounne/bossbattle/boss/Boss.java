package me.chaounne.bossbattle.boss;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public abstract class Boss {

    protected Block spawnBlock;
    protected Entity entity;
    protected BossBar bossBar;
    protected double hp;
    protected String name;

    protected Boss(Block block, double hp, String name) {
        this.spawnBlock = block;
        this.hp = hp;
        this.name = name;
    }

    public Entity getEntity() {
        return entity;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public abstract void spawn();

    public abstract void runOnDamageEvent(EntityDamageByEntityEvent e);

    public abstract void runOnDeathEvent(EntityDeathEvent e);

    public void createBossBar(String title, BarColor color, BarStyle style, Sound sound, int volume, int pitch) {
        this.bossBar = Bukkit.createBossBar(title, color, style);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), sound, volume, pitch);
            bossBar.addPlayer(p);
            bossBar.setProgress(1);
            bossBar.setVisible(true);
            p.sendMessage("§c§l" + name + " has spawned!");
        }
    }

    public void updateBossBar(double damage) {
        double health = bossBar.getProgress() * hp;
        if(health - damage >= 0) {
            bossBar.setProgress(bossBar.getProgress() - damage / hp);
        }else {
            bossBar.setProgress(0);
        }
    }
    public void clearBossBar() {
        this.bossBar.removeAll();
    }
}
