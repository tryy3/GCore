package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.ship.Direction;
import us.tryy3.spigot.plugins.gcore.ship.Landzone;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by tryy3 on 2016-03-20.
 */
public class SetLandzoneCMD implements SubCommand {
    private GCore core;

    public SetLandzoneCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings, Map<String, String> flags) {
        YamlConfiguration config = core.getMainConfig().getConfig();
        if (!(sender.hasPermission("gcore.admin")) || !(sender.hasPermission("gcore.cmd.setlandzone"))) {
            ChatUtils.chat(sender, config.getString("Messages.No-Permission"));
            return;
        }
        Player player = (Player) sender;

        System.out.println(flags.toString());

        String landzone;

        if (flags.containsKey("name")) landzone = flags.get("name");
        else if (flags.containsKey("n")) landzone = flags.get("n");
        else {
            ChatUtils.chat(sender, config.getString("Messages.Incorrect-Syntax"));
            return;
        }

        if (core.getCache().isLandzone(landzone)) {
            ChatUtils.chat(sender, config.getString("Messages.Already-Landzone").replace("%fail%", landzone));
        }

        String[] Ship = null;

        if (flags.containsKey("ship")) Ship = flags.get("ship").split(",");
        else if (flags.containsKey("s")) Ship = flags.get("s").split(",");

        if (Ship != null) {
            for (String sh : Ship) {
                if (core.getShipHandler().isShip(sh)) continue;
                ChatUtils.chat(sender, config.getString("Messages.Invalid-Ship").replace("%fail%", sh));
                return;
            }
        }

        String[] directions = null;

        if (flags.containsKey("direction")) directions = flags.get("direction").split(",");
        else if (flags.containsKey("d")) directions = flags.get("d").split(",");

        Direction[] dirs = null;

        if (directions != null) {
            dirs = new Direction[directions.length];
            for (int i = 0; i < directions.length; i++) {
                if (Direction.isDirection(directions[i])) {
                    dirs[i] = Direction.getDir(directions[i]);
                    continue;
                }
                ChatUtils.chat(sender, config.getString("Messages.Invalid-Direction").replace("%fail%", directions[i]));
                return;
            }
        }

        String commands = null;

        if (flags.containsKey("command")) commands = flags.get("command");
        else if (flags.containsKey("c")) commands = flags.get("c");

        core.getCache().addLandzone(landzone, new Landzone(landzone, player.getLocation(), Arrays.asList(Ship != null ? Ship : new String[0]), dirs, commands));
        ChatUtils.chat(sender, config.getString("Messages.Creation-Landzone").replace("%landzone%", landzone));
    }
}
