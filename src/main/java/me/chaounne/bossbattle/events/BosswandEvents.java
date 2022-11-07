package me.chaounne.bossbattle.events;

import me.chaounne.bossbattle.items.ItemManager;
import me.chaounne.bossbattle.schedulers.Cooldown;
import me.chaounne.bossbattle.schedulers.RepeatingTask;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class BosswandEvents implements Listener {

    private BossBar bossBar = Bukkit.createBossBar("Zombie Master", BarColor.RED, BarStyle.SOLID);

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event){

        Player player = event.getPlayer();
        ItemStack wand = ItemManager.bossWand;

        if(player.getInventory().getItemInMainHand().equals(wand)){

            //zombie master
            if(event.getBlock().getType() == Material.SLIME_BLOCK){
                double hp = 200.0;

                Material helmet = Material.LEATHER_HELMET;
                ItemStack helmIT = new ItemStack(helmet);

                LeatherArmorMeta helmMeta = (LeatherArmorMeta) helmIT.getItemMeta();
                helmMeta.setColor(Color.AQUA);
                helmMeta.addEnchant(Enchantment.LUCK, 1, false);
                helmIT.setItemMeta(helmMeta);

                player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN, 1, 1);

                event.setCancelled(true);
                event.getBlock().setType(org.bukkit.Material.AIR);

                Zombie zombieMaster = event.getBlock().getWorld().spawn(event.getBlock().getLocation().add(0.5,0,0.5), Zombie.class);
                zombieMaster.setCustomName("Zombie Master");
                zombieMaster.setCustomNameVisible(true);
                zombieMaster.getEquipment().setHelmet(helmIT);
                zombieMaster.getEquipment().setHelmetDropChance(0);
                zombieMaster.getEquipment().setItemInMainHand(new ItemStack(Material.STICK));
                zombieMaster.getEquipment().setItemInMainHandDropChance(0);

                zombieMaster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20.0);
                zombieMaster.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(100.0);
                zombieMaster.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.3);

                zombieMaster.setMaxHealth(hp);
                zombieMaster.setHealth(hp);
                zombieMaster.setBaby(false);


                zombieMaster.setMetadata("ZombieMaster", new FixedMetadataValue(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("BossBattle")), "ZombieMaster"));

                player.getWorld().setTime(18000);

                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1, 1);
                    bossBar.addPlayer(p);
                    bossBar.setProgress(1);
                    bossBar.setVisible(true);
                    p.sendMessage("§c§lBOSS SPAWNED");
                }
            }
            //others
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Zombie && e.getDamager() instanceof Player) {
            Zombie zombie = (Zombie) e.getEntity();
            Player player = (Player) e.getDamager();
            System.out.println(zombie.getHealth());
            if (zombie.hasMetadata("ZombieMaster")) {
                bossBar.setProgress((zombie.getHealth() / zombie.getMaxHealth()) - (e.getFinalDamage() / zombie.getMaxHealth()));

                RepeatingTask task = new RepeatingTask(0, 20, () -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, 1, 1);
                    player.addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.HUNGER, 100, 1));
                    zombie.getWorld().strikeLightningEffect(zombie.getLocation());
                    for (int i = 0; i < 10; i++) {
                        zombie.getWorld().spawnEntity(zombie.getLocation(), org.bukkit.entity.EntityType.ZOMBIE);
                    }
                });

                task.start();
                Cooldown cd = new Cooldown(60, task::cancel);
                cd.run();
            }
        }
    }

    @EventHandler
    public void onZombieKilled(EntityDeathEvent e){
        if(e.getEntity() instanceof Zombie){
            Zombie zombie = (Zombie) e.getEntity();
            if(zombie.hasMetadata("ZombieMaster")){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 1, 1);
                    bossBar.removePlayer(p);
                    p.sendMessage("§c§lBOSS KILLED");
                }
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.DIAMOND, 10));
            }
        }
    }
}
