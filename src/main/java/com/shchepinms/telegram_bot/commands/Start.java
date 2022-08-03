package com.shchepinms.telegram_bot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Start extends Command {

    public Start() {
        super("start", "Запуск бота");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        message.setText("""
                Добро пожаловать!\s
                Вас приветствует бот @EngLearnTelegram_bot, я помогу вам учить новые слова каждый день!
                """);
        super.processMessage(absSender, message, null);
    }
}
