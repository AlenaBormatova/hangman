import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class HangmanGame {
    private static final int MAX_ERRORS = 6;
    private final Dictionary dictionary;
    private final Scanner scanner;

    private String word;
    private char[] answer;
    private Set<Character> usedLetters = new TreeSet<>();
    private int errors;

    public HangmanGame(Dictionary dictionary, Scanner scanner) {
        this.dictionary = dictionary;
        this.scanner = scanner;
    }

    // один раунд игры
    void runGame() {
        word = dictionary.getRandomWord();
        answer = createAnswerArray(word);
        usedLetters.clear();
        errors = 0;

        while (!isGameOver()) {
            displayGameState();
            char letter = inputUnusedRussianLetter();
            processLetter(letter);
        }
    }

    private char[] createAnswerArray(String word) {
        char[] answerArray = new char[word.length()];
        Arrays.fill(answerArray, '_');
        return answerArray;
    }

    private void displayGameState() {
        System.out.println("\nСлово: " + String.valueOf(answer));
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

        if (word.indexOf(letter) != -1) { // все вхождения буквы
            revealLetterOccurrences(letter);
            System.out.println("Буква угадана верно!");
        } else {
            System.out.println("Такой буквы нет!");
            errors++;
        }
        System.out.println("\n" + "-".repeat(50));
    }

    private void revealLetterOccurrences(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                answer[i] = letter;
            }
        }
    }

    private boolean isGameOver() {
        if (errors >= MAX_ERRORS) {
            displayGameState();
            System.out.println("Вы проиграли! Загаданное слово: " + word);
            return true;
        }
        if (isWordGuessed()) {
            System.out.println("Поздравляем! Вы отгадали слово: " + word);
            return true;
        }
        return false;
    }

    private boolean isRussianLetter(char ch) {
        return (ch >= 'а' && ch <= 'я') || ch == 'ё';
    }

    private boolean isWordGuessed() {
        for (char c : answer) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }
}