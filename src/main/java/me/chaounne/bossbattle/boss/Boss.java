package me.chaounne.bossbattle.boss;

import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public abstract class Boss {

    protected Block spawnBlock;
    protected Entity entity;
    protected BossBar bossBar;

    protected Boss(Block block) {
        this.spawnBlock = block;
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
}
