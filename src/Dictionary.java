import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dictionary {

    private final List<String> words;
    private final Random random = new Random();

    public Dictionary(String fileName, int minWordLength) {
        this.words = initializeFromFile(fileName, minWordLength);
    }

    private List<String> initializeFromFile(String fileName, int minWordLength) {
        List<String> rawLines;
        try {
            rawLines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("File not found or unreadable: " + fileName, e);
        }

        List<String> filtered = new ArrayList<>();

        for (String line : rawLines) {
            String word = line.trim().toLowerCase();
            if (word.length() >= minWordLength) {
                filtered.add(word);
            }
        }

        if (filtered.isEmpty()) {
            throw new IllegalArgumentException("Dictionary is empty: " + fileName);
        }

        return filtered;
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}