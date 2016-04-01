package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class CommandHandler implements CommandExecutor {
    private Map<String, SubCommand> subCommands = new HashMap<>();
    private GCore core;
    private Pattern p1 = Pattern.compile("(?:-{2})(\\w+)(?:=)(\\w+)");
    private Pattern p2 = Pattern.compile("(?:-)(\\w)");

    public CommandHandler(GCore core) {
        this.core = core;
        this.subCommands.put("candy", new CandyGenCMD(core));
        this.subCommands.put("setlandzone", new SetLandzoneCMD(core));
        this.subCommands.put("dellandzone", new DelLandzoneCMD(core));
        this.subCommands.put("landzone", new LandzoneCMD(core));
        this.subCommands.put("setwarp", new SetWarpCMD(core));
        this.subCommands.put("delwarp", new DelWarpCMD(core));
        this.subCommands.put("warp", new WarpCMD(core));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings == null || strings.length <= 0 || !(subCommands.containsKey(strings[0]))) {
            ChatUtils.chat(commandSender, core.getMainConfig().getConfig().getString("Messages.Incorrect-Syntax"));
            return true;
        }

        Map<String, String> flags = new HashMap<>();

        int i = 0;
        while (true) {
            if (i >= strings.length) break;
            Matcher m1 = p1.matcher(strings[i]);
            if (m1.find()) {
                flags.put(m1.group(1).toLowerCase(), m1.group(2).toLowerCase());
                i++;
                continue;
            }

            if ((i+1) >= strings.length) break;
            Matcher m2 = p2.matcher(strings[i]);
            if (m2.find()) {
                flags.put(m2.group(1).toLowerCase(), strings[i+1].toLowerCase());
                i+=2;
                continue;
            }

            i++;
        }

        subCommands.get(strings[0]).onCommand(commandSender, command, s, strings, flags);
        return true;
    }
}
