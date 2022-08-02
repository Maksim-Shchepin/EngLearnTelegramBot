package com.shchepinms.telegram_bot.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotSettings {
    public static String FILE_NAME = "config.properties";
    private static BotSettings instance;
    private Properties properties;
    private String token;
    private String userName;

    private BotSettings() {}

    public static BotSettings getInstance() {
        if (instance == null)
            instance = new BotSettings();
        return instance;
    }

    {
        properties = new Properties();
        try {
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(FILE_NAME)) {
                properties.load(stream);
            } catch (IOException ex) {
                throw new RuntimeException(String.format("Error loading properties file '%s'", FILE_NAME));
            }
            token = properties.getProperty("token");
            if (token == null) throw new RuntimeException("Token is null after getProperty");
            userName = properties.getProperty("username");
            if (userName == null) throw new RuntimeException("Username is null after getProperty");
        } catch (RuntimeException e) {
            throw new RuntimeException("Bot initialize error: " + e.getMessage());
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
