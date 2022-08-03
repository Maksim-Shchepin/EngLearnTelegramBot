package com.shchepinms.telegram_bot.commands;

import com.shchepinms.telegram_bot.helper.Helper;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Words extends Command {

    public Words() {
        super("words",
                "Количество слов в день");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        if (strings != null && strings.length != 0) {
            try {
                int wordsCount = Integer.parseInt(strings[0]);

                if (Helper.MSG_WORDS_MIN_COUNT <= wordsCount && wordsCount <= Helper.MSG_WORDS_MAX_COUNT) {
                    Helper.getById(message.getFrom().getId()).setWordCountPerDay(wordsCount);
                    message.setText("Количество слов успешно установленно на " + wordsCount + " в день.\n");
                }
                else throw new NumberFormatException();

            } catch (NumberFormatException ex) {
                System.err.println("Не подходящее значение для установки количества слов в день = " + strings[0]);
                message.setText("Ошибка! \""+ strings[0] + "\" не является подходящим значением, пример использования команды:\n" +
                        "/words 3 - установит 3 слова в день, доступный диапазон от 1 до 20 слов.\n");
            }
        } else {
            message.setText("""
                    Команда устанавливает количество слов для изучения на один день, например:
                    /words 5 - установит 5 слов в день, доступный диапазон от 1 до 20 слов.
                    """);
        }
        super.processMessage(absSender, message, null);
    }
}
