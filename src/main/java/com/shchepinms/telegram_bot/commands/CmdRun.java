package com.shchepinms.telegram_bot.commands;

import com.shchepinms.telegram_bot.helper.Helper;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CmdRun extends Command {

    public CmdRun() {
        super("run",
                "Run education!");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        long tgId = message.getFrom().getId();
        Helper helper = Helper.getById(tgId);
        helper.runEducation();
        message.setText(String .format("Ну что ж %s, начинаем обучение!", message.getFrom().getUserName()));
        super.processMessage(absSender, message, null);
    }
}
