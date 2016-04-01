package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.ship.Warp;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.Map;

/**
 * Created by tryy3 on 2016-03-12.
 */
class WarpCMD implements SubCommand {
    private GCore core;

    WarpCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings, Map<String, String> flags) {
        YamlConfiguration config = core.getMainConfig().getConfig();
        if (!(sender.hasPermission("gcore.user")) || !(sender.hasPermission("gcore.cmd.warp"))) {
            ChatUtils.chat(sender, config.getString("Messages.No-Permission"));
            return;
        }

        if (strings.length < 1) {
            ChatUtils.chat(sender, config.getString("Messages.Incorrect-Syntax"));
            return;
        }

        String w = strings[1].toLowerCase();

        if (!(core.getCache().isWarp(w))) {
            ChatUtils.chat(sender, config.getString("Messages.Invalid-Warp-Name").replace("%warp%", strings[1]));
            return;
        }

        Warp warp = core.getCache().getWarp(w);
        core.getShipHandler().activateShip(((Player) sender).getUniqueId(), warp);
        ChatUtils.chat(sender, config.getString("Messages.Warp-Teleportation-Commencing"));
    }
}
