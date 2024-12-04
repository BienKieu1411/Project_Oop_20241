package gamecardbaccarat;

import deckofcards.Card;
import deckofcards.Deck;

import java.util.ArrayList;

public class DeckOfBaccarat extends Deck {

	// Constructor : Khởi tạo bộ bài Deck của game Baccarat
	// Note: Loại bỏ các lá J, Q, K
	public DeckOfBaccarat() {
		this.deck = new ArrayList<>();
		String[] suit = {"C", "S", "H", "D"};
		String[] rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9"};
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 4; ++j) {
				Card card = new CardOfBaccarat(rank[i], suit[j]);
				deck.add(card);
			}
		}
	}
}