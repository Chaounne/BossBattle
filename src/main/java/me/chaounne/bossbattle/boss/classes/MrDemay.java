package me.chaounne.bossbattle.boss.classes;

import me.chaounne.bossbattle.boss.Boss;
import me.chaounne.bossbattle.utils.MathUtils;
import me.chaounne.bossbattle.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MrDemay extends Boss {

    private ArmorStand armorStand;
    public MrDemay(Block block) {
        super(block, 300, "Mr.Demay");
        this.spawn();
    }

    @Override
    public void spawn() {
        World world = spawnBlock.getWorld();

        Villager MrDemay = world.spawn(spawnBlock.getLocation().add(0.5, 0, 0.5), Villager.class);
        MrDemay.setCustomName("Mr.Demay");
        MrDemay.setCustomNameVisible(true);

        MrDemay.setMaxHealth(hp);
        MrDemay.setHealth(hp);

        armorStand = world.spawn(spawnBlock.getLocation(), ArmorStand.class);
        armorStand.setPassenger(MrDemay);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);

        for(Player player : world.getPlayers()) {
            player.sendMessage("Mr.Demay has spawned!");
        }

        entity = MrDemay;
        Location from = entity.getLocation();
        Location to = PlayerUtils.getClosestPlayer(entity).getLocation();
        armorStand.setVelocity(MathUtils.goTo(from, to));
        //armorStand.setVelocity(MathUtils.goTo(to, from, 0.05));

        super.createBossBar("Laurent Demay", BarColor.RED, BarStyle.SOLID, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
    }

    @Override
    public void runOnDamageEvent(EntityDamageByEntityEvent e) {
        super.updateBossBar(e.getFinalDamage());
    }

    @Override
    public void runOnDeathEvent(EntityDeathEvent e) {
        super.clearBossBar();
        e.getDrops().clear();
        e.setDroppedExp(0);
        armorStand.remove();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§c§lMr.Demay has been killed!");
        }
    }
}
