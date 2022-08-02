package com.shchepinms.telegram_bot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryParser {

    private static final Pattern ENG = Pattern.compile("[a-zA-Z]+");
    private static final Pattern RUS = Pattern.compile("[а-яА-Я]+");

    private DictionaryParser() {}

    public static Map<String, String> parseText(InputStream stream) {
        Map<String, String> result = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            while (reader.ready()) {
                RelatedWords relatedWords = getRelatedWordsFromLine(reader.readLine());
                if (relatedWords.isReady()) {
                    result.put(relatedWords.getEng(), relatedWords.getRus());
                    System.out.printf("**Parser** add new -> ENG = %s, RUS = %s%n", relatedWords.getEng(), relatedWords.getRus());
                }
            }
        }catch (IOException e) {
            System.err.printf("Error reading file: %s", e.getMessage());
        }
        return result;
    }

    private static RelatedWords getRelatedWordsFromLine(String line) {
        if (line == null) return new RelatedWords();
        RelatedWords words = new RelatedWords();
        words.setRus(russianWordFrom(line));
        words.setEng(englishWordFrom(line));
        return words;
    }

    private static String russianWordFrom(String line) {
        Matcher rusFind = RUS.matcher(line);
        String russianWord = null;
        if (rusFind.find())
            russianWord = line.substring(rusFind.start(), rusFind.end());
        return russianWord;
    }

    private static String englishWordFrom(String line) {
        Matcher engFind = ENG.matcher(line);
        String englishWord = null;
        if (engFind.find()) {
            englishWord = line.substring(engFind.start(), engFind.end());
        }
        return englishWord;
    }
}
