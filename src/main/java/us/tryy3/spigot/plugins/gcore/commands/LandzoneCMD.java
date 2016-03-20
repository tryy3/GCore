package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizBuilder;
import us.tryy3.spigot.plugins.gcore.quizinator.gcore.QuizWarp;
import us.tryy3.spigot.plugins.gcore.ship.Landzone;
import us.tryy3.spigot.plugins.gcore.ship.Warp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tryy3 on 2016-03-12.
 */
public class LandzoneCMD implements SubCommand {
    private GCore core;

    public LandzoneCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender.hasPermission("gcore.user")) || !(sender.hasPermission("gcore.cmd.landzone"))) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("No-Permission"));
            return;
        }

        if (strings.length < 1) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("Incorrect-Syntax"));
            return;
        }

        String zone = strings[1].toLowerCase();

        if (!(core.getCache().isLandzone(zone))) {
            sender.sendMessage("The landzone "+strings[1]+" is not a valid landzone.");
            return;
        }

        if (!(sender.hasPermission("gcore.landzone."+zone))) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("No-Permission"));
            return;
        }

        Landzone landzone = core.getCache().getLandzone(zone);

        if (landzone.getShip() != null || landzone.getDirection() != null) {
            sender.sendMessage("You can't use this landzone.");
            return;
        }

        Player player = (Player) sender;

        if (landzone.getShip(player) == null) {
            sender.sendMessage("You do not have permission for any ships.");
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
            sender.sendMessage("You do not have permission for any warps in this location.");
            return;
        }

        core.getShipHandler().startShip(player.getUniqueId(), landzone);

        QuizBuilder quiz = new QuizBuilder();
        quiz.addStep(new QuizWarp(warps,
                Arrays.asList("Please select the warp you want to use"),
                "",
                "This is not a valid warp name, please try again",
                player.getUniqueId(),
                core));
        core.getQuizHandler().addPlayer(player.getUniqueId(), quiz.build());
    }
}
