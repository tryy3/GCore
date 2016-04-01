package us.tryy3.spigot.plugins.gcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import us.tryy3.spigot.plugins.gcore.candy.GenerateCandy;
import us.tryy3.spigot.plugins.gcore.commands.CommandHandler;
import us.tryy3.spigot.plugins.gcore.configs.LocationCache;
import us.tryy3.spigot.plugins.gcore.configs.MainConfig;
import us.tryy3.spigot.plugins.gcore.listeners.GeneratorListener;
import us.tryy3.spigot.plugins.gcore.listeners.ShipListener;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizHandler;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizListener;
import us.tryy3.spigot.plugins.gcore.ship.ShipHandler;

import java.io.File;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class GCore extends JavaPlugin {
    private ShipHandler shipHandler;
    private MainConfig mainConfig;
    private LocationCache cache;
    private GenerateCandy candy;
    private QuizHandler quizHandler;

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("HolographicDisplays is not installed or enabled.");
            getLogger().severe("Disabling this plugin.");
            this.setEnabled(false);
            return;
        }

        this.mainConfig = new MainConfig(new File(getDataFolder()+"/config.yml"));
        this.cache = new LocationCache(new File(getDataFolder()+"/cache.yml"), this);
        this.candy = new GenerateCandy(this);
        this.shipHandler = new ShipHandler(this);
        this.quizHandler = new QuizHandler();

        this.candy.runTaskTimer(this, 0L, 1L);

        Bukkit.getServer().getPluginManager().registerEvents(new ShipListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GeneratorListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new QuizListener(this.quizHandler), this);

        getCommand("gcore").setExecutor(new CommandHandler(this));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public ShipHandler getShipHandler() {
        return shipHandler;
    }

    public LocationCache getCache() {
        return cache;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public GenerateCandy getCandy() {
        return candy;
    }

    public QuizHandler getQuizHandler() {
        return quizHandler;
    }
}
