package us.tryy3.spigot.plugins.gcore.ship;

/**
 * Created by tryy3 on 2016-03-12.
 */
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
            default:
                return RANDOM;
        }
    }
}
