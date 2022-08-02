package com.shchepinms.telegram_bot;

import com.shchepinms.telegram_bot.bot.BotProcessor;

public class Main {
    public static void main(String[] args) {
        try {
            BotProcessor bot = BotProcessor.getInstance();
            System.out.println("Bot started :)");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
