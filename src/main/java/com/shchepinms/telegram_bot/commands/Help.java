package com.shchepinms.telegram_bot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;

public class Help extends Command {

    public Help() {
        super("help", "Помощь");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        message.setText("Так, я пока ничего делать то и не умею, как говорится, мне бы кто помог :D");
        super.processMessage(absSender, message, null);
    }
}
