package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by tryy3 on 2016-03-12.
 */
public interface SubCommand {
    public boolean onCommand(CommandSender sender, Command command, String[] strings);
}