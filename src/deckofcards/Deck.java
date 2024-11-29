package deckofcards;

import java.util.ArrayList;
import java.util.Collections;

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