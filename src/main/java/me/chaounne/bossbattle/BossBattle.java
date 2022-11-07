package me.chaounne.bossbattle;

import me.chaounne.bossbattle.commands.Commands;
import me.chaounne.bossbattle.events.BosswandEvents;
import me.chaounne.bossbattle.items.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class BossBattle extends JavaPlugin implements Listener {

    private static BossBattle instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Commands com = new Commands();
        ItemManager.init();
        getCommand("bosswand").setExecutor(com);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new BosswandEvents(), this);
        instance = this;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BossBattle getInstance() {
        return instance;
    }
}
