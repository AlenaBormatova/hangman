import java.util.Scanner;

public class MainSingleGame extends GameLauncher {

    public static void main(String[] args) {
        new MainSingleGame().launchGame("words.txt", 5);
    }

    @Override
    protected void runGameLogic(HangmanGame game, Scanner scanner) {
        game.runGame();
    }
}