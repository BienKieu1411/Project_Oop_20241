package gamecardthirteens;

import java.util.ArrayList;
import java.util.Comparator;

public class CheckSetOfThirteenS {
    // Check các kiểu bộ có thể có trong game bài tiến lên
    public CheckSetOfThirteenS(){

    }

    // Sắp xếp bộ theo giá trị tăng dần
    protected ArrayList<CardOfThirteenS> sortCards(ArrayList<CardOfThirteenS> cards) {
        cards.sort(Comparator.comparingInt(CardOfThirteenS::getRank).thenComparingInt(CardOfThirteenS::getSuit));
        return cards;

    }

    // Kiểm tra đôi
    protected boolean checkDoubleCard(ArrayList<CardOfThirteenS> cards) {
        if (cards.size() != 2)
            return false;
        return cards.get(0).getRank() == cards.get(1).getRank();
    }

    //Kiểm tra tam
    protected boolean checkTripleCard(ArrayList<CardOfThirteenS> cards) {
        if (cards.size() != 3) return false;
        for (int i = 0; i < 2; ++i)
            if (cards.get(i).getRank() != cards.get(i + 1).getRank())
                return false;
        return true;
    }

    //Kiểm tra tứ
    protected boolean checkFourFoldCard(ArrayList<CardOfThirteenS> cards) {
        if (cards.size() != 4)
            return false;
        for (int i = 0; i < 3; ++i)
            if (cards.get(i).getRank() != cards.get(i + 1).getRank())
                return false;
        return true;
    }

    //Kiểm tra sảnh
    protected boolean checkLobby(ArrayList<CardOfThirteenS> cards) {
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

    //Kiểm tra thông
    protected boolean checkPine(ArrayList<CardOfThirteenS> cards) {
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
