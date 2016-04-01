package us.tryy3.spigot.plugins.gcore.quizinator.gcore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import us.tryy3.spigot.plugins.gcore.GCore;
import us.tryy3.spigot.plugins.gcore.configs.LocationCache;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizStep;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by dennis.planting on 3/17/2016.
 */
public class QuizStartWarp extends QuizChoices {
    private GCore core;

    public QuizStartWarp(UUID uuid, List<String> initMessages, String failMessage, List<String> choices, String choiceMessage, GCore core) {
        super(uuid, initMessages, failMessage, choices, choiceMessage);
        this.core = core;
    }

    @Override
    public void finish(List<String> args) {
        String name = args.get(0);

        core.getShipHandler().activateShip(getUuid(), core.getCache().getWarp(name));
        ChatUtils.chat(Bukkit.getPlayer(getUuid()), core.getMainConfig().getConfig().getString("Messages.Warp-Teleportation-Commencing"));
    }
}
