package me.chaounne.bossbattle.boss.classes;

import me.chaounne.bossbattle.boss.Boss;
import me.chaounne.bossbattle.schedulers.Cooldown;
import me.chaounne.bossbattle.schedulers.RepeatingTask;
import me.chaounne.bossbattle.utils.ItemStackBuilder;
import me.chaounne.bossbattle.utils.MathUtils;
import me.chaounne.bossbattle.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

import static java.lang.Thread.sleep;

public class DeadKing extends Boss {

    private RepeatingTask task;


    public DeadKing(Block block) {
        super(block);
        this.spawn();
    }

    @Override
    public void spawn() {
        double hp = 585.0;
        final int[] i = {0};
        Cooldown cd;
        PotionEffect fireRes = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 1);
        ItemStackBuilder itemStackBuilder = new ItemStackBuilder(Material.GOLDEN_HELMET);

        ItemStack crown = itemStackBuilder.addEnchant(Enchantment.PROTECTION_PROJECTILE,4).setUnbreakable(true).getItemStack();

        itemStackBuilder = new ItemStackBuilder(Material.IRON_SWORD);

        ItemStack sword = itemStackBuilder.addEnchant(Enchantment.DAMAGE_ALL, 2).addEnchant(Enchantment.KNOCKBACK,1).setUnbreakable(true).getItemStack();

        World world = spawnBlock.getWorld();

        Skeleton King = world.spawn(spawnBlock.getLocation().add(0.5, 0, 0.5), Skeleton.class);
        Cooldown cd2 = new Cooldown(5, () -> {
            King.setAI(true);
        });

        King.setCustomName("Dead King");
        King.setCustomNameVisible(true);

        King.getEquipment().setHelmet(crown);
        King.getEquipment().setHelmetDropChance(0);
        King.getEquipment().setItemInMainHand(sword);
        King.getEquipment().setItemInMainHandDropChance(0);

        Objects.requireNonNull(King.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(100.0);
        Objects.requireNonNull(King.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(0.7);

        King.setMaxHealth(hp);
        King.setHealth(hp);

        entity = King;

        bossBar = Bukkit.createBossBar("§rDe§kad §rK§ki§rng", BarColor.RED, BarStyle.SOLID);

        for(Player player : world.getPlayers()) {
            bossBar.addPlayer(player);
            player.sendMessage("Dead King has spawned!");
        }

        bossBar.setProgress(1.0);
        bossBar.setVisible(true);

        task = new RepeatingTask(30, 25, () -> {
            Location loc = entity.getLocation();
            while (i[0] <10) {
                King.teleport(loc.add(0, 1, 0));
                i[0]++;
            }
            King.setAI(false);
            cd2.run();
            for(Entity entity : King.getNearbyEntities(10, 7, 10)) {
                if (entity instanceof Player) {
                        Player player = (Player) entity;
                        player.getWorld().strikeLightning(player.getLocation());
                        player.sendMessage("§cBEHOLD MY POWER MORTALS!");
                    }
                }
            i[0] = 0;
        });


        task.start();
        System.out.println("Task started");
        cd = new Cooldown(120, () -> {
            task.cancel();
            System.out.println("Task stopped");
        });
        cd.run();

    }
// Changement de forme si pv < 10% ??
    @Override
    public void runOnDamageEvent(EntityDamageByEntityEvent e) {
        Skeleton King = (Skeleton) entity;
        Player p = (Player) e.getDamager();
        if(King.getHealth() - e.getDamage() >= 0){
            bossBar.setProgress((King.getHealth() / King.getMaxHealth()) - (e.getDamage() / King.getMaxHealth()));
        }
        else{
            bossBar.setProgress(0);
        }
    }

    @Override
    public void runOnDeathEvent(EntityDeathEvent e) {
        task.cancel();
        for(Player player : e.getEntity().getWorld().getPlayers()) {
            bossBar.removePlayer(player);
            bossBar.setProgress(0.0);
            bossBar.setVisible(false);
            player.sendMessage("§cThe Dead King has been slain!");
        }
    }

}
