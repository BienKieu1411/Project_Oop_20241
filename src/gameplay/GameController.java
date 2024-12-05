package gameplay;

import gamecardthirteens.GamePlayThirteenS;
import javafx.stage.Stage;
import java.util.List;

public class GameController {
    public static void startGame(Stage stage, String game, int playerCount, boolean withPlayer, List<String> playerNames) {
        System.out.println("Starting game: " + game);
        System.out.println("Mode: " + (withPlayer ? "With Player" : "With Bot"));
        System.out.println("Player Count: " + playerCount);
        System.out.println(playerNames);
        switch (game){
            case "ThirteenS":
                GamePlayThirteenS thirteenS = new GamePlayThirteenS(stage, playerCount, withPlayer, playerNames);
                break;
        }
    }
}
