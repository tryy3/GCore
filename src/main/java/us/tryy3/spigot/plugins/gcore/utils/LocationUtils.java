package us.tryy3.spigot.plugins.gcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by tryy3 on 2016-03-13.
 */
public class LocationUtils {
    public static String LocationToString(Location location) {
        String loc = location.getWorld() + ";";
        loc += location.getX() + ";";
        loc += location.getY() + ";";
        loc += location.getZ();
        return loc;
    }

    public static Location LocationFromString(String location) {
        String[] split = location.split(";");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }

    public static boolean CompareLocations(Location l1, Location l2) {
        return (l1.getWorld().getName().equalsIgnoreCase(l2.getWorld().getName())) &&
                (l1.getBlockX()==l2.getBlockX()) &&
                (l1.getBlockY()==l2.getBlockY()) &&
                (l1.getBlockZ()==l2.getBlockZ());
    }
}