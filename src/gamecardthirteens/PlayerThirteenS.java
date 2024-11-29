package gamecardthirteens;

import playerofgame.Player;
import java.util.ArrayList;
import java.util.Scanner;

// Kế thừa từ Player
public class PlayerThirteenS extends Player<CardOfThirteenS> {
	protected String getSelection;
	protected String listCardPlayed;
	private final CheckSetOfThirteenS checkSet = new CheckSetOfThirteenS();

	public PlayerThirteenS(String name) {
		super(name);
	}

	// Lựa chọn mà người chơi đã chọn
	public String getSelection(ArrayList<CardOfThirteenS> cardsPreTurn) {
		return getSelection;
	}

	// Người chơi chọn 1 trong các lựa chọn để chơi game
	public void setSelection() {
		this.getSelection = scanner.nextLine();
	}

	// Bộ mà người chơi chọn
	public String getListCardPlayed() {
		return listCardPlayed;
	}

	// Người chơi chọn bộ để đánh
	public void setListCardPlayed() {
		this.listCardPlayed = scanner.nextLine();
	}

	// Sắp xếp các lá bài trên tay
	public void sortCardsInHand() {
		this.cardsInHand = checkSet.sortCards(this.cardsInHand);
	}

	private final Scanner scanner = new Scanner(System.in);
}
