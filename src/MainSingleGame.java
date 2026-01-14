import java.util.Scanner;

public class MainSingleGame {

    public static void main(String[] args) {
        try {
            Dictionary dictionary = new Dictionary("words.txt", 5);
            try (Scanner scanner = new Scanner(System.in)) {
                HangmanGame game = new HangmanGame(dictionary, scanner);
                game.runGame();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to initialize dictionary: " + e.getMessage());
        }
    }
}