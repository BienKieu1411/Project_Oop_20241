package gamecardthirteens;

import deckofcards.Deck;

import java.util.ArrayList;

public class DeckOfThirteenS extends Deck<CardOfThirteenS> {
	// Constructor
	// Note: Bộ bài 52 lá đầy đủ các chất và điểm
	public DeckOfThirteenS() {
		this.deck = new ArrayList<>();
		String[] suit = {"S", "C", "D", "H"};
		String[] rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < 4; ++j) {
				CardOfThirteenS card = new CardOfThirteenS(rank[i], suit[j]);
				deck.add(card);
			}
		}
	}
}