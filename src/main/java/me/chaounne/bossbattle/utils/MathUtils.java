package me.chaounne.bossbattle.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MathUtils {

    public static Vector goTo(Location from, Location target) {
        Vector vector = target.toVector().subtract(from.toVector());
        return vector.normalize();
    }

    public static Vector goTo(Location from, Location target, double speed) {
        Vector vector = target.toVector().subtract(from.toVector());
        return vector.normalize().multiply(speed);
    }

}
