import java.util.Scanner;

public class MainSingleGame extends GameLauncher {

    public static void main(String[] args) {
        new MainSingleGame().launchGame();
    }

    @Override
    protected void runGameLogic(HangmanGame game, Scanner scanner) {
        game.runGame();
    }
}