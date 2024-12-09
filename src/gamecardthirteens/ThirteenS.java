package gamecardthirteens;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logicgame.TurnOfGame;

import java.util.List;

public class ThirteenS extends TurnOfGame {
	public ThirteenS(Stage stage, AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames, String gameName) {
		turnGame(stage, gameRoot, playerCount, withPlayer, playerNames, gameName);
	}
}