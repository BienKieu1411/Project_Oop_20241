package gamecardthirteens;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class ThirteenS extends RulesOfThirteenS {

    public ThirteenS(AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames) {
		SetGameOfThirteenS setGame = new SetGameOfThirteenS();
		// số người chơi
		super.numberOfPlayer = playerCount;
		setGame.setNumberOfPlayer(playerCount);
		// thêm người chơi
		super.playersThirteenS = setGame.addPlayer(withPlayer, playerNames);
		// chia bài
		super.deckOfThirteenS = setGame.dealCard(gameRoot, deckOfThirteenS);
		// nếu có người chơi sở hữu 4 quân 2 trên tay thì sẽ thắng luôn và không xếp hạng các người chơi còn lại
		new DisplayPlayerCards(gameRoot, this.playersThirteenS, numberOfPlayer);
	}
}