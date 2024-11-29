package gamecardthirteens;

import deckofcards.Card;

public class CardOfThirteenS extends Card {
	CardOfThirteenS(String rank, String suit) {
		super(rank, suit);
	}

	// Điểm cuả lá bài
	// Note: 2 -> A -> K -> Q -> J ...
	public int getRank() {
        return switch (this.rank) {
            case "2" -> 15;
            case "A" -> 14;
            case "K" -> 13;
            case "Q" -> 12;
            case "J" -> 11;
            default -> Integer.parseInt(rank);
        };
    }

	// Chất của lá bài
	// Note: H -> D -> C -> S
	public int getSuit() {
        return switch (this.suit) {
            case "H" -> 4;
            case "D" -> 3;
            case "C" -> 2;
            default -> 1;
        };
    }

	// Trả về dạng String của Rank lá bài
	public String printRank() {
		return this.rank;
	}

	// Trả về dạng String của Suit lá bài
	public String printSuit() {
		return this.suit;
	}

	// Ghi đè phương thức equals giúp dễ dàng so sánh 2 lá bài bằng nhau
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		CardOfThirteenS card = (CardOfThirteenS) obj;
		return rank.equals(card.rank) && suit.equals(card.suit);
	}

	// Giúp so sánh lá bài nào lớn hơn
	public int compareCard(CardOfThirteenS other) {
		int rankComparison = Integer.compare(this.getRank(), other.getRank());
		if (rankComparison != 0)
			return rankComparison;
		return Integer.compare(this.getSuit(), other.getSuit());
	}
}