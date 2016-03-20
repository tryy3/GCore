package us.tryy3.spigot.plugins.gcore.quizinator;

import java.util.List;

/**
 * Created by tryy3 on 2016-03-17.
 */
public abstract class QuizStep {
    String answer;
    public QuizStep() {

    }

    public void init() {
        return;
    }

    public boolean next(String msg) {
        this.answer = msg;
        return true;
    }

    public void finish(List<String> args) {

    }

    public void fail() {

    }

    public String getAnswer() {
        return answer;
    }
}
