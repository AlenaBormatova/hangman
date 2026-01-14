import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class HangmanGame {
    private static final int MAX_ERRORS = 6;
    private final Dictionary dictionary;
    private final Scanner scanner;

    public HangmanGame(Dictionary dictionary, Scanner scanner) {
        this.dictionary = dictionary;
        this.scanner = scanner;
    }

    // один раунд игры
    void runGame() {
        String word = dictionary.getRandomWord();
        char[] answer = createAnswerArray(word);
        Set<Character> usedLetters = new TreeSet<>();
        int errors = 0;

        while (!isGameOver(answer, errors, usedLetters, word)) {
            displayGameState(answer, usedLetters, errors);
            char letter = inputUnusedRussianLetter(usedLetters);
            errors = processLetter(word, answer, usedLetters, letter, errors);
        }
    }

    private char[] createAnswerArray(String word) {
        char[] answer = new char[word.length()];
        Arrays.fill(answer, '_');
        return answer;
    }

    private void displayGameState(char[] answer, Set<Character> usedLetters, int errors) {
        System.out.println("\nСлово: " + String.valueOf(answer));
        System.out.println("\nИспользованные буквы: " + usedLetters);
        System.out.printf("\nОшибок: %d из %d  %n", errors, MAX_ERRORS);
        HangmanDrawer.draw(errors);
    }

    private char inputUnusedRussianLetter(Set<Character> usedLetters) {
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

    private int processLetter(String word, char[] answer, Set<Character> usedLetters,
                              char letter, int currentErrors) {
        usedLetters.add(letter);

        if (word.indexOf(letter) != -1) { // все вхождения буквы
            revealLetterOccurrences(word, answer, letter);
            System.out.println("Буква угадана верно!");
        } else {
            System.out.println("Такой буквы нет!");
            currentErrors++;
        }
        System.out.println("\n" + "-".repeat(50));
        return currentErrors;
    }

    private void revealLetterOccurrences(String word, char[] answer, char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                answer[i] = letter;
            }
        }
    }

    private boolean isGameOver(char[] answer, int errors, Set<Character> usedLetters, String word) {
        if (errors >= MAX_ERRORS) {
            displayGameState(answer, usedLetters, errors);
            System.out.println("Вы проиграли! Загаданное слово: " + word);
            return true;
        }
        if (isWordGuessed(answer)) {
            System.out.println("Поздравляем! Вы отгадали слово: " + word);
            return true;
        }
        return false;
    }

    private boolean isRussianLetter(char ch) {
        return (ch >= 'а' && ch <= 'я') || ch == 'ё';
    }

    private boolean isWordGuessed(char[] answer) {
        for (char c : answer) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }
}