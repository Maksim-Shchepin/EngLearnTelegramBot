package com.shchepinms.telegram_bot.commands;

import com.shchepinms.telegram_bot.helper.Helper;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class Interval extends Command {

    public Interval() {
        super("interval",
                "Время между сообщениями");
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        if (strings != null && strings.length != 0) {
            try {
                int time = Integer.parseInt(strings[0]);

                if (Helper.MSG_INTERVAL_MIN_HOUR <= time && time <= Helper.MSG_INTERVAL_MAX_HOUR) {
                    Helper.getById(message.getFrom().getId()).setSendMessageInterval(time * 60);
                    message.setText("Интервал успешно установлен на " + time + " час.\n");
                }
                else if (Helper.MSG_INTERVAL_MIN_MINUTES <= time && time <= Helper.MSG_INTERVAL_MAX_MINUTES) {
                    Helper.getById(message.getFrom().getId()).setSendMessageInterval(time);
                    message.setText("Интервал успешно установлен на " + time + " мин.\n");
                } else throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                System.err.println("Не подходящее время для интервала между сообщениями = " + strings[0]);
                message.setText("Ошибка! \""+ strings[0] + "\" не является подходящим значением, пример использования команды:\n" +
                        """
                        /interval 6 - установит интервал 6 часов, доступный диапазон от 1 до 14 часов.
                        /interval 115 - установит интервал 115 минут, диапазон от 15 до 840 минут.
                        """);
            }
        } else {
            message.setText("""
                    Команда устанавливает время между сообщениями, например:
                    /interval 2 - установит интервал 2 часа, доступен диапазон от 1 до 14 часов.
                    /interval 30 - установит интервал 30 минут, диапазон от 15 до 840 минут.
                    """);
        }
        super.processMessage(absSender, message, null);
    }
}
