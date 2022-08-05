package com.shchepinms.telegram_bot.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class Command implements IBotCommand {

    private final String commandIdentifier;
    private final String description;

    public Command(String commandIdentifier, String description) {
        this.commandIdentifier = commandIdentifier;
        this.description = description;
    }

    @Override
    public String getCommandIdentifier() {
        return commandIdentifier;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        System.out.printf(String.format("COMMAND: %s(%s)%n", message.getText(), Arrays.toString(strings)));
        try {
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(message.getChatId().toString())
                    .text(message.getText())
                    .build();
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.printf("Command message processing error: %s%n", e.getMessage());
        }
    }
}
