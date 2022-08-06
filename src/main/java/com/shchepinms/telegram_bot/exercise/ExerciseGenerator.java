package com.shchepinms.telegram_bot.exercise;

import com.shchepinms.telegram_bot.bot.BotProcessor;
import com.shchepinms.telegram_bot.helper.Helper;
import com.shchepinms.telegram_bot.util.RelatedWords;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;

public class ExerciseGenerator implements Runnable {

    private final Helper helper;

    public ExerciseGenerator(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void run() {
        StringBuilder exercise = new StringBuilder("Слова на сегодня: \n\n");
            helper.getDictionary().getNewWords(helper.getWordsCountPerDay()).forEach(x -> exercise
                .append(x.getEng())
                .append(" <---> ")
                .append(x.getRus())
                .append("\n"));
        exercise.append("\n");
        BotProcessor.getInstance().sendMessage(helper.getUserTelegramId(), exercise.toString());
    }
}
