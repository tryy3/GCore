package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.ship.Warp;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.Map;

/**
 * Created by tryy3 on 2016-03-12.
 */
class SetWarpCMD implements SubCommand {
    private GCore core;

    SetWarpCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings, Map<String, String> flags) {
        YamlConfiguration config = core.getMainConfig().getConfig();
        if (!(sender.hasPermission("gcore.admin")) || !(sender.hasPermission("gcore.cmd.setwarp"))) {
            ChatUtils.chat(sender, config.getString("Messages.No-Permission"));
            return;
        }

        String name, from, to;

        if (flags.containsKey("name")) name = flags.get("name");
        else if (flags.containsKey("n")) name = flags.get("n");
        else {
            ChatUtils.chat(sender, config.getString("Messages.Incorrect-Syntax"));
            return;
        }

        if (flags.containsKey("from")) from = flags.get("from");
        else if (flags.containsKey("f")) from = flags.get("f");
        else {
            ChatUtils.chat(sender, config.getString("Messages.Incorrect-Syntax"));
            return;
        }

        if (flags.containsKey("to")) to = flags.get("to");
        else if (flags.containsKey("t")) to = flags.get("t");
        else {
            ChatUtils.chat(sender, config.getString("Messages.Incorrect-Syntax"));
            return;
        }

        if (core.getCache().isWarp(name)) {
            ChatUtils.chat(sender, config.getString("Messages.Already-Warp").replace("%fail%", name));
            return;
        }

        if (!(core.getCache().isLandzone(from))) {
            ChatUtils.chat(sender, config.getString("Messages.Invalid-Landzone").replace("%fail%", from));
            return;
        }

        if (!(core.getCache().isLandzone(to))) {
            ChatUtils.chat(sender, config.getString("Messages.Invalid-Landzone").replace("%fail%", to));
            return;
        }

        core.getCache().addWarp(new Warp(name,core.getCache().getLandzone(from), core.getCache().getLandzone(to)));
        ChatUtils.chat(sender, config.getString("Messages.Creation-Warp").replace("%warp%", name));
    }
}
