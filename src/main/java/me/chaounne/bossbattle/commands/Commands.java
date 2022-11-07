package me.chaounne.bossbattle.commands;

import me.chaounne.bossbattle.items.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        //bosswand
        if(command.getName().equalsIgnoreCase("bosswand")){
            player.sendMessage("You have received the boss wand");
            ItemStack wand = ItemManager.bossWand;
            player.getInventory().addItem(wand);
            }



        return true;
    }
}
