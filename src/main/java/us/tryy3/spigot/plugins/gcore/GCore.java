package us.tryy3.spigot.plugins.gcore;

import org.bukkit.plugin.java.JavaPlugin;
import us.tryy3.spigot.plugins.gcore.configs.LocationCache;
import us.tryy3.spigot.plugins.gcore.configs.MainConfig;
import us.tryy3.spigot.plugins.gcore.ship.ShipHandler;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class GCore extends JavaPlugin {
    private ShipHandler shipHandler;
    private MainConfig mainConfig;
    private LocationCache cache;


    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public ShipHandler getShipHandler() {
        return shipHandler;
    }
}
