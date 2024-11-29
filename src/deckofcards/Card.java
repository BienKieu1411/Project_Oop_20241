package deckofcards;

public class Card {
	protected String suit;// Chât của lá bài
	protected String rank;// Điểm của lá bài

//      ♥ H (Hearts) : Chất cơ
//      ♦ D (Diamonds) : Chất rô
//      ♣ C (Clubs) : Chất nhép
//      ♠ S (Spade) : Chất bích

	// Constructor
	public Card(String rank, String suit) {
		this.rank = rank;
		this.suit = suit;
	}

	// In ra lá bài
	@Override
	public String toString() {
		return rank + '-' + suit;
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(rank, suit);
	}
}