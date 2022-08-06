package dictionary;

import com.shchepinms.telegram_bot.util.RelatedWords;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDictionary implements Dictionary, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected List<RelatedWords> initialWords;

    protected List<RelatedWords> currentWords;
    protected List<RelatedWords> learnedWords;

    public AbstractDictionary() {
        this.initialWords = new ArrayList<>();
        this.learnedWords = new ArrayList<>();
        this.currentWords = new ArrayList<>();
    }

    @Override
    public void removeAllLearned() {
        initialWords.clear();
    }

    @Override
    public void removeAllUnlearned() {
        learnedWords.clear();
    }

    @Override
    public void removePairFromLearned(RelatedWords words) {
        try {
            learnedWords.remove(words);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removePairFromUnlearned(RelatedWords words) {
        try {
            initialWords.remove(words);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
