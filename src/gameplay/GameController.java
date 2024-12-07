package gameplay;

import gamecardbaccarat.GamePlayBaccarat;
import gamecardthirteens.GamePlayThirteenS;
import javafx.stage.Stage;
import java.util.List;

public class GameController {
    public static void startGame(Stage stage, String game, int playerCount, boolean withPlayer, List<String> playerNames) {
        switch (game){
            case "ThirteenS":
                GamePlayThirteenS thirteenS = new GamePlayThirteenS(stage, playerCount, withPlayer, playerNames);
                break;
            case "Baccarat":
                GamePlayBaccarat gamePlayBaccarat = new GamePlayBaccarat(stage, playerCount, withPlayer, playerNames);
        }
    }
}
