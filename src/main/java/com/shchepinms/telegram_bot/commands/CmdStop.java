package com.shchepinms.telegram_bot.commands;

import com.shchepinms.telegram_bot.helper.Helper;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CmdStop extends Command {

    public CmdStop() {
        super("stop",
                "Stop word :)");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        long tgId = message.getFrom().getId();
        Helper helper = Helper.getById(tgId);
        helper.stopEducation();
        message.setText(String .format("Стоп слово! %s, пора отдыхать!", message.getFrom().getUserName()));
        super.processMessage(absSender, message, null);
    }
}
