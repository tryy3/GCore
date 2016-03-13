package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.GCore;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class LandzoneCMD implements SubCommand {
    private GCore core;

    public LandzoneCMD(GCore core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String[] strings) {
        if (!sender.hasPermission("gcore.landzone")) return false;
        if (strings.length < 2) return false;

        core.getShipHandler().addLandzone(strings[1], ((Player) sender).getLocation());
        return true;
    }
}
