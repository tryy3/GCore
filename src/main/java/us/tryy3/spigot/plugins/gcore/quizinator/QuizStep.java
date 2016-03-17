package us.tryy3.spigot.plugins.gcore.quizinator;

import java.util.List;

/**
 * Created by tryy3 on 2016-03-17.
 */
public abstract class QuizStep {
    String msg;
    public QuizStep() {

    }

    public boolean next(String msg) {
        this.msg = msg;
        return true;
    }

    public void finish(List<String> args) {

    }

    public void fail() {

    }

    public String getMsg() {
        return msg;
    }
}
