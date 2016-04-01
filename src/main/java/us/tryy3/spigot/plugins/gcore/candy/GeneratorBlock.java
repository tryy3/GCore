package us.tryy3.spigot.plugins.gcore.candy;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class GeneratorBlock {
    private Hologram hologram;
    private Location location;
    private Inventory inventory;
    private int count;
    private CandyTier tier;
    private GCore core;

    public GeneratorBlock(GCore core, Location location, CandyTier tier) {
        this(core, location, tier, Bukkit.createInventory(null, 54, ChatUtils.format(tier.getInvName())));
    }
    public GeneratorBlock(GCore core, Location location, CandyTier tier, Inventory inventory) {
        this(core, location, tier, inventory, tier.getDelay());
    }
    public GeneratorBlock(GCore core, Location location, CandyTier tier, Inventory inventory, int count) {
        this.core = core;
        this.location = location;
        this.inventory = inventory;
        this.tier = tier;
        this.count = count;
        createHologram();
    }

    private void createHologram() {
        Location newloc = location.clone();
        newloc.add(0.5, 2.1, 0.5);
        this.hologram = HologramsAPI.createHologram(core, newloc);
        updateHologram();
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

    public void delete() {
        hologram.delete();
        inventory.clear();
    }

    public void updateHologram() {
        YamlConfiguration config = core.getMainConfig().getConfig();
        hologram.clearLines();
        hologram.appendTextLine(ChatUtils.format(config.getString("Messages.Candy-Title")));
        hologram.appendTextLine(ChatUtils.format(config.getString("Messages.Candy-Items").replace("%total%",String.valueOf(getTier().getAmount())).replace("%amount%",String.valueOf(size()))));
        hologram.appendTextLine(ChatUtils.format(config.getString("Messages.Candy-Tier").replace("%tier%",getTier().getName())));
    }
}
