package gamecardthirteens;

import java.util.ArrayList;

public class RulesOfThirteenS {
	protected int numberOfPlayer;
	protected DeckOfThirteenS deckOfThirteenS = new DeckOfThirteenS();
	protected ArrayList<PlayerThirteenS> playersThirteenS = new ArrayList<>();
	protected CheckSetOfThirteenS checkSet = new CheckSetOfThirteenS();

	public RulesOfThirteenS() {
	}

	// So sánh 2 bộ với nhau
	protected boolean compareCards(ArrayList<CardOfThirteenS> card1s, ArrayList<CardOfThirteenS> card2s) {
		card1s = checkSet.sortCards(card1s);
		card2s = checkSet.sortCards(card2s);
		if(card1s.isEmpty()) return false;
		if(card2s.isEmpty()) return true;
		if(card1s.size() != card2s.size()) return false;
		return card1s.getLast().compareCard(card2s.getLast()) == 1;
	}

	// Kiểu của bộ bài: Đơn, Đôi, Tam, Tứ, Sảnh, Thông.
	protected String getTypeOfCards(ArrayList<CardOfThirteenS> cards) {
		if (cards.size() == 1)
			return "Once";
		if (checkSet.checkDoubleCard(cards))
			return "Double";
		if (checkSet.checkTripleCard(cards))
			return "Triple";
		if (checkSet.checkFourFoldCard(cards))
			return "Four-Fold";
		if (checkSet.checkLobby(cards))
			return "Lobby";
		if (checkSet.checkPine(cards))
			return "Pine";
		return "Invalid";
	}

	// Kiểm tra xem bộ mà người đánh có thoả mãn lớn hơn bộ của đối thủ không
	protected boolean checkCardsDrop(ArrayList<CardOfThirteenS> cards, ArrayList<CardOfThirteenS> cardsPreTurn) {
		if (cards.isEmpty())
			return false;
		if (cardsPreTurn.isEmpty())
			return true;
		String typeOfCardsPreTurn = getTypeOfCards(cardsPreTurn), typeOfCards = getTypeOfCards(cards);
		if (!typeOfCards.equals(typeOfCardsPreTurn)) {
			if (typeOfCardsPreTurn.equals("Once")) {
				if (cardsPreTurn.getFirst().getRank() != 15)
					return false;
				return (typeOfCards.equals("Four-Fold") || typeOfCards.equals("Pine"));
			}
			if (typeOfCardsPreTurn.equals("Pine") && cardsPreTurn.size() == 6) {
				return typeOfCards.equals("Four-Fold");
			}
			if (typeOfCardsPreTurn.equals("Double")) {
				if (cardsPreTurn.getFirst().getRank() != 15)
					return false;
				return typeOfCards.equals("Four-Fold") || (typeOfCards.equals("Pine") && cards.size() >= 8);
			}
			if (typeOfCardsPreTurn.equals("Four-Fold")) {
                return typeOfCards.equals("Pine") && cards.size() > 6;
            }
			return false;
		}
		if (typeOfCardsPreTurn.equals("Pine") && cardsPreTurn.size() == 6) {
			return cards.size() > 6;
		}
		return compareCards(cards, cardsPreTurn);
	}

	// Kiểm tra người chơi có tứ quý 2 -> thắng
	protected int checkWinner() {
		for (int i = 0; i < numberOfPlayer; i++) {
			ArrayList<CardOfThirteenS> cards = new ArrayList<>();
			for (int j = 9; j < 13; ++j) {
				CardOfThirteenS card = playersThirteenS.get(i).getCardsInHand().get(j);
				cards.add(card);
			}
			if (checkSet.checkFourFoldCard(cards) && (cards.get(3).getRank() == 2))
				return i;
		}
		return -1;
	}

	// Kiểm tra điều kiện kết thúc trò chơi(khi có người hết bài trên tay)
	// Người chơi sẽ được xếp hạng: Nhất, Nhì, Ba, ... cho đến người chơi cuối còn bài trên tay
	protected void endOfGame(ArrayList<PlayerThirteenS> playersThirteenS, int numberOfPlayer) {
		for (int i = 0; i < playersThirteenS.size(); i++) {
			if (playersThirteenS.get(i).getCardsInHand().isEmpty()) {
				System.out.println(playersThirteenS.get(i).getNameOfPlayer() + " got " +
						switch (numberOfPlayer - playersThirteenS.size() + 1) {
							case 1 -> "First";
							case 2 -> "Second";
							case 3 -> "Third";
							default -> "";
				} + " place!");
				System.out.println();
				playersThirteenS.remove(i);
				return;
			}
		}
	}
}