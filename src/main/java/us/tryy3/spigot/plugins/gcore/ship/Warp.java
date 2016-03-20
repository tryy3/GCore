package us.tryy3.spigot.plugins.gcore.ship;

import java.util.List;

/**
 * Created by tryy3 on 2016-03-13.
 */
public class Warp {
    private String name;
    private Landzone from;
    private Landzone to;

    public Warp(String name, Landzone from, Landzone to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public Landzone getFrom() {
        return from;
    }

    public Landzone getTo() {
        return to;
    }
}
