package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.ship.Warp;

/**
 * Created by tryy3 on 2016-03-12.
 */
class WarpCMD implements SubCommand {
    private GCore core;

    WarpCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender.hasPermission("gcore.user")) || !(sender.hasPermission("gcore.cmd.warp"))) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("No-Permission"));
            return;
        }

        if (strings.length < 1) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("Incorrect-Syntax"));
            return;
        }

        String w = strings[1].toLowerCase();

        if (!(core.getCache().isWarp(w))) {
            sender.sendMessage("The warp "+strings[1]+" is not a valid warp name.");
            return;
        }

        Warp warp = core.getCache().getWarp(w);
        core.getShipHandler().activateShip(((Player) sender).getUniqueId(), warp);
        sender.sendMessage("Warping to "+warp.getName());
    }
}
