package us.tryy3.spigot.plugins.gcore.quizinator.gcore;

import org.bukkit.Bukkit;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.ship.Warp;

import java.util.List;
import java.util.UUID;

/**
 * Created by dennis.planting on 3/17/2016.
 */
public class QuizAddWarp extends QuizMessage {
    private GCore core;
    public QuizAddWarp(List<String> choices, List<String> initMessages, String choiceMessage, String failMessage, UUID uuid, GCore core) {
        super(choices, initMessages, choiceMessage, failMessage, uuid);
        this.core = core;
    }

    @Override
    public void finish(List<String> args) {
        String name = args.get(0);
        String from = args.get(1);
        String to = args.get(2);

        if (core.getCache().isWarp(name)) {
            Bukkit.getPlayer(getUuid()).sendMessage("There is already a warp with the name "+name);
            return;
        }
        if (!(core.getCache().isLandzone(from))) {
            Bukkit.getPlayer(getUuid()).sendMessage("There is no landzone called"+from);
            return;
        }
        if (!(core.getCache().isLandzone(to))) {
            Bukkit.getPlayer(getUuid()).sendMessage("There is no landzone called"+to);
            return;
        }

        core.getCache().addWarp(new Warp(name,
                core.getCache().getLandzone(from),
                core.getCache().getLandzone(to)));

        Bukkit.getPlayer(getUuid()).sendMessage("Added a warp at your location with the name "+name);
    }
}
