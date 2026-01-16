public class HangmanDrawer {

    private static final String[] HANGMAN_STATES = {
            // 0 ошибок
            """
             +---+
             |   |
                 |
                 |
                 |
                 |
            """,

            // 1 ошибка
            """
             +---+
             |   |
             O   |
                 |
                 |
                 |
            """,

            // 2 ошибки
            """
             +---+
             |   |
             O   |
             |   |
                 |
                 |
            """,

            // 3 ошибки
            """
             +---+
             |   |
             O   |
            /|   |
                 |
                 |
            """,

            // 4 ошибки
            """
             +---+
             |   |
             O   |
            /|\\  |
                 |
                 |
            """,

            // 5 ошибок
            """
             +---+
             |   |
             O   |
            /|\\  |
            /    |
                 |
            """,

            // 6 ошибок
            """
             +---+
             |   |
             O   |
            /|\\  |
            / \\  |
                 |
            """
    };

    public static void draw(int errors) {
        System.out.print(HANGMAN_STATES[errors]);
    }
}