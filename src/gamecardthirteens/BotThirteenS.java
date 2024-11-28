package gamecardthirteens;

import java.util.ArrayList;

public class BotThirteenS extends PlayerThirteenS {
	private final RulesOfThirteenS rules = new RulesOfThirteenS();
	private boolean checkSort = false;
	private ArrayList<CardOfThirteenS> listCards = new ArrayList<>();

	public BotThirteenS(String name) {
		super(name);
	}

	@Override
	public void setSelection() {

	}

	@Override
	public String getSelection(ArrayList<CardOfThirteenS> cardsPreTurn) {
		if (!checkSort) {
			checkSort = true;
			return "Sort";
		}
		if (!selectionOfBot(cardsPreTurn).isEmpty()) {
			listCards = selectionOfBot(cardsPreTurn);
			return "Play";
		}
		return "Skip";
	}

	@Override
	public void setListCardPlayed() {

	}

	@Override
	public String getListCardPlayed() {
		String played = "";
		for (CardOfThirteenS card : listCards) {
			played = played.concat(card.printRank()).concat("-").concat(card.printSuit()).concat(" ");
		}
		return played;
	}

	private ArrayList<CardOfThirteenS> checkSet(String type, ArrayList<CardOfThirteenS> cardsPreTurn) {
		int n = 0;
		ArrayList<CardOfThirteenS> listCardsPlayed = new ArrayList<>();
		if (type.equals("Once")) n = 1;
		if (type.equals("Double")) n = 2;
		if (type.equals("Triple")) n = 3;
		if (type.equals("Four-Fold")) n = 4;
		for (int i = 0; i < cardsInHand.size() - n + 1; i++) {
			for (int j = 0; j < n; ++j) {
				listCardsPlayed.add((CardOfThirteenS) cardsInHand.get(i + j));
			}
			if (rules.checkCardsDrop(listCardsPlayed, cardsPreTurn))
				return listCardsPlayed;
			else listCardsPlayed.clear();
		}
		return listCardsPlayed;
	}

	private ArrayList<CardOfThirteenS> checkLobby(int n, ArrayList<CardOfThirteenS> cardsPreTurn) {
		ArrayList<CardOfThirteenS> listCardsPlayed = new ArrayList<>();
		ArrayList<CardOfThirteenS> list = new ArrayList<>();
		list.add((CardOfThirteenS) cardsInHand.getFirst());
		for (int i = 1; i < cardsInHand.size(); i++) {
			if (((CardOfThirteenS) cardsInHand.get(i)).getRank() != list.getLast().getRank()) {
				list.add((CardOfThirteenS) cardsInHand.get(i));
			}
		}
		for (int i = 0; i < list.size() - n; i++) {
			for (int j = i; j < i + n; j++) {
				listCardsPlayed.add(list.get(j));
			}
			if (rules.getTypeOfCards(listCardsPlayed).equals("Lobby")) {
				if (rules.compareCards(listCardsPlayed, cardsPreTurn))
					return listCardsPlayed;
			}
			listCardsPlayed.clear();
		}
		return listCardsPlayed;
	}

	public ArrayList<CardOfThirteenS> selectionOfBot(ArrayList<CardOfThirteenS> cardsPreTurn) {
		ArrayList<CardOfThirteenS> listCardsPlayed = new ArrayList<>();
		if (cardsPreTurn.isEmpty()) {
			if (cardsInHand.size() == 1 || ((CardOfThirteenS) cardsInHand.getFirst()).getRank() != ((CardOfThirteenS) cardsInHand.get(1)).getRank())
				listCardsPlayed.add((CardOfThirteenS) cardsInHand.getFirst());
			else {
				listCardsPlayed.add((CardOfThirteenS) cardsInHand.getFirst());
				listCardsPlayed.add((CardOfThirteenS) cardsInHand.get(1));
			}
			return listCardsPlayed;
		}
		String typeCardsPreTurn = rules.getTypeOfCards(cardsPreTurn);
		int size = cardsPreTurn.size();
		if (typeCardsPreTurn.equals("Once")) {
			return checkSet("Once", cardsPreTurn);
		}
		if (typeCardsPreTurn.equals("Double")) {
			return checkSet("Double", cardsPreTurn);
		}
		if (typeCardsPreTurn.equals("Triple")) {
			return checkSet("Triple", cardsPreTurn);
		}
		if (typeCardsPreTurn.equals("Four-Fold")) {
			return checkSet("Four-Fold", cardsPreTurn);
		}
		if (typeCardsPreTurn.equals("Lobby")) {
			return checkLobby(size, cardsPreTurn);
		}
		return listCardsPlayed;
	}
}