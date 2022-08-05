package com.shchepinms.telegram_bot.util;

import com.shchepinms.telegram_bot.helper.Helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserConfigManager {

    public static final String SERIALIZE_DIRECTORY_PATH =
            System.getProperty("user.dir") + File.separator + "UserSettings" + File.separator;
    public static final String SERIALIZE_FILE_SUFFIX = ".helper";

    private UserConfigManager() {}

    public static void save(Helper helper) {
        File userConfigFile = getHelperInstanceFile(helper);
        try(FileOutputStream fileOutputStream = new FileOutputStream(userConfigFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(helper);
        } catch (IOException ex) {
            System.err.println("Ошибка при сохранении в файл: " + ex.getMessage());
        }
    }

    private static File getHelperInstanceFile(Helper helper) {
        return getHelperInstanceFile(helper.getUserTelegramId());
    }

    private static File getHelperInstanceFile(long userTelegramId) {
        File helperInstanceFile = new File(SERIALIZE_DIRECTORY_PATH + userTelegramId + SERIALIZE_FILE_SUFFIX);
        provideDirectoryExist();
        return helperInstanceFile;
    }

    private static void provideDirectoryExist() {
        new File (SERIALIZE_DIRECTORY_PATH).mkdir();
    }

    public static Helper load(long userTelegramId) {
        File userConfigFile = getHelperInstanceFile(userTelegramId);
        try(FileInputStream fileInputStream = new FileInputStream(userConfigFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Helper helper = (Helper) objectInputStream.readObject();
            if (helper.isValid())
                return helper;
            else
                throw new InvalidObjectException("Invalid object: helper.isValid = false");
        }catch (IOException | ClassNotFoundException ex) {
            System.err.printf("Error loading from file %s - %s%n", userConfigFile.getPath(), ex.getMessage());
            System.err.println("New instance returned");
            return new Helper(userTelegramId);
        }
    }

    /**
     *
     * @return all telegramIds as ArrayList parsed from files from SERIAL_DIRECTORY_PATH.
     * In case of errors or target directory is empty method returns empty ArrayList.
     */
    public static List<Long> getSavedTelegramIds() {
        File directoryPath = new File(SERIALIZE_DIRECTORY_PATH);
        String[] userConfigFiles = directoryPath.list();
        List<Long> telegramIds = new ArrayList<>();
        if (userConfigFiles != null && userConfigFiles.length > 0) {
            for (String userConfigFile :
                    userConfigFiles) {
                long tgId = getTelegramIdFromFileName(userConfigFile);
                if (tgId != 0)
                    telegramIds.add(tgId);
            }
        }
        return telegramIds;
    }

    /**
     *
     * @param fileName string name of file with user configs, serialized instance Helper.class.
     *                 Consists of "user telegram id" + suffix ".helper", for example "12345678.helper"
     * @return long value parsed from fileName. This value is expected to be the telegramId of the user
     * whose Helper.class instance serialized in the file.
     * If Long.parseLong throw Exception, then result is not telegramId, method returns 0.
     */
    private static long getTelegramIdFromFileName(String fileName) {
        String telegramIdFromFileName = fileName.replace(SERIALIZE_FILE_SUFFIX, "");
        long telegramId = 0;
        try {
            telegramId = Long.parseLong(telegramIdFromFileName);
        } catch (NumberFormatException ex) {
            System.err.printf("Error parsing telegramId from filename %s %s%n" + ex.getMessage());
        }
        return telegramId;
    }
}
