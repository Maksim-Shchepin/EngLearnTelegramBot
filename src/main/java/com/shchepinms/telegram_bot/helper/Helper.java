package com.shchepinms.telegram_bot.helper;

import com.shchepinms.telegram_bot.bot.BotProcessor;
import com.shchepinms.telegram_bot.exercise.ExerciseGenerator;
import com.shchepinms.telegram_bot.util.RelatedWords;
import com.shchepinms.telegram_bot.util.UserConfigManager;
import dictionary.Dictionary;
import dictionary.EngRusDictionary;

import java.io.*;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Helper implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Map<Long, Helper> helpers;
    public static final int MSG_INTERVAL_MIN_HOUR = 1;
    public static final int MSG_INTERVAL_MAX_HOUR = 14;
    public static final int MSG_INTERVAL_MIN_MINUTES = 15;
    public static final int MSG_INTERVAL_MAX_MINUTES = 840;

    public static final int MSG_WORDS_MIN_COUNT = 1;

    public static final int MSG_WORDS_MAX_COUNT = 20;

    private static final int DEFAULT_WORD_COUNT = 3;
    private static final int DEFAULT_MSG_INTERVAL_MINUTE = 4 * 60;
    private static final int START_OF_DAY_MINUTE = 8 * 60;
    private static final int END_OF_DAY_MINUTE = 24 * 60 - 1;




    private long userTelegramId;

    private int wordsCountPerDay;

    private int sendMessageInterval;

    transient private ScheduledExecutorService sendMsgScheduler;

    private Dictionary dictionary;




    static {
        helpers = HelpersLoader.initializeHelpers();
    }

    static class HelpersLoader {
        private static final List<Long> telegramIds;

        static {
            telegramIds = UserConfigManager.getSavedTelegramIds();
        }
        public static Map<Long, Helper> initializeHelpers() {
            Map<Long, Helper> helpers = new HashMap<>();
            if (!telegramIds.isEmpty()) {
                for (long tgId :
                        telegramIds) {
                    Helper helper = UserConfigManager.load(tgId);
                    helpers.put(tgId, helper);
                }
            }
            return helpers;
        }
    }


    public Helper(long userTelegramId) {
        this.dictionary = new EngRusDictionary();
        this.wordsCountPerDay = DEFAULT_WORD_COUNT;
        this.sendMessageInterval = DEFAULT_MSG_INTERVAL_MINUTE;
        this.userTelegramId = userTelegramId;
        this.serialize();
        helpers.put(userTelegramId, this);
    }

    public void runEducation() {
        sendMsgScheduler = new ScheduledThreadPoolExecutor(1);
        ExerciseGenerator exercise = new ExerciseGenerator(this);
        ScheduledFuture<?> future = sendMsgScheduler.scheduleAtFixedRate(exercise, 0, sendMessageInterval, TimeUnit.MINUTES);
        sendMsgScheduler.schedule(() -> {
            future.cancel(true);
            sendMsgScheduler.shutdown();
        }, 12, TimeUnit.SECONDS);
    }

    public void stopEducation() {

    }

    private void serialize() {
        UserConfigManager.save(this);
    }


    public static Helper getById(long telegramId) {
        Helper helper = helpers.get(telegramId);
        return helper != null ? helper : new Helper(telegramId);
    }

    public boolean isValid() {
        int wordsCount = this.getWordsCountPerDay();
        int interval = this.getSendMessageInterval();
        if (wordsCount < MSG_WORDS_MIN_COUNT || wordsCount > MSG_WORDS_MAX_COUNT) return false;
        if (interval < MSG_INTERVAL_MIN_HOUR || interval > MSG_INTERVAL_MAX_MINUTES) return false;
        if (this.getUserTelegramId() <= 0) return false;
        if (this.getDictionary() == null) return false;
        return true;
    }


    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.serialize();
    }

    public long getUserTelegramId() {
        return userTelegramId;
    }

    public int getWordsCountPerDay() {
        return wordsCountPerDay;
    }

    public void setWordsCountPerDay(int wordsCountPerDay) {
        this.wordsCountPerDay = wordsCountPerDay;
        this.serialize();
    }

    public int getSendMessageInterval() {
        return sendMessageInterval;
    }

    public void setSendMessageInterval(int sendMessageInterval) {
        this.sendMessageInterval = sendMessageInterval;
        this.serialize();
    }

}
