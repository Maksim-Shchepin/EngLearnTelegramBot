package com.shchepinms.telegram_bot.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class RelatedWords implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String russianWord;
    private String englishWord;

    public RelatedWords() {
        this.englishWord = null;
        this.russianWord = null;
    }

    public RelatedWords(String russianWord, String englishWord) {
        this.russianWord = russianWord;
        this.englishWord = englishWord;
    }

    public String getRus() {
        return russianWord;
    }

    public void setRus(String russianWord) {
        this.russianWord = russianWord;
    }

    public String getEng() {
        return englishWord;
    }

    public void setEng(String englishWord) {
        this.englishWord = englishWord;
    }

    public boolean isReady() {
        return russianWord != null && englishWord != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelatedWords that = (RelatedWords) o;
        return Objects.equals(russianWord, that.russianWord) && Objects.equals(englishWord, that.englishWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(russianWord, englishWord);
    }
}
