package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.Location;

/**
 * Created by tryy3 on 2016-03-13.
 */
public class Landzone {
    private String name;
    private Location location;

    public Landzone(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
