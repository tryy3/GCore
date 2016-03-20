package us.tryy3.spigot.plugins.gcore.quizinator.gcore;

import org.bukkit.Bukkit;
import us.tryy3.spigot.plugins.gcore.GCore;

import java.util.List;
import java.util.UUID;

/**
 * Created by dennis.planting on 3/17/2016.
 */
public class QuizWarp extends QuizMessage {
    private GCore core;

    public QuizWarp(List<String> choices, List<String> initMessages, String choiceMessage, String failMessage, UUID uuid, GCore core) {
        super(choices, initMessages, choiceMessage, failMessage, uuid);
        this.core = core;
    }

    @Override
    public void finish(List<String> args) {
        String name = args.get(0);

        if (!(core.getCache().isWarp(name))) {
            Bukkit.getPlayer(getUuid()).sendMessage("The warp "+name+" is not a valid warp");
            return;
        }

        core.getShipHandler().activateShip(getUuid(), core.getCache().getWarp(name));
        Bukkit.getPlayer(getUuid()).sendMessage(core.getMainConfig().getConfig().getString("Warp-Teleportation-Commencing"));
    }
}
