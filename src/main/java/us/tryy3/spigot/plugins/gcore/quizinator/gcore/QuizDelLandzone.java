package us.tryy3.spigot.plugins.gcore.quizinator.gcore;

import org.bukkit.Bukkit;
import us.tryy3.spigot.plugins.gcore.GCore;

import java.util.List;
import java.util.UUID;

/**
 * Created by tryy3 on 2016-03-17.
 */
public class QuizDelLandzone extends QuizMessage {
    private GCore core;

    public QuizDelLandzone(List<String> choices, List<String> initMessages, String choiceMessage, String failMessage, UUID uuid, GCore core) {
        super(choices, initMessages, choiceMessage, failMessage, uuid);
        this.core = core;
    }

    @Override
    public void finish(List<String> args) {
        String name = args.get(0);

        if (!(core.getCache().isLandzone(name))) {
            Bukkit.getPlayer(getUuid()).sendMessage("The landzone "+name+" is not a valid landzone");
            return;
        }

        core.getCache().delLandzone(name);
        Bukkit.getPlayer(getUuid()).sendMessage("Removed the landzone "+name);
    }
}
