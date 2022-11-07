package me.chaounne.bossbattle.events;

import me.chaounne.bossbattle.boss.ActualBoss;
import me.chaounne.bossbattle.boss.Boss;
import me.chaounne.bossbattle.boss.BossEnum;
import me.chaounne.bossbattle.items.ItemManager;
import me.chaounne.bossbattle.schedulers.Cooldown;
import me.chaounne.bossbattle.schedulers.RepeatingTask;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
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

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class BosswandEvents implements Listener {

    private BossBar bossBar = Bukkit.createBossBar("Zombie Master", BarColor.RED, BarStyle.SOLID);

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {

        Player player = event.getPlayer();
        ItemStack wand = ItemManager.bossWand;

        if(player.getInventory().getItemInMainHand().equals(wand)){

            if(ActualBoss.getBoss() == null) {
                try {
                    Boss boss = Objects.requireNonNull(BossEnum.getBossEnumByMaterial(
                                    event.getBlock().getType())).getBossClass()
                            .getDeclaredConstructor(Block.class).newInstance(event.getBlock());
                    ActualBoss.setBoss(boss);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Boss boss = ActualBoss.getBoss();

        if(e.getEntity() == boss.getEntity() && e.getDamager() instanceof Player) {
            boss.runOnDamageEvent(e);
        }
    }

    @EventHandler
    public void onZombieKilled(EntityDeathEvent e) {
        Boss boss = ActualBoss.getBoss();

        if(e.getEntity() == boss.getEntity()) {
            boss.runOnDeathEvent(e);
        }
    }
}
