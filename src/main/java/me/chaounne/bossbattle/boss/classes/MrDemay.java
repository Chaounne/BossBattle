package me.chaounne.bossbattle.boss.classes;

import me.chaounne.bossbattle.boss.Boss;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MrDemay extends Boss {

    public MrDemay(Block block) {
        super(block);
        this.spawn();
    }

    @Override
    public void spawn() {
        double hp = 300.0;
        World world = spawnBlock.getWorld();

        Villager MrDemay = world.spawn(spawnBlock.getLocation().add(0.5, 0, 0.5), Villager.class);
        MrDemay.setCustomName("Mr.Demay");
        MrDemay.setCustomNameVisible(true);

        MrDemay.
    }

    @Override
    public void runOnDamageEvent(EntityDamageByEntityEvent e) {

    }

    @Override
    public void runOnDeathEvent(EntityDeathEvent e) {

    }
}
