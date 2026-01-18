import java.util.Arrays;

public class SecretWord {
    private final String word;
    private final char[] answer;

    public SecretWord(String word) {
        this.word = word;
        this.answer = createAnswerArray(word);
    }

    public String getDisplayString() {
        return String.valueOf(answer);
    }

    public boolean letterIsInWord(char letter) {
        return word.indexOf(letter) != -1;
    }

    public void revealLetterOccurrences(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                answer[i] = letter;
            }
        }
    }

    public boolean isWordGuessed() {
        for (char c : answer) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public String getFullWordString() {
        return word;
    }

    private char[] createAnswerArray(String word) {
        char[] answerArray = new char[word.length()];
        Arrays.fill(answerArray, '_');
        return answerArray;
    }
}