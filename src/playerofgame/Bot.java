package playerofgame;

import deckofcards.Card;
import logicgame.Rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bot extends Player{
    private boolean checkSort = false;
    private ArrayList<Card> listCards =  new ArrayList<>();
    public Bot(String name, boolean isBot, Rules rules){
        super(name, isBot, rules);
    }
    // Ghi đè phương thức getSelection để đưa ra lựa chọn của Bot
    @Override
    public String getSelection(ArrayList<Card> cardsPreTurn) {
        // Bot sẽ tự động chọn 'Sort' ở lượt đầu
        if (!checkSort) {
            checkSort = true;
            super.sortCardsInHand();
            return "Sort";
        }
        // Nếu tìm được Bot sẽ chọn 'Play', còn không sẽ 'Skip'
        if(!selectionOfBot(cardsPreTurn).isEmpty()) {
            listCards = selectionOfBot(cardsPreTurn);
            return "Play";
        }
        return "Skip";
    }

    // Ghi đè phương thức getListCardPlayed để đưa ra bộ bài Bot đánh, bộ phải thoả mãn chặn được bộ ở lượt trước.
    @Override
    public ArrayList<Card> getListCardPlayed() {
        return listCards;
    }
    // Phương thức checkSet để check và tìm bộ thoả mãn
    private ArrayList<Card> checkSet (String type, int n, ArrayList<Card> cardsPreTurn){
        ArrayList<Card> listCardsPlayed = new ArrayList<>();
        if(type.equals("Lobby")){
            ArrayList<Card> list = new ArrayList<>();
            list.add(cardsInHand.getFirst());
            for(int i = 1; i < cardsInHand.size(); i++) {
                Card card = cardsInHand.get(i);
                // Chỉ lấy 1 quân bài trong các quân bài có Rank giống nhau
                if(card.getRank() != list.getLast().getRank()) {
                    // Nếu tìm thấy 2 trong tay thì dừng lại
                    if(card.getRank() == 15) break;
                    list.add(card);
                }
            }
            for(int i = 0; i < list.size() - n; i++) {
                for(int j = i; j < i + n; j++) {
                    // Thêm quân bài vào list
                    listCardsPlayed.add(list.get(j));
                }
                // Nếu bộ bài đã tìm được thoả mãn là sảnh và chặn được bộ bài của đối thủ thì trả về bộ bài đã tìm
                if(rules.getTypeOfCards(listCardsPlayed).equals("Lobby")){
                    if(rules.compareCards(listCardsPlayed, cardsPreTurn))
                        return listCardsPlayed;
                }
                // Sau mỗi lần tìm kiếm thì xoá list, trả lại list rỗng
                listCardsPlayed.clear();
            }
        }
        else {
            // Trường hợp bộ là Đơn, Đôi, Tam, Tứ
            if (type.equals("Once")) n = 1;
            if (type.equals("Double")) n = 2;
            if (type.equals("Triple")) n = 3;
            if (type.equals("Four-Fold")) n = 4;
            for (int i = 0; i < cardsInHand.size() - n + 1; i++) {
                for (int j = 0; j < n; ++j) {
                    listCardsPlayed.add(cardsInHand.get(i + j));
                }
                if (rules.getTypeOfCards(listCardsPlayed).equals(type) && rules.checkCardsDrop(listCardsPlayed, cardsPreTurn))
                    return listCardsPlayed;
                listCardsPlayed.clear();
            }
        }
        return listCardsPlayed;
    }

    public ArrayList<Card> selectionOfBot(ArrayList<Card> cardsPreTurn) {
        if(cardsPreTurn.isEmpty()) {
            ArrayList<Card> listCardsPlayed = new ArrayList<>();
            // Tạo listType để sắp xếp random, để sự lựa chọn của Bot khi bắt đầu đánh lượt mới là ngẫu nhiên, khó đoán cho người chơi
            List<String> listType = new ArrayList<>(List.of("Lobby", "Four-Fold", "Triple", "Double", "Once", "Lobby"));
            Collections.shuffle(listType);
            for(String type : listType) {
                // Nếu là sảnh, thì sẽ chọn từ sảnh dài nhất đến sảnh ngắn nhất
                if(type.equals("Lobby")){
                    for(int i = cardsInHand.size(); i > 2; --i) {
                        listCardsPlayed = checkSet(type, i, cardsPreTurn);
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
