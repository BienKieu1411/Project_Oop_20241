package gamecardthirteenn;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logicgame.TurnOfGame;

import java.util.List;

public class ThirteenN extends TurnOfGame {
    public ThirteenN(Stage stage, AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames, String gameName) {
        turnGame(stage, gameRoot, playerCount, withPlayer, playerNames, gameName);
    }
}
