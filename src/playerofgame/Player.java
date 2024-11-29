package playerofgame;

import java.util.ArrayList;
import java.util.Iterator;

public class Player<T> {
	protected String nameOfPlayer;
	protected ArrayList<T> cardsInHand;

	public Player(String nameOfPlayer) {
		this.nameOfPlayer = nameOfPlayer;
		this.cardsInHand = new ArrayList<>();
	}

	public ArrayList<T> getCardsInHand() {
		return this.cardsInHand;
	}

	public void addCard(T card) {
		this.cardsInHand.add(card);
	}

	public void dropCard(T card) {
		Iterator<T> iterator = cardsInHand.iterator();
		while (iterator.hasNext()) {
			T currentCard = iterator.next();
			if (currentCard.equals(card)) {
				iterator.remove();
				break;
			}
		}
	}

	public void printCardInHand() {
		System.out.println(nameOfPlayer + "'s cards in hand:");
		for (T card : cardsInHand) {
			System.out.print(card.toString() + " ");
		}
		System.out.println();
	}

	public String getNameOfPlayer() {
		return nameOfPlayer;
	}

	public void setNameOfPlayer(String nameOfPlayer) {
		this.nameOfPlayer = nameOfPlayer;
	}
}
