package us.tryy3.spigot.plugins.gcore.quizinator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tryy3 on 2016-03-17.
 */
public class QuizBuilder {
    public List<QuizStep> steps = new ArrayList<>();

    public QuizBuilder() {

    }

    public QuizBuilder addStep(QuizStep step) {
        this.steps.add(step);
        return this;
    }

    public class Quiz {
        public List<QuizStep> steps;

        public Quiz(List<QuizStep> steps) {
            this.steps = steps;
        }

        public 
    }
}
