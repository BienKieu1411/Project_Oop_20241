package rulesofgame;

import deckofcards.Card;
import java.util.ArrayList;

public class Rules {
    protected int numberOfPlayer;
    protected CheckSet checkSet = new CheckSet();

    // So sánh 2 bộ với nhau
    public boolean compareCards(ArrayList<Card> card1s, ArrayList<Card> card2s) {
        card1s = checkSet.sortCards(card1s);
        card2s = checkSet.sortCards(card2s);
        if(card1s.isEmpty()) return false;
        if(card2s.isEmpty()) return true;
        if(card1s.size() != card2s.size()) return false;
        return card1s.getLast().compareCard(card2s.getLast()) == 1;
    }

    // Kiểu của bộ bài: Đơn, Đôi, Tam, Tứ, Sảnh, Thông.
    public String getTypeOfCards(ArrayList<Card> cards) {
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
}