package us.tryy3.spigot.plugins.gcore.candy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.tryy3.spigot.plugins.gcore.utils.Hologram;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class GeneratorBlock {
    private Hologram hologram;
    private Location location;
    private Inventory inventory;

    public GeneratorBlock(Hologram hologram, Location location, Inventory inventory) {
        this.location = location;
        this.hologram = hologram;
        this.inventory = inventory;
    }

    public Location getLocation() {
        return location;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int size() {
        int i = 0;
        for (ItemStack stack : inventory) {
            if (stack == null || stack.getType() == Material.AIR) continue;

            i+=stack.getAmount();
        }
        return i;
    }
}
