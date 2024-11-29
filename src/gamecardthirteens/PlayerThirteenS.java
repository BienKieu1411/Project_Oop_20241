package gamecardthirteens;

import playerofgame.Player;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerThirteenS extends Player<CardOfThirteenS> {
	protected String getSelection;
	protected String listCardPlayed;
	private final CheckSetOfThirteenS checkSet = new CheckSetOfThirteenS();

	public PlayerThirteenS(String name) {
		super(name);
	}

	public String getSelection(ArrayList<CardOfThirteenS> cardsPreTurn) {
		return getSelection;
	}

	public void setSelection() {
		this.getSelection = scanner.nextLine();
	}

	public String getListCardPlayed() {
		return listCardPlayed;
	}

	public void setListCardPlayed() {
		this.listCardPlayed = scanner.nextLine();
	}

	public void sortCardsInHand() {
		this.cardsInHand = checkSet.sortCards(this.cardsInHand);
	}

	private final Scanner scanner = new Scanner(System.in);
}
