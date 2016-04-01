package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * Created by tryy3 on 2016-03-12.
 */
public interface SubCommand {
    void onCommand(CommandSender sender, Command command, String s, String[] strings, Map<String, String> flags);
}
