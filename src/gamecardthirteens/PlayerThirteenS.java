package gamecardthirteens;

import deckofcards.Card;
import playerofgame.Player;
import rulesofgame.CheckSet;
import java.util.ArrayList;

// Kế thừa từ Player
public class PlayerThirteenS extends Player {
	protected ArrayList<Card> listCardPlayed = new ArrayList<>();
	private final CheckSet CHECK_SET = new CheckSet();

	public PlayerThirteenS(String name, boolean isBot) {
		super(name, isBot);
	}

	// Lựa chọn mà người chơi đã chọn
	public String getSelection(ArrayList<Card> cardsPreTurn) {
		return "Skip";
	}

	// Bộ mà người chơi chọn
	public ArrayList<Card> getListCardPlayed() {
		return listCardPlayed;
	}

	// Người chơi chọn bộ để đánh
	public void setListCardPlayed(ArrayList<Card> listCardPlayed) {
		this.listCardPlayed = listCardPlayed;
	}

	// Sắp xếp các lá bài trên tay
	public void sortCardsInHand() {
		this.cardsInHand = CHECK_SET.sortCards(this.cardsInHand);
	}

}
