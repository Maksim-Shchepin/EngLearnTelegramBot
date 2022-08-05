package com.shchepinms.telegram_bot.bot;

import com.shchepinms.telegram_bot.commands.Help;
import com.shchepinms.telegram_bot.commands.Interval;
import com.shchepinms.telegram_bot.commands.Start;
import com.shchepinms.telegram_bot.commands.Words;
import com.shchepinms.telegram_bot.core.UserException;
import com.shchepinms.telegram_bot.helper.Helper;
import com.shchepinms.telegram_bot.settings.BotSettings;
import com.shchepinms.telegram_bot.util.DictionaryParser;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BotProcessor extends TelegramLongPollingCommandBot {
    private final static int TEXT_LIMIT = 512;
    private final static BotSettings botSettings = BotSettings.getInstance();
    private static BotProcessor instance;
    private List<String> registeredCommands = new ArrayList<>();

    private BotProcessor() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            registerCommands();
        } catch (TelegramApiException ex) {
            throw new RuntimeException("Telegram bot initialize error: " + ex.getMessage());
        }
    }

    /**
     * Запуск бота.
     *
     * @return Возвращает экземпляр синглтона, создание экземпляра провоцирует инициализацию и запуск бота.
     */
    public static BotProcessor run() {
        if (instance == null)
            instance = new BotProcessor();
        return instance;
    }

    private void registerCommands() {
        register(new Start());
        register(new Help());
        register(new Interval());
        register(new Words());
        setRegisteredCommands();
    }


    private void setRegisteredCommands() {
        registeredCommands = getRegisteredCommands()
                .stream()
                .map(IBotCommand::getCommandIdentifier)
                .collect(Collectors.toList());
    }

    private void sendMessage(Long chatId, String message) {
        try {
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId.toString())
                    .text(message)
                    .build();
            execute(sendMessage);
            logMessage(chatId, chatId, false, message);
        } catch (TelegramApiException e) {
            System.err.printf("Sending message error: %s", e.getMessage());
        }
    }

    private MessageType getMessageType(Update update) throws UserException {
        MessageType messageType = null;
        try {
            if (update.getMessage().getText() != null)
                messageType = (update.getMessage().getText().matches("^/\\w+")) ?
                        MessageType.COMMAND :
                        MessageType.TEXT;
            else if (update.getMessage().getDocument() != null)
                messageType = MessageType.DOCUMENT;
            if (messageType == null)
                throw new IllegalArgumentException(update.toString());
            return messageType;
        } catch (RuntimeException e) {
            System.err.printf("Invalid message type: %s", e.getMessage());
            throw new UserException("Неподдерживаемый тип сообщения");
        }
    }

    private void processText(Update update) throws TelegramApiException, IOException, UserException {
        String text = update.getMessage().getText();
        logMessage(
                update.getMessage().getChatId(),
                update.getMessage().getFrom().getId(),
                true,
                text);
        if (text.length() > TEXT_LIMIT) {
            System.err.printf("Message exceeds maximum length of %d", TEXT_LIMIT);
            throw new UserException(String.format("Сообщение превышает максимальную длину %d символов", TEXT_LIMIT));
        }
    }

    private void processDocument(Update update) {
        Document document = update.getMessage().getDocument();
        String filePath;
        try {
            GetFile getFile = GetFile.builder()
                    .fileId(document.getFileId())
                    .build();
            filePath = execute(getFile).getFilePath();
            Helper helper = Helper.getById(update.getMessage().getChatId());
            helper.setDictionary(DictionaryParser.parseText(downloadFileAsStream(filePath)));
        } catch (TelegramApiException ex) {
            System.err.printf("Error getting file: %s", ex.getMessage());
        }

    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        String command = update.getMessage().getText().substring(1);
        sendMessage(update.getMessage().getChatId(),
                String.format("Некорректная команда [%s], доступные команды: %s",
                        command, registeredCommands.toString()));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            try {
                MessageType messageType = getMessageType(update);
                switch (messageType) {
                    case COMMAND -> processInvalidCommandUpdate(update);
                    case TEXT -> processText(update);
                    case DOCUMENT -> processDocument(update);
                }
            } catch (UserException e) {
                sendMessage(update.getMessage().getChatId(), e.getMessage());
            } catch (TelegramApiException | RuntimeException | IOException e) {
                System.out.printf("Received message processing error: %s%n", e.getMessage());
                sendMessage(update.getMessage().getChatId(), "Ошибка обработки сообщения");
            }
        }
    }

    private void logMessage(Long chatId, Long userId, boolean input, String text) {
        if (text.length() > TEXT_LIMIT)
            text = text.substring(0, TEXT_LIMIT);
        System.out.printf("CHAT [%d] MESSAGE %s %d: %s%n", chatId, input ? "FROM" : "TO", userId, text);
    }

    @Override
    public String getBotUsername() {
        return botSettings.getUserName();
    }

    @Override
    public String getBotToken() {
        return botSettings.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

}
