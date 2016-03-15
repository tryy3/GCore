package us.tryy3.spigot.plugins.gcore.candy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.utils.LocationUtils;

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


    }

    public void load() {
        YamlConfiguration tierConfig = core.config().getConfig();
        for (Map.Entry<String, Object> tiers : tierConfig.getConfigurationSection("Generators").getValues(false).entrySet()) {
            List<ItemStack> items = new ArrayList<>();
            for (String s : tierConfig.getStringList("Generators."+tiers.getKey()+".Items")) {
                String[] split = s.split(";");

                items.add(new ItemStack(Material.getMaterial(split[0]), Integer.parseInt(split[1])));
            }

            int delay = tierConfig.getInt("Generators."+tiers.getKey()+".Seconds");
            int amount = tierConfig.getInt("Generators."+tiers.getKey()+".Max");
            String name = tierConfig.getString("Generators."+tiers.getKey()+".Name");
            String invName = tierConfig.getString("Generators."+tiers.getKey()+".Inventory-Name");
            Material block = Material.getMaterial(tierConfig.getString("Generators."+tiers.getKey()+".Block"));

            this.tiers.put(tiers.getKey(), new CandyTier(items, delay, amount, name, block, invName));
        }

        for (String s : core.getCache().getConfig().getStringList("Generators")) {
            String[] split = s.split(";");
            Location location = LocationUtils.LocationFromArray(split[0],split[1],split[2],split[3]);

            Inventory inventory =

            GeneratorBlock block = new GeneratorBlock()
        }
    }

    public void save() {

    }

    @Override
    public void run() {
        for (GeneratorBlock block : blocks) {
            block.count();
            if (block.getCount() >= 0) continue;
            block.resetCount();

            if (block.size() >= block.getTier().getAmount()) continue;

            for (ItemStack is : block.getTier().getItems()) {
                block.getInventory().addItem(is);
            }


        }
    }
}
