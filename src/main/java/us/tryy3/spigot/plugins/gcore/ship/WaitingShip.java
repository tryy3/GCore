package us.tryy3.spigot.plugins.gcore.ship;

import org.bukkit.entity.ArmorStand;

import java.util.UUID;

/**
 * Created by tryy3 on 2016-03-16.
 */
public class WaitingShip {
    private Landzone landzone;
    private UUID uuid;
    private ArmorStand stand;
    private Direction direction;

    public WaitingShip(Landzone landzone, UUID uuid, ArmorStand stand, Direction direction) {
        this.landzone = landzone;
        this.uuid = uuid;
        this.stand = stand;
        this.direction = direction;
    }

    public ArmorStand getStand() {
        return stand;
    }

    public Direction getDirection() {
        return direction;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Landzone getLandzone() {
        return landzone;
    }
}
