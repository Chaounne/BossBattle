package me.chaounne.bossbattle.items;

import me.chaounne.bossbattle.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
    public static ItemStack bossWand;

    public static void init(){
        createBossWand();
    }

    private static void createBossWand() {
        ItemStackBuilder itemStackBuilder = new ItemStackBuilder(Material.DIAMOND_AXE);
        ItemStack item = itemStackBuilder.setName("Boss Wand").addEnchant(Enchantment.LUCK, 1).addFlag(ItemFlag.HIDE_ENCHANTS).setUnbreakable(true).getItemStack();
        bossWand = item;
    }
}
