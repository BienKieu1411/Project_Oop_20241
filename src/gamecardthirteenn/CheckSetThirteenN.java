package gamecardthirteenn;

import deckofcards.Card;
import logicgame.CheckSet;

import java.util.ArrayList;

public class CheckSetThirteenN extends CheckSet {
    @Override
    public boolean checkDoubleCard(ArrayList<Card> cards) {
        if (cards.size() != 2)
            return false;
        if(cards.get(0).isRed() && cards.get(1).isRed()) return cards.get(0).getRank() == cards.get(1).getRank();
        if(!cards.get(0).isRed() && !cards.get(1).isRed()) return cards.get(0).getRank() == cards.get(1).getRank();
        return false;
    }

    @Override
    public boolean checkLobby(ArrayList<Card> cards) {
        if (cards.size() < 3)
            return false;
        cards = sortCards(cards);
        if (cards.getLast().getRank() == 15)
            return false;
        for (int i = 1; i < cards.size(); ++i)
            if (cards.get(i).getRank() - cards.get(i - 1).getRank() != 1 || cards.get(i).getSuit() != cards.get(i - 1).getSuit())
                return false;
        return true;
    }
    @Override
    public boolean checkPine(ArrayList<Card> cards) {
        if (cards.size() % 2 == 1 || cards.size() < 6)
            return false;
        cards = sortCards(cards);
        ArrayList<Card> cardArrayList = new ArrayList<>();
        cardArrayList.add(cards.get(0));
        cardArrayList.add(cards.get(1));
        if (cards.get(0).getRank() == 15 || !checkDoubleCard(cardArrayList))
            return false;
        for (int i = 3; i < cards.size(); i += 2) {
            if (cards.get(i).getRank() == 15)
                return false;
            cardArrayList.clear();
            cardArrayList.add(cards.get(i-1));
            cardArrayList.add(cards.get(i));
            if (!checkDoubleCard(cardArrayList) || cards.get(i).getRank() - cards.get(i - 2).getRank() != 1 )
                return false;
        }
        return true;
    }
}
