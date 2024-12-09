package gameplay;

import javafx.stage.Stage;
import java.util.List;

public class GameController {
    public static void startGame(Stage stage, String game, int playerCount, boolean withPlayer, List<String> playerNames) {
        new GamePlay(stage, playerCount, withPlayer, playerNames, game);
    }
}
