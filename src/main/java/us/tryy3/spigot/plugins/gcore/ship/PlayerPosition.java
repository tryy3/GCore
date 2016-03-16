package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.Location;

import java.util.UUID;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class PlayerPosition {
    private UUID uuid;
    private Location location;

    public PlayerPosition(UUID uuid, Location location) {
        this.uuid = uuid;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public UUID getUuid() {
        return uuid;
    }
}
