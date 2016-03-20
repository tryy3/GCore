package us.tryy3.spigot.plugins.gcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizBuilder;
import us.tryy3.spigot.plugins.gcore.quizinator.gcore.QuizAddLandzone;
import us.tryy3.spigot.plugins.gcore.quizinator.gcore.QuizMessage;
import us.tryy3.spigot.plugins.gcore.ship.Direction;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tryy3 on 2016-03-20.
 */
public class SetLandzoneCMD implements SubCommand {
    private GCore core;

    public SetLandzoneCMD(GCore core) {
        this.core = core;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender.hasPermission("gcore.admin")) || !(sender.hasPermission("gcore.cmd.setlandzone"))) {
            sender.sendMessage(core.getMainConfig().getConfig().getString("No-Permission"));
            return;
        }

        Player player = (Player) sender;
        QuizBuilder builder = new QuizBuilder();
        builder.addStep(new QuizMessage(null, Arrays.asList("Please choose a landzone name"), null, null, player.getUniqueId()));
        builder.addStep(new QuizMessage(core.getShipHandler().getShips(), Arrays.asList("Please choose one or multiple ships, seperate the ships using a comma."), "Ship: ", null, player.getUniqueId()));
        builder.addStep(new QuizMessage(Direction.getDirs(), Arrays.asList("Please choose a direction."), "Direction: ", null, player.getUniqueId()));
        builder.addStep(new QuizAddLandzone(null, Arrays.asList("Please choose a command that will run when a player gets tped to this landzone"), null, null, player.getUniqueId(), core));
        core.getQuizHandler().addPlayer(player.getUniqueId(), builder.build());
    }
}
