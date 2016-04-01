package us.tryy3.spigot.plugins.gcore.quizinator;

import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;

/**
 * Created by tryy3 on 2016-03-17.
 */
public abstract class QuizStep {
    private String answer;
    private UUID uuid;
    private List<String> initMessages;
    private String failMessage;

    public QuizStep(UUID uuid, List<String> initMessages, String failMessage) {
        this.uuid = uuid;
        this.initMessages = initMessages;
        this.failMessage = failMessage;
    }

    public void init() {
        if (initMessages != null || initMessages.size() > 0) {
            for (String s : initMessages) {
                Bukkit.getPlayer(uuid).sendMessage(s);
            }
        }
    }

    public boolean next(String msg) {
        this.answer = msg;
        return true;
    }

    public void finish(List<String> args) {

    }

    public void fail(String msg) {
        if (failMessage == null || failMessage.equalsIgnoreCase("")) return;
        Bukkit.getPlayer(uuid).sendMessage(failMessage.replace("%fail%", msg));
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getInitMessages() {
        return initMessages;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFailMessage() {
        return failMessage;
    }
}
