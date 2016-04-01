package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizBuilder;
import us.tryy3.spigot.plugins.gcore.quizinator.gcore.QuizStartWarp;
import us.tryy3.spigot.plugins.gcore.ship.Landzone;
import us.tryy3.spigot.plugins.gcore.ship.Warp;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class LandzoneCMD implements SubCommand {
    private GCore core;

    public LandzoneCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings, Map<String, String> string) {
        YamlConfiguration config = core.getMainConfig().getConfig();
        if (!(sender.hasPermission("gcore.user")) || !(sender.hasPermission("gcore.cmd.landzone"))) {
            ChatUtils.chat(sender, config.getString("Messages.No-Permission"));
            return;
        }

        if (strings.length < 1) {
            ChatUtils.chat(sender, config.getString("Messages.Incorrect-Syntax"));
            return;
        }

        String zone = strings[1].toLowerCase();

        if (!(core.getCache().isLandzone(zone))) {
            ChatUtils.chat(sender, config.getString("Messages.Not-Valid-Tier").replace("%landzone%",strings[1]));
            return;
        }

        if (!(sender.hasPermission("gcore.landzone."+zone))) {
            ChatUtils.chat(sender, config.getString("Messages.No-Permission"));
            return;
        }

        Landzone landzone = core.getCache().getLandzone(zone);

        if (landzone.getShip() != null || landzone.getDirection() != null) {
            ChatUtils.chat(sender, config.getString("Messages.Invalid-Landzone").replace("%landzone%",strings[1]));
            return;
        }

        Player player = (Player) sender;

        if (landzone.getShip(player) == null) {
            ChatUtils.chat(sender, config.getString("Messages.No-Permission-Ships").replace("%landzone%",strings[1]));
            return;
        }

        List<String> warps = new ArrayList<>();
        for (Warp warp : core.getCache().getWarps().values()) {
            if (!(warp.getFrom().getName().equalsIgnoreCase(landzone.getName()))) continue;
            if (!(player.hasPermission("gcore.warp."+warp.getName()))) continue;
            if (!(player.hasPermission("gcore.landzone."+warp.getTo().getName()))) continue;
            warps.add(warp.getName());
        }

        if (warps.size() <= 0) {
            ChatUtils.chat(sender, config.getString("Messages.No-Permission-Warp").replace("%landzone%",strings[1]));
            return;
        }

        core.getShipHandler().startShip(player.getUniqueId(), landzone);

        QuizBuilder quiz = new QuizBuilder();
        quiz.addStep(new QuizStartWarp(player.getUniqueId(),
                Arrays.asList(ChatUtils.format(config.getString("Messages.Select-Warps"))),
                ChatUtils.format(config.getString("Messages.Invalid-Warp-Name")),
                warps,
                "Warps: %choice%",
                core));
        core.getQuizHandler().addPlayer(player.getUniqueId(), quiz.build());
    }
}
