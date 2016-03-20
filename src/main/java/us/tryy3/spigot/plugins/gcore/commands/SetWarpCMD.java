package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizBuilder;
import us.tryy3.spigot.plugins.gcore.quizinator.gcore.QuizAddWarp;
import us.tryy3.spigot.plugins.gcore.quizinator.gcore.QuizMessage;

import java.util.Arrays;

/**
 * Created by tryy3 on 2016-03-12.
 */
class SetWarpCMD implements SubCommand {
    private GCore core;

    SetWarpCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender.hasPermission("gcore.admin")) || !(sender.hasPermission("gcore.cmd.setwarp"))) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("No-Permission"));
            return;
        }

        Player player = (Player) sender;
        QuizBuilder builder = new QuizBuilder();
        builder.addStep(new QuizMessage(null, Arrays.asList("Please choose a warp name."), null, null, player.getUniqueId()));
        builder.addStep(new QuizMessage(null, Arrays.asList("Please choose a landzone to warp from."), null, null, player.getUniqueId()));
        builder.addStep(new QuizAddWarp(null, Arrays.asList("Please choose a landzone to warp to."), null, null, player.getUniqueId(), core));
        core.getQuizHandler().addPlayer(player.getUniqueId(), builder.build());
    }
}
