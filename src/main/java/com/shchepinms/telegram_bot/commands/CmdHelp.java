package com.shchepinms.telegram_bot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CmdHelp extends Command {

    public CmdHelp() {
        super("help", "Помощь");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        message.setText("""
                Я знаю следующие полезные команды:
               
                -------- Процесс обучения --------
                
                /run  -  начать получать слова по расписанию
                /stop -  прекратить получать слова
                
                ------- Настройка параметров ---
                
                /words 2  - получать по 2 пары ENG-RUS слов в сообщении
                /interval 2  -  получать сообщения каждые 2 часа
                
                ------------------------------------------------
                """);
        super.processMessage(absSender, message, null);
    }
}
