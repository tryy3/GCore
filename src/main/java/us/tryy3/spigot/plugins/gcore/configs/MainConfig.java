package us.tryy3.spigot.plugins.gcore.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import us.tryy3.spigot.plugins.gcore.GCore;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class MainConfig {
    private File file;
    private YamlConfiguration config;

    public MainConfig(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        load();
    }


    public void load() {
        if (!(file.exists())) {
            File parent = file.getParentFile();
            if (!(parent.exists())) {
                parent.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        Map<String, String> values = new HashMap<>();

        if (!(config.contains("Generators"))) {
            // Generator 0
            saveDefault("Generators.0.Inventory-Name", "&cGenerator I");
            saveDefault("Generators.0.Name", "Tier I");
            saveDefault("Generators.0.Block", "NOTE_BLOCK");
            saveDefault("Generators.0.Seconds", 10);
            saveDefault("Generators.0.Max", 50);
            saveDefault("Generators.0.Items", Arrays.asList("STONE;5", "GRASS;4"));

            // Generator 1
            saveDefault("Generators.1.Inventory-Name", "&cGenerator II");
            saveDefault("Generators.1.Name", "Tier II");
            saveDefault("Generators.1.Block", "NOTE_BLOCK");
            saveDefault("Generators.1.Seconds", 5);
            saveDefault("Generators.1.Max", 100);
            saveDefault("Generators.1.Items", Arrays.asList("STONE;9", "GRASS;8"));
        }

        // Messages
        saveDefault("Messages.No-Permission", "'&cYou do not have permission to use that command.'");
        saveDefault("Messages.No-Permission-Ships", "&cYou do not have permission to use any ships in the landzone %landzone%.");
        saveDefault("Messages.No-Permission-Warp", "'&cYou do not have permission to go to any warps in the landzone %landzone%.");

        saveDefault("Messages.Incorrect-Syntax", "&cYour command syntax is incorrect.");

        saveDefault("Messages.Invalid-Warp", "&cThat is not a valid warp name, please try again.");
        saveDefault("Messages.Invalid-Warp-Name", "&cThe warp %warp% is not a valid warp name.");
        saveDefault("Messages.Invalid-Ship", "&c%fail% is not a valid ship, please try again.");
        saveDefault("Messages.Invalid-Direction", "&cThe direction %fail% is not a valid direction, please try again.");
        saveDefault("Messages.Invalid-Landzone", "&cThere is no landzone called %fail%.");

        saveDefault("Messages.Already-Landzone", "&cThere is already a landzone with the name %fail%.");
        saveDefault("Messages.Already-Warp", "&cThere is already a warp with the name %fail%.");

        saveDefault("Messages.Not-Valid-Tier", "&cThe tier %tier% is not a valid candy tier.");
        saveDefault("Messages.Not-Valid-Landzone", "&cThe landzone %landzone% is not a valid landzone.");

        saveDefault("Messages.Deleted-Warp", "&7Removed the warp %warp%.");
        saveDefault("Messages.Deleted-Landzone", "&7Removed the landzone %landzone%.");

        saveDefault("Messages.Creation-Warp", "&7Created a warp with the name %warp%.");
        saveDefault("Messages.Creation-Landzone", "&7Created a landzone with the name %landzone% at your location.");

        saveDefault("Messages.Warp-Has-Landzone", "&7The landzone %landzone% is in the warps %warps%, please remove it first, before removing the landzone.");
        saveDefault("Messages.Warp-Teleportation-Commencing", "&7Teleportation commencing, your ship is being prepared.");

        saveDefault("Messages.Supply-Player-Name", "&cPlease supply a player name.");
        saveDefault("Messages.Player-Not-Online", "&cThe player %player% is currently offline.");
        saveDefault("Messages.Select-Warps", "&7Please select the warp you wish to use.");
        saveDefault("Messages.Full-Inventory", "&cYour inventory is full, please free up some space before breaking this block.");
        saveDefault("Messages.Arrived-At", "&7You have arrived at %warp%.");

        saveDefault("Messages.Candy-Title", "&d&lCandy Generator");
        saveDefault("Messages.Candy-Items", "&7[&c%total% / %amount%&7]");
        saveDefault("Messages.Candy-Tier", "&7[&5%tier%&7]");
        save();
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDefault(String key, Object value) {
        saveDefault(key, value, null);
    }

    private void saveDefault(String key, Object value, String check) {
        if (check == null) check = key;
        if (config.contains(check)) return;
        config.set(key, value);
    }
    public YamlConfiguration getConfig() {
        return config;
    }
}
