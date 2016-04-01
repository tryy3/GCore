package us.tryy3.spigot.plugins.gcore.quizinator.gcore;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import us.tryy3.spigot.plugins.gcore.quizinator.QuizStep;
import us.tryy3.spigot.plugins.gcore.utils.ChatUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by tryy3 on 23/03/2016.
 */
public class QuizChoices extends QuizStep {
    private List<String> choices;
    private String choiceMessage;

    public QuizChoices(UUID uuid, List<String> initMessages, String failMessage, List<String> choices, String choiceMessage) {
        super(uuid, initMessages, failMessage);
        this.choices = choices;
        this.choiceMessage = choiceMessage;
    }

    @Override
    public void init() {
        super.init();

        if (choices != null || choices.size() > 0) {
            choiceMessage = ChatUtils.format(choiceMessage);
            for (String s : choices) {
                TextComponent message = new TextComponent(choiceMessage.replace("%choice%", s));
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, s));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click me!").create()));
                Bukkit.getPlayer(getUuid()).spigot().sendMessage(message);
            }
        }
    }

    @Override
    public boolean next(String msg) {
        String[] split = msg.toLowerCase().replaceAll(" ", "").split(",");
        for (String s : split) {
            if (!(choices.contains(s))) return false;
        }
        return true;
    }
}
