package playerofgame;

import java.util.ArrayList;
import java.util.Iterator;

// Dùng template để dễ dàng tạo người chơi của game bất kì, dễ dàng mở rộng
public class Player<T> {
	protected String nameOfPlayer;
	protected ArrayList<T> cardsInHand;

	//Phương thức khởi dựng
	public Player(String nameOfPlayer) {
		this.nameOfPlayer = nameOfPlayer;
		this.cardsInHand = new ArrayList<>();
	}

	// Các lá bài trên tay của người chơi
	public ArrayList<T> getCardsInHand() {
		return this.cardsInHand;
	}

	// Thêm 1 lá bài lên tay
	public void addCard(T card) {
		this.cardsInHand.add(card);
	}

	// Đánh 1 lá bài
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

	// In ra các lá bài hiện có trên tay
	public void printCardInHand() {
		System.out.println(nameOfPlayer + "'s cards in hand:");
		for (T card : cardsInHand) {
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
}
