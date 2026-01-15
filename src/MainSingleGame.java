import java.nio.file.Path;
import java.util.Scanner;

public class MainSingleGame {

    public static void main(String[] args) {
        Path dictionaryPath = Path.of("words.txt").toAbsolutePath();
        try {
            Dictionary dictionary = new Dictionary("words.txt", 5);
            try (Scanner scanner = new Scanner(System.in)) {
                HangmanGame game = new HangmanGame(dictionary, scanner);
                game.runGame();
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
}