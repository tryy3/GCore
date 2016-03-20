package us.tryy3.spigot.plugins.gcore.quizinator.gcore;

import org.bukkit.Bukkit;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.ship.Landzone;

import java.util.List;
import java.util.UUID;

/**
 * Created by dennis.planting on 3/17/2016.
 */
public class QuizAddLandzone extends QuizMessage {
    private GCore core;
    public QuizAddLandzone(List<String> choices, List<String> initMessages, String choiceMessage, String failMessage, UUID uuid, GCore core) {
        super(choices, initMessages, choiceMessage, failMessage, uuid);
        this.core = core;
    }

    @Override
    public void finish(List<String> args) {
        String name = args.get(0);
        String command = args.get(1);

        if (core.getCache().isLandzone(name)) {
            Bukkit.getPlayer(getUuid()).sendMessage("There is already a landzone with the name "+name);
            return;
        }

        if (command.equalsIgnoreCase("") || command.equalsIgnoreCase("null")) command = null;

        core.getCache().addLandzone(name, new Landzone(name, Bukkit.getPlayer(getUuid()).getLocation(), command));
    }
}
