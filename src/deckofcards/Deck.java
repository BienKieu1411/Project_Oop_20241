package deckofcards;

import java.util.ArrayList;
import java.util.Collections;

//Dùng template để đa dạng bộ bài, dễ dàng mở rộng ra các bộ bài 52 lá, 36 lá, 54 lá, ....
public class Deck<T> {
	protected ArrayList<T> deck;

	// Phương thức xáo trộn bộ bài
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}

	// Lấy ra 1 quân bài ở trên cùng bộ bài
	public T getCardTop() {
		return deck.removeLast();
	}
}