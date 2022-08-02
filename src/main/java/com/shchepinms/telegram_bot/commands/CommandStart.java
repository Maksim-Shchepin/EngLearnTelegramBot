package com.shchepinms.telegram_bot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CommandStart extends Command {

    public CommandStart() {
        super("start", "Запуск бота");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        message.setText("Добро пожаловать! \n"
                + "Вас приветствует бот @EngLearnTelegram_bot, я помогу вам учить новые слова каждый день!\n");
        super.processMessage(absSender, message, null);
    }
}
