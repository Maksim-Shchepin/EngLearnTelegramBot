package dictionary;

import com.shchepinms.telegram_bot.util.RelatedWords;

import java.util.List;

public interface Dictionary {

    List<RelatedWords> getNewWords(int wordsCount);

    List<RelatedWords> getLearnedWords(int wordsCount);

    void addNewPair(RelatedWords words);

    void addNewWords(List<RelatedWords> words);

    void removePairFromLearned(RelatedWords words);

    void removePairFromUnlearned(RelatedWords words);

    void removeAllLearned();

    void removeAllUnlearned();
}
