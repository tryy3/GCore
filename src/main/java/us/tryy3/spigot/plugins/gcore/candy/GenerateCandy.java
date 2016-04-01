package us.tryy3.spigot.plugins.gcore.candy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.commands.SubCommand;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;
import us.tryy3.spigot.plugins.gcore.utils.InventoryToBase64;
import us.tryy3.spigot.plugins.gcore.utils.InventoryUtils;
import us.tryy3.spigot.plugins.gcore.utils.LocationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dennis.planting on 3/14/2016.
 */
public class GenerateCandy extends BukkitRunnable {
    private GCore core;
    private List<GeneratorBlock> blocks = new ArrayList<>();
    private Map<String, CandyTier> tiers = new HashMap<>();

    public GenerateCandy(GCore core) {
        this.core = core;

        load();
    }

    public void load() {
        YamlConfiguration tierConfig = core.getMainConfig().getConfig();
        if (tierConfig.contains("Generators")) {
            for (Map.Entry<String, Object> tiers : tierConfig.getConfigurationSection("Generators").getValues(false).entrySet()) {
                List<ItemStack> items = new ArrayList<>();
                for (String s : tierConfig.getStringList("Generators." + tiers.getKey() + ".Items")) {
                    String[] split = s.split(";");

                    items.add(new ItemStack(Material.getMaterial(split[0]), Integer.parseInt(split[1])));
                }

                int delay = tierConfig.getInt("Generators." + tiers.getKey() + ".Seconds");
                int amount = tierConfig.getInt("Generators." + tiers.getKey() + ".Max");
                String name = tierConfig.getString("Generators." + tiers.getKey() + ".Name");
                String invName = tierConfig.getString("Generators." + tiers.getKey() + ".Inventory-Name");
                Material block = Material.getMaterial(tierConfig.getString("Generators." + tiers.getKey() + ".Block"));

                this.tiers.put(tiers.getKey(), new CandyTier(items, delay, amount, name, block, invName, tiers.getKey()));
            }
        }

        if (core.getCache().getConfig().contains("Generators")) {
            for (String s : core.getCache().getConfig().getStringList("Generators")) {
                String[] split = s.split(";");
                CandyTier tier = tiers.get(split[0]);
                Location location = LocationUtils.LocationFromArray(split[1], split[2], split[3], split[4]);

                if (location.getBlock() == null || location.getBlock().getType() != tier.getBlock()) {
                    System.out.println("Found a generator block in config that doesn't exists in the world, skipping ("+s+")");
                    return;
                }

                Inventory inventory = Bukkit.createInventory(null, 54, ChatUtils.format(tier.getInvName()));
                InventoryToBase64.setInventory(inventory, split[5]);

                GeneratorBlock block = new GeneratorBlock(core, location, tier, inventory, Integer.parseInt(split[0]));
                blocks.add(block);
            }
        }
    }

    public void save() {
        YamlConfiguration cache = core.getCache().getConfig();

        List<String> output = new ArrayList<>();

        for (GeneratorBlock block : blocks) {
            String s = "";
            s+=block.getTier().getKey()+";";
            s+=LocationUtils.LocationToString(block.getLocation())+";";
            s+=InventoryToBase64.serializeInventoryAsString(block.getInventory());
            output.add(s);
        }

        cache.set("Generators",output);
        try {
            cache.save(core.getCache().getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isTier(String key) {
        return tiers.containsKey(key);
    }

    public CandyTier getTier(String key) {
        return tiers.get(key);
    }

    public boolean isGenerator(Location location) {
        for (GeneratorBlock block : blocks) {
            if (LocationUtils.CompareLocations(location, block.getLocation())) return true;
        }
        return false;
    }

    public GeneratorBlock getGenerator(Location location) {
        for (GeneratorBlock block : blocks) {
            if (LocationUtils.CompareLocations(location, block.getLocation())) return block;
        }
        return null;
    }

    public void addGenerator(Location location, String key) {
        CandyTier tier = tiers.get(key);
        GeneratorBlock block = new GeneratorBlock(core, location, tier);
        blocks.add(block);
        save();
    }

    public void delGenerator(Location location) {
        for (GeneratorBlock block : blocks) {
            if (!(LocationUtils.CompareLocations(location, block.getLocation()))) continue;
            blocks.remove(block);
            block.delete();
            break;
        }
        save();
    }

    @Override
    public void run() {
        for (GeneratorBlock block : blocks) {
            block.count();
            if (block.getCount() >= 0) continue;
            block.resetCount();

            if (block.size() >= block.getTier().getAmount()) continue;

            for (ItemStack is : block.getTier().getItems()) {
                if (!(InventoryUtils.fillInventory(block.getInventory(), is, block.size(), block.getTier().getAmount()))) break;
            }

            block.updateHologram();
        }
    }
}
