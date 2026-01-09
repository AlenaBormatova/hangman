import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            WordList dictionary = new WordList("words.txt");
            Scanner scanner = new Scanner(System.in);
            HangmanGame game = new HangmanGame(dictionary, scanner);
            game.runMainMenu();
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл слов.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}