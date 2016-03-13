package us.tryy3.spigot.plugins.gcore.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class Hologram {
    private double height = 0;
    private Location location = null;
    private Location originalLocation;
    private List<String> lines = new ArrayList<>();
    private ArrayList<Entity> holograms = new ArrayList<>();

    public Hologram(Location location, List<String> lines, double height) {
        this.lines = lines;
        this.originalLocation = location;
        this.location = this.originalLocation.clone();
        this.height = height;
    }

    public void spawn() {
        this.location.setY((this.location.getY()+this.height)-1.25);
        for (int i = lines.size(); i > 0; i--) {
            ArmorStand hologram = (ArmorStand) this.location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            hologram.setCustomName(lines.get(i-1));
            hologram.setCustomNameVisible(true);
            hologram.setGravity(false);
            hologram.setVisible(false);
            this.location.setY(this.location.getY()+0.25);
            this.holograms.add(hologram);
        }

        this.location = this.originalLocation.clone();
    }

    public void despawn() {
        for (Entity entity : holograms) {
            entity.remove();
        }
        this.holograms = new ArrayList<>();
    }

    public void setLine(int number, String line) {
        this.lines.set(number, line);
    }
    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
