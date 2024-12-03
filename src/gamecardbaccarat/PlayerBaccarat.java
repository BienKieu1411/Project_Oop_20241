package gamecardbaccarat;

import playerofgame.Player;

public class PlayerBaccarat extends Player<CardOfBaccarat> {
	private int moneyPlayer;// Số tiền của người chơi

	// Constructor
	public PlayerBaccarat() {
		super("Player");
		this.moneyPlayer = 0;
	}

	// Thêm tiền cho người chơi
	public void addMoneyPlayer(int moneyPlayer) {
		this.moneyPlayer += moneyPlayer;
	}

	// Tìm lá bài cao nhất trong tay người chơi
	public CardOfBaccarat biggestCardInHand() {
		CardOfBaccarat card = cardsInHand.getFirst();
		for (int i = 1; i < cardsInHand.size(); i++) {
			CardOfBaccarat cardinhand = cardsInHand.get(i);
			if (cardinhand.compareCard(card) != 0)
				card = cardinhand;
		}
		return card;
	}

	// In ra người chơi + các lá bài
	public void printPlayer() {
		System.out.println("Player " + this.getNameOfPlayer() + " has " + moneyPlayer + "$");
	}

	// Geter và Seter Money
	public int getMoneyPlayer() {
		return moneyPlayer;
	}

	public void setMoneyPlayer(int moneyPlayer) {
		this.moneyPlayer = moneyPlayer;
	}
}