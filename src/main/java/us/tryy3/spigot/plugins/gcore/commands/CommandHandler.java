package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import us.tryy3.spigot.plugins.gcore.GCore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class CommandHandler implements CommandExecutor {
    private Map<String, SubCommand> subCommands = new HashMap<>();
    private GCore core;

    public CommandHandler(GCore core) {
        this.core = core;
        this.subCommands.put("candy", new CandyGenCMD(core));
        this.subCommands.put("landzone", new LandzoneCMD(core));
        this.subCommands.put("setlandzone", new SetLandzoneCMD(core));
        this.subCommands.put("setwarp", new SetWarpCMD(core));
        this.subCommands.put("warp", new WarpCMD(core));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 0 || !(subCommands.containsKey(strings[0]))) {
            commandSender.sendMessage(core.getMainConfig().getConfig().getString("Incorrect-Syntax"));
            return true;
        }

        subCommands.get(strings[0]).onCommand(commandSender, command, s, strings);
        return true;
    }
}
