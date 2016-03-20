package us.tryy3.spigot.plugins.gcore.quizinator.gcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizStep;

import java.util.List;
import java.util.UUID;

/**
 * Created by dennis.planting on 3/17/2016.
 */
public class QuizMessage extends QuizStep {
    private List<String> choices;
    private List<String> initMessages;
    private String choiceMessage;
    private String failMessage;
    private UUID uuid;

    public QuizMessage(List<String> choices, List<String> initMessages, String choiceMessage, String failMessage, UUID uuid) {
        this.choices = choices;
        this.initMessages = initMessages;
        this.choiceMessage = choiceMessage;
        this.failMessage = failMessage;
        this.uuid = uuid;
    }

    @Override
    public void init() {
        Player player = Bukkit.getPlayer(uuid);
        for (String init : initMessages) {
            player.sendMessage(init);
        }

        for (String choice : choices) {
            player.sendMessage(choiceMessage.replace("%choice%",choice));
        }

        super.init();
    }

    @Override
    public boolean next(String msg) {
        return choices.contains(msg);
    }

    @Override
    public void fail() {
        Bukkit.getPlayer(uuid).sendMessage(failMessage);
    }

    public UUID getUuid() {
        return uuid;
    }
}
