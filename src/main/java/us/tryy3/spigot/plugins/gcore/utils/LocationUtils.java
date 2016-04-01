package us.tryy3.spigot.plugins.gcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by tryy3 on 2016-03-13.
 */
public class LocationUtils {
    public static String LocationToString(Location location) {
        String loc = location.getWorld().getName() + ";";
        loc += location.getX() + ";";
        loc += location.getY() + ";";
        loc += location.getZ();
        return loc;
    }

    public static Location LocationFromString(String location) {
        String[] split = location.split(";");

        return LocationFromArray(split[0],split[1],split[2],split[3]);
    }

    public static Location LocationFromArray(String world, String x, String y, String z) {
        return new Location(Bukkit.getWorld(world), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
    }

    public static boolean CompareLocations(Location l1, Location l2) {
        return (l1.getWorld().getName().equalsIgnoreCase(l2.getWorld().getName())) &&
                (l1.getBlockX()==l2.getBlockX()) &&
                (l1.getBlockY()==l2.getBlockY()) &&
                (l1.getBlockZ()==l2.getBlockZ());
    }
}