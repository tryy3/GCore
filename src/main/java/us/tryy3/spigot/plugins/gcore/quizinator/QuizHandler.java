package us.tryy3.spigot.plugins.gcore.quizinator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizBuilder.Quiz;

/**
 * Created by tryy3 on 2016-03-17.
 */
public class QuizHandler {
    private Map<UUID, Quiz> activeQuizes = new HashMap<>();

    public QuizHandler() {

    }

    public void addPlayer(UUID uuid, Quiz quiz) {
        this.activeQuizes.put(uuid, quiz);
        quiz.init();
    }

    public void delPlayer(UUID uuid) {
        this.activeQuizes.remove(uuid);
    }

    public Quiz getPlayer(UUID uuid) {
        return this.activeQuizes.get(uuid);
    }

    public boolean hasPlayer(UUID uuid) {
        return this.activeQuizes.containsKey(uuid);
    }
}
