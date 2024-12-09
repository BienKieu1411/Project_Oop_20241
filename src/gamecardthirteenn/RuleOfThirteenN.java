package gamecardthirteenn;

import deckofcards.Card;
import logicgame.Rules;

import java.util.ArrayList;

public class RuleOfThirteenN extends Rules {
    public RuleOfThirteenN() {
        CheckSetThirteenN checkSetThirteenN = new CheckSetThirteenN();
        super.setCheckSet(checkSetThirteenN);
    }
    @Override
    public boolean compareCards(ArrayList<Card> card1s, ArrayList<Card> card2s) {
        card1s = checkSet.sortCards(card1s);
        card2s = checkSet.sortCards(card2s);
        if(card1s.isEmpty()) return false;
        if(card2s.isEmpty()) return true;
        if(card1s.size() != card2s.size()) return false;
        if(card1s.getLast().getSuit() != card2s.getLast().getSuit()) return false;
        return card1s.getLast().compareCard(card2s.getLast()) > 0;
    }
    @Override
    public boolean checkCardsDrop(ArrayList<Card> cards, ArrayList<Card> cardsPreTurn) {
        if (cards.isEmpty())
            return false;
        String typeOfCardsPreTurn = getTypeOfCards(cardsPreTurn), typeOfCards = getTypeOfCards(cards);
        if(typeOfCards.equals("Invalid"))
            return false;
        if (cardsPreTurn.isEmpty())
            return true;
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
        if(typeOfCardsPreTurn.equals("Once")){
            if(cards.getFirst().getRank() == 15){
                if(cardsPreTurn.getFirst().getRank() < 15) return true;
                return cards.getFirst().getSuit() > cardsPreTurn.getFirst().getSuit();
            }
            return cards.getFirst().getSuit() == cardsPreTurn.getFirst().getSuit() && cards.getFirst().getRank() > cardsPreTurn.getFirst().getRank();
        }
        return compareCards(cards, cardsPreTurn);
    }
}
