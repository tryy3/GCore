package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.Map;

/**
 * Created by tryy3 on 23/03/2016.
 */
public class DelWarpCMD implements SubCommand {
    private GCore core;

    public DelWarpCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings, Map<String, String> flags) {
        YamlConfiguration config = core.getMainConfig().getConfig();

        if (!(sender.hasPermission("gcore.admin")) || !(sender.hasPermission("gcore.cmd.delwarp"))) {
            ChatUtils.chat(sender, core.getMainConfig().getConfig().getString("Messages.No-Permission"));
            return;
        }

        if (strings.length < 2) {
            ChatUtils.chat(sender, core.getMainConfig().getConfig().getString("Messages.Incorrect-Syntax"));
            return;
        }

        String name = strings[1].toLowerCase();

        if (!(core.getCache().isWarp(name))) {
            ChatUtils.chat(sender, config.getString("Messages.Invalid-Warp-Name").replace("%warp%", name));
            return;
        }

        core.getCache().delWarp(name);
        ChatUtils.chat(sender, config.getString("Messages.Deleted-Warp").replace("%warp%", name));
    }
}
