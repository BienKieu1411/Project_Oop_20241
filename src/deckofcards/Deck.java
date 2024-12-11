package deckofcards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	protected ArrayList<Card> deck;
	public Deck(int numOfCards) {
		this.deck = new ArrayList<>();
		String[] suit = {"C", "S", "H", "D"};
		String[] rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		int count = 0;
		boolean check = false;
		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < 4; ++j) {
				Card card = new Card(rank[i], suit[j]);
				deck.add(card);
				count++;
				if(count == numOfCards) {
					check = true;
					break;
				}
			}
			if(check) break;
		}
	}


	// Phương thức xáo trộn bộ bài
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}

	// Lấy ra 1 quân bài ở trên cùng bộ bài
	public Card getCardTop() {
		return deck.removeLast();
	}
}