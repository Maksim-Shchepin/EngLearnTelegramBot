package com.shchepinms.telegram_bot.helper;

import java.util.Map;
import java.util.TreeMap;

public class Helper {

    private static final Map<Long, Helper> helpers;
    private static final int DEFAULT_WORD_COUNT = 3;
    private static final int DEFAULT_MSG_INTERVAL = 4 * 60 * 60 * 1000;
    private static final int DEFAULT_FIRST_MESSAGE_TIME = 8 * 60 * 60 * 1000;



    private final long userTelegramId;

    private int wordCountPerDay;

    private int sendMessageInterval;

    private Map<String, String> dictionary;




    static {
        // loading from file or Else
        helpers = new TreeMap<>();
    }


    public Helper(long userTelegramId) {
        this.dictionary = new TreeMap<>();
        this.wordCountPerDay = DEFAULT_WORD_COUNT;
        this.sendMessageInterval = DEFAULT_MSG_INTERVAL;
        this.userTelegramId = userTelegramId;
        helpers.put(userTelegramId, this);
    }



    public static Helper getById(long telegramId) {
        return helpers.entrySet()
                .stream()
                .filter(entry -> entry.getKey() == telegramId)
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(new Helper(telegramId));
    }


    public Map<String, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public long getUserTelegramId() {
        return userTelegramId;
    }

    public int getWordCountPerDay() {
        return wordCountPerDay;
    }

    public void setWordCountPerDay(int wordCountPerDay) {
        this.wordCountPerDay = wordCountPerDay;
    }

    public int getSendMessageInterval() {
        return sendMessageInterval;
    }

    public void setSendMessageInterval(int sendMessageInterval) {
        this.sendMessageInterval = sendMessageInterval;
    }
}
