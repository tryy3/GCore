package us.tryy3.spigot.plugins.gcore.ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tryy3 on 2016-03-12.
 */

// TODO: Make this nicer, use a list or hashmap instead.
public enum Direction {
    DOWN(0),
    UP(1),
    SOUTH(2),
    WEST(3),
    NORTH(4),
    EAST(5),
    RANDOM(6);

    int dir = 0;

    Direction(int dir) {
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public static Direction getDir(int dir) {
        switch (dir) {
            case 0:
                return DOWN;
            case 1:
                return UP;
            case 2:
                return SOUTH;
            case 3:
                return WEST;
            case 4:
                return NORTH;
            case 5:
                return EAST;
            case 6:
                return RANDOM;
            default:
                return null;
        }
    }

    public static Direction getDir(String dir) {
        switch (dir.toUpperCase()) {
            case "DOWN":
                return DOWN;
            case "UP":
                return UP;
            case "SOUTH":
                return SOUTH;
            case "WEST":
                return WEST;
            case "NORTH":
                return NORTH;
            case "EAST":
                return EAST;
            case "RANDOM":
                return RANDOM;
            default:
                return null;
        }
    }

    public static boolean isDirection(String dir) {
        dir = dir.toLowerCase();
        return dir.equalsIgnoreCase("down") ||
                dir.equalsIgnoreCase("up") ||
                dir.equalsIgnoreCase("south") ||
                dir.equalsIgnoreCase("west") ||
                dir.equalsIgnoreCase("north") ||
                dir.equalsIgnoreCase("east") ||
                dir.equalsIgnoreCase("random");
    }

    public static List<String> getDirs() {
        List<String> dir = new ArrayList<>();
        dir.add("Down");
        dir.add("Up");
        dir.add("South");
        dir.add("West");
        dir.add("North");
        dir.add("East");
        dir.add("Random");
        return dir;
    }
}
