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

    public Quiz build() {
        return new Quiz(steps);
    }

    public class Quiz {
        private List<QuizStep> steps;
        private int currentStep = 0;
        private List<String> msgs = new ArrayList<>();

        public Quiz(List<QuizStep> steps) {
            this.steps = steps;
        }

        public void init() {
            steps.get(currentStep).init();
        }

        public void next() {
            currentStep++;
        }

        public boolean hasNext() {
            return (currentStep >= (steps.size()-1));
        }

        public boolean nextStep(String arg) {
            if (!(steps.get(currentStep)).next(arg)) return false;
            msgs.add(steps.get(currentStep).getAnswer());
            steps.get(currentStep).finish(msgs);
            return true;
        }

        public void failStep() {
            steps.get(currentStep).fail();
        }
    }
}
