import java.util.*;

public class Main {

    private static final String[] worlds = {"кефир", "спагетти", "тефтели", "курица", "картошка", "котлета"};

    private static final Scanner scanner = new Scanner(System.in);

    static int maxErrors = 6;

    public static void main(String[] args) {
        runMainMenu();
    }

    // меню приложения
    private static void runMainMenu() {
        while (true) {
            System.out.println("\nПриветствую Вас. Вы меня не знаете, но я Вас знаю. Я хочу поиграть с Вами в Виселицу\n");
            System.out.println("Начать новую игру?");
            System.out.println("1 - да");
            System.out.println("0 - нет");
            System.out.println("\nВаш выбор:");

            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                runGame();
            } else if (choice.equals("0")) {
                System.out.println("Выход из приложения.");
                break;
            } else {
                System.out.println("Ошибка ввода. Попробуйте ещё раз.\n");
            }
        }
    }

    // один раунд игры
    private static void runGame() {
        String world = getRandomWorld(); // случайное слово

        char[] answer = new char[world.length()]; // массив для отображения прогресса
        Arrays.fill(answer, '_');

        Set<Character> usedLetters = new HashSet<>(); // множество использованных букв
        int errors = 0; // счётчик ошибок

        while (true) {
            System.out.println("\nСлово: " + String.valueOf(answer));
            System.out.println("\nИспользованные буквы: " + usedLetters);
            System.out.println("\nОшибок: " + errors + " из " + maxErrors);
            drawHangman(errors);

            if (errors >= maxErrors) {
                System.out.println("Вы проиграли! Загаданное слово: " + world);
                break;
            }

            if (isWorldGuessed(answer)) {
                System.out.println("Поздравляем! Вы отгадали слово: " + world);
                break;
            }

            System.out.println("Введите букву: ");
            String symbol = scanner.nextLine();

            if (symbol.length() != 1) {
                System.out.println("Введите ОДНУ букву.");
                continue;
            }

            char ch = symbol.charAt(0);

            // только маленькие русские буквы
            if (ch < 'а' || ch > 'я') {
                System.out.println("Введите маленькую букву кириллицы.");
                continue;
            }

            if (usedLetters.contains(ch)) {
                System.out.println("Эта буква уже была.");
                continue;
            }

            usedLetters.add(ch);

            if (world.indexOf(ch) != -1) { // все вхождения буквы
                for (int i = 0; i < world.length(); i++) {
                    if (world.charAt(i) == ch) {
                        answer[i] = ch;
                    }
                }
                System.out.println("Буква угадана верно!");
            } else {
                errors++;
                System.out.println("Такой буквы нет!");
            }
        }
    }

    // выбор случайного слова
    private static String getRandomWorld() {
        Random random = new Random();
        return worlds[random.nextInt(worlds.length)];
    }

    // отгадано ли слово
    private static boolean isWorldGuessed(char[] answer) {
        for (char c : answer) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    // состояние виселицы
    private static void drawHangman(int errors) {
        System.out.println(" +---+");
        System.out.println(" |   |");

        if (errors >= 1) System.out.println(" O   |");
        else System.out.println("     |");
        if (errors == 2) System.out.println(" |   |");
        else if (errors == 3) System.out.println("/|   |");
        else if (errors >= 4) System.out.println("/|\\  |");
        else System.out.println("     |");
        if (errors == 5) System.out.println("/    |");
        else if (errors >= 6) System.out.println("/ \\  |");
        else System.out.println("     |");

        System.out.println("     |");
        System.out.println("=========\n");
    }
}