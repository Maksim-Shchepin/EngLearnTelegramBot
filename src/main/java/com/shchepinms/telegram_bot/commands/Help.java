package com.shchepinms.telegram_bot.commands;

import com.shchepinms.telegram_bot.helper.Helper;
import com.shchepinms.telegram_bot.util.UserConfigManager;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Help extends Command {

    public Help() {
        super("help", "Помощь");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        UserConfigManager.save(Helper.getById(message.getFrom().getId()));
        Helper helper = UserConfigManager.load(message.getFrom().getId());
        System.out.println(helper.getWordsCountPerDay());
//        message.setText("Так, я пока ничего делать то и не умею, как говорится, мне бы кто помог :D");
        super.processMessage(absSender, message, null);
    }
}
