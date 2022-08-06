package dictionary;

import com.shchepinms.telegram_bot.util.RelatedWords;

import java.util.ArrayList;
import java.util.List;

public class EngRusDictionary extends AbstractDictionary {


    private RelatedWords getNewPair() {
        RelatedWords firstFromInitialWords = new RelatedWords();
        if (initialWords.size() > 0) {
            firstFromInitialWords = initialWords.get(0);
            initialWords.remove(0);
        }
        return firstFromInitialWords;
    }

    @Override
    public List<RelatedWords> getNewWords(int wordsCount) {
        if (currentWords.size() == 0) {
            for (int i = 0; i < wordsCount; i++) {
                currentWords.add(getNewPair());
            }
        }
        return new ArrayList<>(currentWords);
    }

    @Override
    public List<RelatedWords> getLearnedWords(int wordsCount) {
        return learnedWords;
    }

    @Override
    public void addNewWords(List<RelatedWords> words) {

    }

    @Override
    public void addNewPair(RelatedWords words) {
        if (!initialWords.contains(words))
            initialWords.add(words);
    }


    private void movePairToLearned(RelatedWords words) {

    }


    private void backPairToUnlearned(RelatedWords words) {

    }
}
