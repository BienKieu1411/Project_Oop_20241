package gamecardthirteens;

import java.util.ArrayList;
import java.util.Comparator;

public class RulesOfThirteenS {
	protected int numberOfPlayer;
	protected DeckOfThirteenS deckOfThirteenS = new DeckOfThirteenS();
	protected ArrayList<PlayerThirteenS> playersThirteenS = new ArrayList<>();

	public RulesOfThirteenS() {
	}

	protected ArrayList<CardOfThirteenS> sortCards(ArrayList<CardOfThirteenS> cards) {
		cards.sort(new Comparator<>() {
			@Override
			public int compare(CardOfThirteenS card1, CardOfThirteenS card2) {
				int nameCompare = Integer.compare(card1.getRank(), card2.getRank());
				if (nameCompare != 0)
					return nameCompare;
				return Integer.compare(card1.getSuit(), card2.getSuit());
			}
		});
		return cards;
	}

	/*    private boolean compare(CardOfThirteenS card1, CardOfThirteenS card2) {
			if(card1.getRank() == card2.getRank())
				return card1.getSuit() > card2.getSuit();
			return card1.getRank() > card2.getRank();
		}*/
	// So sánh 2 bộ bài với nhau
	protected boolean compareCards(ArrayList<CardOfThirteenS> card1s, ArrayList<CardOfThirteenS> card2s) {
		card1s = sortCards(card1s);
		card2s = sortCards(card2s);
		if(card1s.isEmpty()) return false;
		if(card2s.isEmpty()) return true;
		if(card1s.size() != card2s.size()) return false;
		return card1s.getLast().compareCard(card2s.getLast()) == 1;
	}

	protected String getTypeOfCards(ArrayList<CardOfThirteenS> cards) {
		if (cards.size() == 1)
			return "Once";
		if (checkDoubleCard(cards))
			return "Double";
		if (checkTripleCard(cards))
			return "Triple";
		if (checkFourFoldCard(cards))
			return "Four-Fold";
		if (checkLobby(cards))
			return "Lobby";
		if (checkPine(cards))
			return "Pine";
		return "Invalid";
	}

	protected boolean checkCardsDrop(ArrayList<CardOfThirteenS> cards, ArrayList<CardOfThirteenS> cardsPreTurn) {
		if (cards.isEmpty())
			return false;
		if (cardsPreTurn.isEmpty())
			return true;
		String typeOfCardsPreTurn = getTypeOfCards(cardsPreTurn);
		String typeOfCards = getTypeOfCards(cards);
		if (!typeOfCards.equals(typeOfCardsPreTurn)) {
			if (typeOfCardsPreTurn.equals("Once")) {
				if (cardsPreTurn.getFirst().getRank() != 15)
					return false;
				if (typeOfCards.equals("Four-Fold") || typeOfCards.equals("Pine"))
					return true;
				return false;
			}
			if (typeOfCardsPreTurn.equals("Pine") && cardsPreTurn.size() == 6) {
				if (typeOfCards.equals("Four-Fold") || (typeOfCards.equals("Pine") && cards.size() > 6))
					return true;
				return false;
			}
			if (typeOfCardsPreTurn.equals("Double")) {
				if (cardsPreTurn.getFirst().getRank() != 15)
					return false;
				if (typeOfCards.equals("Four-Fold") || (typeOfCards.equals("Pine") && cards.size() >= 8))
					return true;
				return false;
			}
			if (typeOfCardsPreTurn.equals("Four-Fold")) {
				if (typeOfCards.equals("Pine") && cards.size() > 6)
					return true;
				return false;
			}
			return false;
		}
		return compareCards(cards, cardsPreTurn);
	}

	// Kiểm tra người chơi có tứ quý 2 -> thắng
	protected int checkWinner() {
		for (int i = 0; i < numberOfPlayer; i++) {
			ArrayList<CardOfThirteenS> cards = new ArrayList<>();
			for (int j = 9; j < 13; ++j) {
				CardOfThirteenS card = (CardOfThirteenS) (playersThirteenS.get(i).getCardsInHand()).get(j);
				cards.add(card);
			}
			if (checkFourFoldCard(cards) && (cards.get(3).getRank() == 2))
				return i;
		}
		return -1;
	}


	// Kiểm tra điều kiện kết thúc trò chơi(khi có người hết bài trên tay)
	protected int endOfGame() {
		for (int i = 0; i < numberOfPlayer; i++) {
			if (playersThirteenS.get(i).getCardsInHand().isEmpty())
				return i;
		}
		return -1;
	}


	// Kiểm tra bộ đôi, bộ 3 , tứ , thông , sảnh ...
	private boolean checkDoubleCard(ArrayList<CardOfThirteenS> cards) {
		if (cards.size() != 2)
			return false;
		return cards.get(0).getRank() == cards.get(1).getRank();
	}

	private boolean checkTripleCard(ArrayList<CardOfThirteenS> cards) {
		if (cards.size() != 3) return false;
		for (int i = 0; i < 2; ++i)
			if (cards.get(i).getRank() != cards.get(i + 1).getRank())
				return false;
		return true;
	}

	private boolean checkFourFoldCard(ArrayList<CardOfThirteenS> cards) {
		if (cards.size() != 4)
			return false;
		for (int i = 0; i < 3; ++i)
			if (cards.get(i).getRank() != cards.get(i + 1).getRank())
				return false;
		return true;
	}

	private boolean checkLobby(ArrayList<CardOfThirteenS> cards) {
		if (cards.size() < 3)
			return false;
		cards = sortCards(cards);
		if (cards.getLast().getRank() == 15)
			return false;
		for (int i = 1; i < cards.size(); ++i)
			if (cards.get(i).getRank() - cards.get(i - 1).getRank() != 1)
				return false;
		return true;
	}

	private boolean checkPine(ArrayList<CardOfThirteenS> cards) {
		if (cards.size() % 2 == 1 || cards.size() < 6)
			return false;
		cards = sortCards(cards);
		if (cards.get(0).getRank() != cards.get(1).getRank() || cards.get(0).getRank() == 15)
			return false;
		for (int i = 3; i < cards.size(); i += 2) {
			if (cards.get(i).getRank() == 15)
				return false;
			if (cards.get(i).getRank() != cards.get(i - 1).getRank() || cards.get(i).getRank() - cards.get(i - 2).getRank() != 1)
				return false;
		}
		return true;
	}
}