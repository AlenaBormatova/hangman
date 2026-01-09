import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class HangmanGame {
    private final WordList wordList;
    private final Scanner scanner;

    private static final int MAX_ERRORS = 6;

    private static final String START_COMMAND = "1";
    private static final String EXIT_COMMAND = "0";

    public HangmanGame(WordList wordList, Scanner scanner) {
        this.wordList = wordList;
        this.scanner = scanner;
    }

    void runMainMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("\nПриветствую Вас. Вы меня не знаете, но я Вас знаю. Я хочу поиграть с Вами в Виселицу.");
            System.out.println("Начать новую игру?");
            System.out.println(START_COMMAND + " - да / " + EXIT_COMMAND + " - нет");
            System.out.println("\nВаш выбор:");

            String choice = scanner.nextLine();
            if (choice.equals(START_COMMAND)) {
                runGame();
                continue;
            } else if (choice.equals(EXIT_COMMAND)) {
                System.out.println("Выход из приложения.");
                break;
            }
            System.out.println("Ошибка ввода. Попробуйте ещё раз.\n");
        }
    }

    // один раунд игры
    private void runGame() {
        String word = wordList.getRandomWord();
        char[] answer = createAnswerArray(word);
        TreeSet<Character> usedLetters = new TreeSet<>();
        int errors = 0;

        while (!isGameOver(answer, errors, usedLetters, word)) {
            displayGameState(answer, usedLetters, errors);
            char letter = readAndValidateLetter(usedLetters);
            errors = processLetter(word, answer, usedLetters, letter, errors);
        }
    }

    private char[] createAnswerArray(String word) {
        char[] answer = new char[word.length()];
        Arrays.fill(answer, '_');
        return answer;
    }

    private void displayGameState(char[] answer, TreeSet<Character> usedLetters, int errors) {
        System.out.println("\nСлово: " + String.valueOf(answer));
        System.out.println("\nИспользованные буквы: " + usedLetters);
        System.out.println("\nОшибок: " + errors + " из " + MAX_ERRORS);
        HangmanDrawer.draw(errors);
    }

    private char readAndValidateLetter(TreeSet<Character> usedLetters) {
        while (true) {
            System.out.println("Введите букву: ");
            String symbol = scanner.nextLine();

            if (symbol.length() != 1) {
                System.out.println("Введите ОДНУ букву.");
                continue;
            }

            char letter = symbol.charAt(0);
            if (!isValidRussianLetter(letter)) {
                System.out.println("Введите маленькую букву кириллицы.");
                continue;
            }

            if (usedLetters.contains(letter)) {
                System.out.println("Эта буква уже была.");
                continue;
            }

            return letter;
        }
    }

    private int processLetter(String word, char[] answer, TreeSet<Character> usedLetters,
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

    private boolean isGameOver(char[] answer, int errors, TreeSet<Character> usedLetters, String word) {
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

    private boolean isValidRussianLetter(char ch) {
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