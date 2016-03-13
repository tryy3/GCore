package us.tryy3.spigot.plugins.gcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import us.tryy3.spigot.plugins.gcore.GCore;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class GeneratorListener {
    private GCore core;

    public GeneratorListener(GCore core) {
        this.core = core;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

    }
}
