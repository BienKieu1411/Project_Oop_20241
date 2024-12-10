package playerofgame;

import deckofcards.Card;
import logicgame.CheckSet;
import logicgame.Rules;

import java.util.ArrayList;
import java.util.Iterator;

// Dùng template để dễ dàng tạo người chơi của game bất kì, dễ dàng mở rộng
public class Player {
	protected String nameOfPlayer;
	protected ArrayList<Card> cardsInHand;
	protected boolean isBot;
	protected ArrayList<Card> listCardPlayed = new ArrayList<>();
	private CheckSet checkSet = new CheckSet();
	protected Rules rules;
	private int moneyPlayer;// Số tiền của người chơi

	//Phương thức khởi dựng
	public Player(String nameOfPlayer, boolean isBot, Rules rules) {
		this.nameOfPlayer = nameOfPlayer;
		this.cardsInHand = new ArrayList<>();
		this.isBot = isBot;
		this.rules = rules;
	}

	public boolean getIsBot() {
		return isBot;
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

	public void addMoneyPlayer(int moneyPlayer) {
		this.moneyPlayer += moneyPlayer;
	}

	public int getMoneyPlayer() {
		return moneyPlayer;
	}

	public void setMoneyPlayer(int moneyPlayer) {
		this.moneyPlayer = moneyPlayer;
	}

	// Lấy tên của người chơi
	public String getNameOfPlayer() {
		return nameOfPlayer;
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
		this.cardsInHand = checkSet.sortCards(this.cardsInHand);
	}
}
