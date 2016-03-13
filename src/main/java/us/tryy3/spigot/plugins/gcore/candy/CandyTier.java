package us.tryy3.spigot.plugins.gcore.candy;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class CandyTier extends BukkitRunnable {
    private List<GeneratorBlock> locations;
    private int delay;
    private int count;
    private int amount;
    private List<ItemStack> items;

    public CandyTier(List<GeneratorBlock> locations, List<ItemStack> items, int delay, int count) {
        this.locations = locations;
        this.items = items;
        this.delay = delay;
        this.count = count;
        this.amount = count;
    }

    @Override
    public void run() {
        if (count > 0) {
            count--;
            return;
        }

        for (GeneratorBlock block : locations) {
            if (block.size() >= amount) continue;

            for (ItemStack stack : items) {
                block.getInventory().addItem(stack);
            }

            block.getHologram().setLine(1, ChatColor.translateAlternateColorCodes('&', "&7[&c"+block.size()+"/"+amount+"&7]"));
            block.getHologram().despawn();
            block.getHologram().spawn();
        }
        count = amount;
    }

    public boolean isGenerator(GeneratorBlock location) {
        return locations.contains(location);
    }

    public void addLocation(GeneratorBlock location) {
        locations.add(location);
        location.getHologram().spawn();
    }

    public void removeLocation(GeneratorBlock location) {
        locations.remove(location);
        location.getHologram().despawn();
    }

    public List<GeneratorBlock> getLocations() {
        return locations;
    }

    public int getAmount() {
        return amount;
    }

    public int getDelay() {
        return delay;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
