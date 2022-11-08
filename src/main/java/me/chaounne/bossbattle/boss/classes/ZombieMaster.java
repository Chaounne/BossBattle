package me.chaounne.bossbattle.boss.classes;

import me.chaounne.bossbattle.boss.Boss;
import me.chaounne.bossbattle.schedulers.Cooldown;
import me.chaounne.bossbattle.schedulers.RepeatingTask;
import me.chaounne.bossbattle.utils.ItemStackBuilder;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class ZombieMaster extends Boss {

    private RepeatingTask task;

    public ZombieMaster(Block block) {
        super(block);
        this.spawn();
    }

    @Override
    public void spawn() {
        double hp = 200.0;
        World world = spawnBlock.getWorld();


        ItemStackBuilder itemStackBuilder = new ItemStackBuilder(Material.LEATHER_HELMET);
        LeatherArmorMeta helmMeta = (LeatherArmorMeta) itemStackBuilder.getItemStack().getItemMeta();
        helmMeta.setColor(Color.AQUA);
        ItemStack helmIT = itemStackBuilder.addEnchant(Enchantment.LUCK, 1).setItemMeta(helmMeta).getItemStack();

        Zombie zombieMaster = world.spawn(spawnBlock.getLocation().add(0.5,0,0.5), Zombie.class);
        zombieMaster.setCustomName("Zombie Master");
        zombieMaster.setCustomNameVisible(true);
        Objects.requireNonNull(zombieMaster.getEquipment()).setHelmet(helmIT);
        zombieMaster.getEquipment().setHelmetDropChance(0);
        zombieMaster.getEquipment().setItemInMainHand(new ItemStack(Material.STICK));
        zombieMaster.getEquipment().setItemInMainHandDropChance(0);

        Objects.requireNonNull(zombieMaster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(20.0);
        Objects.requireNonNull(zombieMaster.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(100.0);
        Objects.requireNonNull(zombieMaster.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(0.3);

        zombieMaster.setMaxHealth(hp);
        zombieMaster.setHealth(hp);
        zombieMaster.setBaby(false);

        zombieMaster.setMetadata("ZombieMaster", new FixedMetadataValue(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("BossBattle")), "ZombieMaster"));

        super.entity = zombieMaster;

        world.setTime(18000);
        world.playSound(entity.getLocation(), Sound.EVENT_RAID_HORN, 1, 1);

        bossBar = Bukkit.createBossBar("Zombie Master", BarColor.RED, BarStyle.SOLID);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1, 1);
            bossBar.addPlayer(p);
            bossBar.setProgress(1);
            bossBar.setVisible(true);
            p.sendMessage("§c§lBOSS SPAWNED");
        }
    }

    @Override
    public void runOnDamageEvent(EntityDamageByEntityEvent e) {
        Zombie zombie = (Zombie) getEntity();
        Player player = (Player) e.getDamager();
        System.out.println(zombie.getHealth());
        bossBar.setProgress((zombie.getHealth() / zombie.getMaxHealth()) - (e.getFinalDamage() / zombie.getMaxHealth()));

        task = new RepeatingTask(0, 20, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1, 1);
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.HUNGER, 100, 1));
            zombie.getWorld().strikeLightningEffect(zombie.getLocation());
            for (int i = 0; i < 10; i++) {
                zombie.getWorld().spawnEntity(zombie.getLocation(), org.bukkit.entity.EntityType.ZOMBIE);
            }
        });

        task.start();
    }

    @Override
    public void runOnDeathEvent(EntityDeathEvent e) {
        task.cancel();
        Zombie zombie = (Zombie) getEntity();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 1, 1);
            bossBar.removePlayer(p);
            bossBar.setProgress(0.0);
            bossBar.setVisible(false);
            p.sendMessage("§c§lBOSS KILLED");
        }
        e.getDrops().clear();
        e.getDrops().add(new ItemStack(Material.DIAMOND, 10));
    }
}
