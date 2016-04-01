package us.tryy3.spigot.plugins.gcore.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis.planting on 3/15/2016.
 */
public class ChatUtils {
    public static List<String> StringsToArray(String... args) {
        List<String> retString = new ArrayList<>();

        for (String s : args) {
            retString.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        return retString;
    }

    public static void chat(CommandSender player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
