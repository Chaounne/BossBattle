package me.chaounne.bossbattle.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemStackBuilder {

    ItemStack is;

    public ItemStackBuilder(ItemStack is) {
        this.is = is;
    }
    public ItemStackBuilder(Material type) {
        this.is = new ItemStack(type);
    }
    public ItemStackBuilder(Material type, int amount) {
        this.is = new ItemStack(type, amount);
    }
    public ItemStackBuilder(Material type, int amount, short damage) {
        this.is = new ItemStack(type, amount, damage);
    }

    public ItemStack getItemStack() {
        return this.is;
    }


    public ItemStackBuilder setName(String name) {
        ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(name);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder setLore(String lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        this.is.setItemMeta(im);
        return this;
    }
    public ItemStackBuilder setLore(List<String> lores) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(lores);
        this.is.setItemMeta(im);
        return this;
    }
    public ItemStackBuilder setLore(String[] lores) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(Arrays.asList(lores));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder addEnchant(Enchantment enchant, int level) {
        ItemMeta im = this.is.getItemMeta();
        im.addEnchant(enchant, level, true);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder addFlag(ItemFlag flag) {
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(flag);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder setItemMeta(ItemMeta meta) {
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setAmout(int amount) {
        this.is.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta im = this.is.getItemMeta();
        im.setUnbreakable(unbreakable);
        this.is.setItemMeta(im);
        return this;
    }

}
