package com.shchepinms.telegram_bot.util;

class RelatedWords {
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
}
