package us.tryy3.spigot.plugins.gcore.ship;

import java.util.List;

/**
 * Created by tryy3 on 2016-03-13.
 */
public class Warp {
    private Landzone from;
    private Landzone to;
    private Direction direction;
    private List<String> ships;

    public Warp(Landzone from, Landzone to, Direction direction, List<String> ships) {
        this.from = from;
        this.to = to;
        this.direction = direction;
        this.ships = ships;
    }

    public Landzone getFrom() {
        return from;
    }

    public Landzone getTo() {
        return to;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<String> getShips() {
        return ships;
    }
}
