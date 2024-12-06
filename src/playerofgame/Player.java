package playerofgame;

import deckofcards.Card;

import java.util.ArrayList;
import java.util.Iterator;

// Dùng template để dễ dàng tạo người chơi của game bất kì, dễ dàng mở rộng
public class Player {
	protected String nameOfPlayer;
	protected ArrayList<Card> cardsInHand;

	//Phương thức khởi dựng
	public Player(String nameOfPlayer) {
		this.nameOfPlayer = nameOfPlayer;
		this.cardsInHand = new ArrayList<>();
	}

	// Các lá bài trên tay của người chơi
	public ArrayList<Card> getCardsInHand() {
		return this.cardsInHand;
	}

	// Thêm 1 lá bài lên tay
	public void addCard(Card card) {
		this.cardsInHand.add(card);
	}

	// Đánh 1 lá bài
	public void dropCard(Card card) {
		Iterator<Card> iterator = cardsInHand.iterator();
		while (iterator.hasNext()) {
			Card currentCard = iterator.next();
			if (currentCard.equals(card)) {
				iterator.remove();
				break;
			}
		}
	}

	// In ra các lá bài hiện có trên tay
	public void printCardInHand() {
		System.out.println(nameOfPlayer + "'s cards in hand:");
		for (Card card : cardsInHand) {
			System.out.print(card.toString() + " ");
		}
		System.out.println();
	}

	// Lấy tên của người chơi
	public String getNameOfPlayer() {
		return nameOfPlayer;
	}

	// Set tên cho người chơi
	public void setNameOfPlayer(String nameOfPlayer) {
		this.nameOfPlayer = nameOfPlayer;
	}

	public void setCardsFaceUp() {
		for (Card card : cardsInHand) {
			card.setFaceUp(true);
		}
	}

	public void setCardsFaceDown() {
		for (Card card : cardsInHand) {
			card.setFaceUp(false);
		}
	}
}
