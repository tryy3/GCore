package us.tryy3.spigot.plugins.gcore.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class MainConfig {
    private File file;
    private YamlConfiguration config;

    public MainConfig(File file) {
        this.file = file;
    }


    public void load() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
