import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class HangmanGame {
    private static final int MAX_ERRORS = 6;
    private final Dictionary dictionary;
    private final Scanner scanner;

    private SecretWord secretWord;
    private Set<Character> usedLetters = new TreeSet<>();
    private int errors;

    public HangmanGame(Dictionary dictionary, Scanner scanner) {
        this.dictionary = dictionary;
        this.scanner = scanner;
    }

    // один раунд игры
    void runGame() {
        secretWord = new SecretWord(dictionary.getRandomWord());
        usedLetters.clear();
        errors = 0;

        while (!isGameOver()) {
            displayGameState();
            char letter = inputUnusedRussianLetter();
            processLetter(letter);
        }
        announceGameResult();
    }

    private void displayGameState() {
        System.out.println("\nСлово: " + secretWord.getDisplayString());
        System.out.println("\nИспользованные буквы: " + usedLetters);
        System.out.printf("\nОшибок: %d из %d  %n", errors, MAX_ERRORS);
        HangmanDrawer.draw(errors);
    }

    private char inputUnusedRussianLetter() {
        while (true) {
            System.out.println("Введите букву: ");
            String symbol = scanner.nextLine();

            if (symbol.length() != 1) {
                System.out.println("Введите ОДНУ букву.");
                continue;
            }

            char letter = Character.toLowerCase(symbol.charAt(0));
            if (!isRussianLetter(letter)) {
                System.out.println("Введите русскую букву.");
                continue;
            }

            if (usedLetters.contains(letter)) {
                System.out.println("Эта буква уже была.");
                continue;
            }

            return letter;
        }
    }

    private void processLetter(char letter) {
        usedLetters.add(letter);

        if (secretWord.letterIsInWord(letter)) {
            secretWord.revealLetterOccurrences(letter);
            System.out.println("Буква угадана верно!");
        } else {
            System.out.println("Такой буквы нет!");
            errors++;
        }
        System.out.println("\n" + "-".repeat(50));
    }

    private boolean isGameOver() {
        return isLose() || isWin();
    }

    private boolean isLose() {
        return errors >= MAX_ERRORS;
    }

    private boolean isWin() {
        return secretWord.isWordGuessed();
    }

    private void announceGameResult() {
        displayGameState();
        if (isLose()) {
            System.out.println("Вы проиграли! Загаданное слово: " + secretWord.getFullWordString());
        } else {
            System.out.println("Поздравляем! Вы отгадали слово: " + secretWord.getFullWordString());
        }
    }

    private boolean isRussianLetter(char ch) {
        return (ch >= 'а' && ch <= 'я') || ch == 'ё';
    }
}