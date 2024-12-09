package deckofcards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
	protected String suit;// Chât của lá bài
	protected String rank;// Điểm của lá bài
	protected boolean isSelected = false;
	protected boolean isFaceUp = false;
	private Image frontImage; // Ảnh mặt trước
	private Image backImage;  // Ảnh mặt sau

//      ♥ H (Hearts) : Chất cơ
//      ♦ D (Diamonds) : Chất rô
//      ♣ C (Clubs) : Chất nhép
//      ♠ S (Spade) : Chất bích

	// Constructor
	public Card(String rank, String suit) {
		this.rank = rank;
		this.suit = suit;
		this.frontImage = new Image(getClass().getResourceAsStream("/cardsimage/" + toString() + ".png"));
		this.backImage = new Image(getClass().getResourceAsStream("/cardsimage/BACK.png"));
		this.isFaceUp = false; // Mặc định là úp bài
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

	public String printRank(){
		return this.rank;
	}

	public String printSuit(){
		return this.suit;
	}

	public boolean isRed(){
		return this.getSuit() > 2;
	}

	// Ghi đè phương thức equals giúp dễ dàng so sánh 2 lá bài bằng nhau
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Card card = (Card) obj;
		return rank.equals(card.rank) && suit.equals(card.suit);
	}


	public ImageView getFrontView() {
		ImageView imageView = new ImageView(frontImage);
		imageView.setFitWidth(70);  // Đặt chiều rộng
		imageView.setFitHeight(100); // Đặt chiều cao
		return imageView;
	}

	public ImageView getBackView() {
		ImageView imageView = new ImageView(backImage);
		imageView.setFitWidth(70);  // Đặt chiều rộng
		imageView.setFitHeight(100); // Đặt chiều cao
		return imageView;
	}

	public void setFaceUp(boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}

	public boolean isFaceUp() {
		return isFaceUp;
	}

	public ImageView getCurrentView() {
		return isFaceUp ? getFrontView() : getBackView();
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	// Giúp so sánh lá bài nào lớn hơn
	public int compareCard(Card other) {
		int rankComparison = Integer.compare(this.getRank(), other.getRank());
		if (rankComparison != 0)
			return rankComparison;
		return Integer.compare(this.getSuit(), other.getSuit());
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