package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.Location;

/**
 * Created by tryy3 on 2016-03-13.
 */
public class Landzone {
    private String name;
    private Location location;
    private String command;

    public Landzone(String name, Location location) {
        this(name, location, null);
    }
    public Landzone(String name, Location location, String command) {
        this.name = name;
        this.location = location;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getCommand() {
        return command;
    }

    public boolean hasCommand() {
        return command != null;
    }
}
