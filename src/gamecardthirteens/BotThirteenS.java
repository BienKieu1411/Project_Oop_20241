package gamecardthirteens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BotThirteenS extends PlayerThirteenS {
	private final RulesOfThirteenS rules = new RulesOfThirteenS();
	private boolean checkSort = false;
	private ArrayList<CardOfThirteenS> listCards =  new ArrayList<>();

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
		if(!selectionOfBot(cardsPreTurn).isEmpty()) {
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
		for(CardOfThirteenS card : listCards) {
			played = played.concat(card.printRank()).concat("-").concat(card.printSuit()).concat(" ");
		}
		return played;
	}

	private ArrayList<CardOfThirteenS> checkSet (String type, int n, ArrayList<CardOfThirteenS> cardsPreTurn){
		ArrayList<CardOfThirteenS> listCardsPlayed = new ArrayList<>();
		if(type.equals("Lobby")){
			ArrayList<CardOfThirteenS> list = new ArrayList<>();
			list.add(cardsInHand.getFirst());
			for(int i = 1; i < cardsInHand.size(); i++) {
				CardOfThirteenS card = cardsInHand.get(i);
				if(card.getRank() != list.getLast().getRank()) {
					if(card.getRank() == 15) break;
					list.add(card);
				}
			}
			for(int i = 0; i < list.size() - n; i++) {
				for(int j = i; j < i + n; j++) {
					listCardsPlayed.add(list.get(j));
				}
				if(rules.getTypeOfCards(listCardsPlayed).equals("Lobby")){
					if(rules.compareCards(listCardsPlayed, cardsPreTurn))
						return listCardsPlayed;
				}
				listCardsPlayed.clear();
			}
		}
		if(type.equals("Once")) n = 1;
		if(type.equals("Double")) n = 2;
		if(type.equals("Triple")) n = 3;
		if(type.equals("Four-Fold")) n = 4;
		for(int i = 0; i < cardsInHand.size() - n + 1; i++) {
			for (int j = 0; j < n; ++j) {
				listCardsPlayed.add(cardsInHand.get(i+j));
			}
			if(rules.getTypeOfCards(listCardsPlayed).equals(type) && rules.checkCardsDrop(listCardsPlayed, cardsPreTurn))
				return listCardsPlayed;
			listCardsPlayed.clear();
		}
		return listCardsPlayed;
	}

	public ArrayList<CardOfThirteenS> selectionOfBot(ArrayList<CardOfThirteenS> cardsPreTurn) {
		if(cardsPreTurn.isEmpty()) {
			ArrayList<CardOfThirteenS> listCardsPlayed = new ArrayList<>();
			List<String> listType = new ArrayList<>(List.of("Lobby", "Four-Fold", "Triple", "Double", "Once", "Lobby"));
			Collections.shuffle(listType);
			for(String type : listType) {
				if(type.equals("Lobby")){
					for(int i = cardsInHand.size(); i > 2; --i) {
						listCardsPlayed = checkSet("Lobby", i, cardsPreTurn);
						if(!listCardsPlayed.isEmpty()) return listCardsPlayed;
					}
				}
				else {
					listCardsPlayed = checkSet(type, cardsPreTurn.size(), cardsPreTurn);
					if (!listCardsPlayed.isEmpty()) return listCardsPlayed;
				}
			}
			return listCardsPlayed;
		}
		String typeCardsPreTurn = rules.getTypeOfCards(cardsPreTurn);
		return checkSet(typeCardsPreTurn, cardsPreTurn.size(), cardsPreTurn);
	}
}