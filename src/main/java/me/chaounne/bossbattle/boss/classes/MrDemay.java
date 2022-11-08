package me.chaounne.bossbattle.boss.classes;

import me.chaounne.bossbattle.boss.Boss;
import me.chaounne.bossbattle.schedulers.Cooldown;
import me.chaounne.bossbattle.utils.MathUtils;
import me.chaounne.bossbattle.utils.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MrDemay extends Boss {

    private Bat bat;
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

        MrDemay.setMaxHealth(hp);
        MrDemay.setHealth(hp);

        bat = world.spawn(spawnBlock.getLocation(), Bat.class);
        bat.setPassenger(MrDemay);
        bat.setInvulnerable(true);
        bat.setInvulnerable(true);
        bat.setAI(false);

        for(Player player : world.getPlayers()) {
            player.sendMessage("Mr.Demay has spawned!");
        }

        entity = MrDemay;

            Location from = entity.getLocation();
            Location to = PlayerUtils.getClosestPlayer(entity).getLocation();
            bat.setVelocity(MathUtils.goTo(from, to, 0.05));
            bat.setVelocity(MathUtils.goTo(to, from, 0.05));

    }

    @Override
    public void runOnDamageEvent(EntityDamageByEntityEvent e) {

    }

    @Override
    public void runOnDeathEvent(EntityDeathEvent e) {

    }
}
