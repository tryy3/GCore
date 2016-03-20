package us.tryy3.spigot.plugins.gcore.quizinator;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizBuilder.Quiz;

/**
 * Created by tryy3 on 2016-03-17.
 */
public class QuizListener implements Listener {
    private QuizHandler handler;

    public QuizListener(QuizHandler handler) {
        this.handler = handler;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (!(this.handler.hasPlayer(e.getPlayer().getUniqueId()))) return;
        Quiz quiz = this.handler.getPlayer(e.getPlayer().getUniqueId());
        if (!(quiz.nextStep(e.getMessage()))) {
            quiz.failStep();
            return;
        }
        quiz.next();
        if (quiz.hasNext()) {
            quiz.init();
            return;
        }
        this.handler.delPlayer(e.getPlayer().getUniqueId());
    }
}
