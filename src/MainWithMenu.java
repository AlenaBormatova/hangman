import java.nio.file.Path;
import java.util.Scanner;

public class MainWithMenu {

    private static final String START_COMMAND = "1";
    private static final String EXIT_COMMAND = "0";

    private static final String RULES = """
            
            🎭 ПРАВИЛА ИГРЫ:
            ┌─────────────────────────────────────┐
            │ Я загадал слово из словаря.         │
            │ У тебя 6 попыток угадать все буквы. │
            │ Пиши ПО ОДНОЙ русской букве!        │
            │           НЕ СПЕШИ...               │
            │    6 ошибок = ИГРА ОКОНЧЕНА         │
            └─────────────────────────────────────┘
            """;

    public static void main(String[] args) {
        Path dictionaryPath = Path.of("words.txt").toAbsolutePath();
        try {
            Dictionary dictionary = new Dictionary("words.txt", 5);
            try (Scanner scanner = new Scanner(System.in)) {
                HangmanGame game = new HangmanGame(dictionary, scanner);
                while (true) {
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("\nПриветствую Вас. Вы меня не знаете, но я Вас знаю. Я хочу поиграть с Вами в Виселицу.");
                    System.out.println(RULES);
                    System.out.println("Начать новую игру?");
                    System.out.printf("%s - да / %s - нет  %n", START_COMMAND, EXIT_COMMAND);
                    System.out.println("\nВаш выбор:");

                    String choice = scanner.nextLine();
                    if (choice.equals(START_COMMAND)) {
                        game.runGame();
                        continue;
                    } else if (choice.equals(EXIT_COMMAND)) {
                        System.out.println("Выход из приложения.");
                        break;
                    }
                    System.out.println("Ошибка ввода. Попробуйте ещё раз.\n");
                }
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