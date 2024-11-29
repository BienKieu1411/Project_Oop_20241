package gamecardbaccarat;

import deckofcards.Card;

public class CardOfBaccarat extends Card {

	// Constructor lá bài game Baccarat
	CardOfBaccarat(String rank, String suit) {
		super(rank, suit);
	}

	// Get Rank lá bài game Baccarat:
	// Note: Lá A đc gán Rank = 11;
	public int getRank() {
		if (this.rank.equals("A"))
			return 11;
		return Integer.parseInt(rank);
	}

	// Get Suit
	public int getSuit() {
        return switch (this.suit) {
            case "D" -> 4;
            case "H" -> 3;
            case "S" -> 2;
            default -> 1;
        };
    }
}