package me.chaounne.bossbattle.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerUtils {

    public static Player getClosestPlayer(Entity entity) {
        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;
        for (Player player : entity.getWorld().getPlayers()) {
            double distance = player.getLocation().distance(entity.getLocation());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPlayer = player;
            }
        }
        return closestPlayer;
    }

}
