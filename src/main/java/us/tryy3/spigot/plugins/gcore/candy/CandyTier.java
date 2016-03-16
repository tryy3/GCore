package us.tryy3.spigot.plugins.gcore.candy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class CandyTier {
    private int delay;
    private int amount;
    private List<ItemStack> items;
    private String name;
    private Material block;
    private String invName;
    private String key;

    public CandyTier(List<ItemStack> items, int delay, int amount, String name, Material block, String invName, String key) {
        this.items = items;
        this.delay = delay;
        this.amount = amount;
        this.name = name;
        this.block = block;
        this.invName = invName;
        this.key = key;
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

    public String getName() {
        return name;
    }

    public String getInvName() {
        return invName;
    }

    public Material getBlock() {
        return block;
    }

    public String getKey() {
        return key;
    }
}
