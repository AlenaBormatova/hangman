import java.nio.file.Path;
import java.util.Scanner;

public abstract class GameLauncher {
    protected static final Path DICTIONARY_PATH = Path.of("words.txt").toAbsolutePath();
    protected static final int MIN_WORD_LENGTH = 5;

    protected void launchGame() {
        try {
            Dictionary dictionary = new Dictionary(DICTIONARY_PATH.toString(), MIN_WORD_LENGTH);
            try (Scanner scanner = new Scanner(System.in)) {
                HangmanGame game = new HangmanGame(dictionary, scanner);
                runGameLogic(game, scanner);
            }
        } catch (IllegalArgumentException e) {
            System.err.printf("""
                    Не удалось открыть файл со словами:
                    Путь: %s
                    Причина: %s
                    
                    Работа программы завершена.
                    """, DICTIONARY_PATH, e.getMessage());
        }
    }

    protected abstract void runGameLogic(HangmanGame game, Scanner scanner);
}