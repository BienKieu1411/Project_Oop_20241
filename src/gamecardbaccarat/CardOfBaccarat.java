package gamecardbaccarat;

import deckofcards.Card;

public class CardOfBaccarat extends Card {

	// Constructor lá bài game Baccarat
	CardOfBaccarat(String rank, String suit) {
		super(rank, suit);
	}

	// Get Rank lá bài game Baccarat:
	// Note: Lá A đc gán Rank = 11;
	@Override
	public int getRank() {
		if (this.rank.equals("A"))
			return 11;
		return Integer.parseInt(rank);
	}

	// Get Suit
	@Override
	public int getSuit() {
        return switch (this.suit) {
            case "D" -> 4;
            case "H" -> 3;
            case "S" -> 2;
            default -> 1;
        };
    }
	// So Sánh 2 lá bài
	@Override
	public int compareCard(Card other) {
		int suitComparison = Integer.compare(this.getSuit(), other.getSuit());
		if (suitComparison != 0)
			return suitComparison;
		return Integer.compare(this.getRank(), other.getRank());
	}
}