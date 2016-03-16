package us.tryy3.spigot.plugins.gcore.candy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;
import us.tryy3.spigot.plugins.gcore.utils.Hologram;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class GeneratorBlock {
    private Hologram hologram;
    private Location location;
    private Inventory inventory;
    private int count;
    private CandyTier tier;

    public GeneratorBlock(Location location, CandyTier tier) {
        this(location, tier, Bukkit.createInventory(null, 54, tier.getInvName()));
    }
    public GeneratorBlock(Location location,CandyTier tier, Inventory inventory) {
        this(location, tier, inventory, tier.getAmount());
    }
    public GeneratorBlock(Location location, CandyTier tier, Inventory inventory, int count) {
        this.location = location;
        this.inventory = inventory;
        this.hologram = createHologram();
        this.tier = tier;
        this.count = count;
    }

    private Hologram createHologram() {
        List<String> strings = ChatUtils.StringsToArray("&d&lCandy Generator","&7[&c"+size()+" / "+getTier().getAmount()+"&7]","&7[&5"+getTier().getName()+"&7]");

        Hologram hologram = new Hologram(location,strings,0.2);
        hologram.spawn();
        return hologram;
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

    public void count() {
        count--;
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        this.count = tier.getDelay();
    }

    public CandyTier getTier() {
        return tier;
    }

    public void updateHologram() {
        hologram.setLine(1, ChatColor.translateAlternateColorCodes('&', "&7[&c"+size()+"/"+getTier().getAmount()+"&7]"));
        hologram.despawn();
        hologram.spawn();
    }
}
