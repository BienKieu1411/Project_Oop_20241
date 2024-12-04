package gamecardbaccarat;

import deckofcards.Card;
import rulesofgame.Rules;
import java.util.ArrayList;

public class RulesOfBaccarat extends Rules {
	protected int numberOfPlayer;
	protected ArrayList<PlayerBaccarat> playersBaccarat = new ArrayList<>();

	// Tìm ra người thắng cuộc
	public String winnerBaccarat() {
		int index = 0;
		int maxPoint = 0;
		// So sánh điểm
		for (int i = 0; i < numberOfPlayer; i++) {
			int point = getScoreOfPlayer(playersBaccarat.get(i));
			if (point > maxPoint) {
				maxPoint = point;
				index = i;
			}
		}
		// Cộng tiền cho người thắng
		for (int i = 0; i < numberOfPlayer; ++i) {
			if (i != index) {
				playersBaccarat.get(index).addMoneyPlayer(playersBaccarat.get(i).getMoneyPlayer());
				playersBaccarat.get(i).setMoneyPlayer(0);
			}
		}
		return playersBaccarat.get(index).getNameOfPlayer();

	}

	// Tính điểm của người chơi
	public int getScoreOfPlayer(PlayerBaccarat player) {
		Card biggestCard = player.biggestCardInHand();
		int scoreOfPlayer = 0;
		for (int i = 0; i < 3; ++i) {
			scoreOfPlayer += (player.getCardsInHand().get(i)).getRank();
			if (scoreOfPlayer > 10)
				scoreOfPlayer -= 10;
		}
		// Tính điểm theo trọng số : điểm cao nhất -> lá bài có chất cao nhất -> lá bài có điểm cao nhất
		return 100 * scoreOfPlayer + 10 * biggestCard.getSuit() + biggestCard.getRank();
	}


}
