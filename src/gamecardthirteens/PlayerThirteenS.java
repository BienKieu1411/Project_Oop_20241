package gamecardthirteens;

import deckofcards.Card;
import playerofgame.Player;
import rulesofgame.CheckSet;
import java.util.ArrayList;
import java.util.Scanner;

// Kế thừa từ Player
public class PlayerThirteenS extends Player {
	protected String getSelection;
	protected ArrayList<Card> listCardPlayed;
	private final CheckSet CHECK_SET = new CheckSet();

	public PlayerThirteenS(String name) {
		super(name);
	}

	// Lựa chọn mà người chơi đã chọn
	public String getSelection(ArrayList<Card> cardsPreTurn) {
		return getSelection;
	}

	// Người chơi chọn 1 trong các lựa chọn để chơi game
	public void setSelection(String selection) {
		this.getSelection = selection;
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
