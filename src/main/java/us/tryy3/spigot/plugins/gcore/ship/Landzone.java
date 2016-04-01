package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by tryy3 on 2016-03-13.
 */
public class Landzone {
    private String name;
    private Location location;
    private String command;
    private List<String> ship;
    private Direction[] direction;

    public Landzone(String name, Location location, List<String> ship, Direction[] direction, String command) {
        this.name = name;
        this.location = location;
        this.ship = ship;
        this.command = command;
        this.direction = direction;
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

    public List<String> getShip() {
        return ship;
    }

    public String getShip(Player player) {
        for (String s : ship) {
            if (player.hasPermission("gcore.ship."+s)) return s;
        }
        return null;
    }

    public Direction getDirection() {
        Random random = new Random();
        int i = random.nextInt(direction.length);
        return direction[i];
    }

    public Direction[] getDirections() {
        return direction;
    }
}
