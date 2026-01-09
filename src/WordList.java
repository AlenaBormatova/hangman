import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordList {

    private final List<String> words;
    private final Random random = new Random();
    private static final int MIN_WORD_LENGTH = 5;

    public WordList(String fileName) throws IOException {
        this.words = initializeFromFile(fileName);
    }

    private List<String> initializeFromFile(String fileName) throws IOException {
        List<String> rawLines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        List<String> filtered = new ArrayList<>();

        for (String line : rawLines) {
            String word = line.trim().toLowerCase();
            if (word.length() >= MIN_WORD_LENGTH) {
                filtered.add(word);
            }
        }

        if (filtered.isEmpty()) {
            throw new IllegalStateException("Словарь пуст: " + fileName);
        }

        return filtered;
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}