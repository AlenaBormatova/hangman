import java.nio.file.Path;
import java.util.Scanner;

public abstract class GameLauncher {

    protected void launchGame(String dictionaryFileName, int minWordLength) {
        Path dictionaryPath = Path.of(dictionaryFileName).toAbsolutePath();
        try {
            Dictionary dictionary = new Dictionary(dictionaryPath.toString(), minWordLength);
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
                    """, dictionaryPath, e.getMessage());
        }
    }

    protected abstract void runGameLogic(HangmanGame game, Scanner scanner);
}