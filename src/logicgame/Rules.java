package logicgame;

import deckofcards.Card;
import playerofgame.Player;

import java.util.ArrayList;

public class Rules {
    protected int numberOfPlayer;
    protected CheckSet checkSet = new CheckSet();
    protected ArrayList<Player> players = new ArrayList<>();
    public ArrayList<String> topWinners = new ArrayList<>();

    protected void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    protected void setCheckSet(CheckSet checkSet) {
        this.checkSet = checkSet;
    }

    // So sánh 2 bộ với nhau
    public boolean compareCards(ArrayList<Card> card1s, ArrayList<Card> card2s) {
        card1s = checkSet.sortCards(card1s);
        card2s = checkSet.sortCards(card2s);
        if(card1s.isEmpty()) return false;
        if(card2s.isEmpty()) return true;
        if(card1s.size() != card2s.size()) return false;
        return card1s.getLast().compareCard(card2s.getLast()) > 0;
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
        return compareCards(cards, cardsPreTurn);
    }

    // Kiểm tra người chơi có tứ quý 2 -> thắng
    protected int checkWinner() {
        for (int i = 0; i < numberOfPlayer; i++) {
            ArrayList<Card> cards = new ArrayList<>();
            for (int j = 9; j < 13; ++j) {
                Card card = players.get(i).getCardsInHand().get(j);
                cards.add(card);
            }
            if (checkSet.checkFourFoldCard(cards) && (cards.get(3).getRank() == 2))
                return i;
        }
        return -1;
    }

    // Kiểm tra điều kiện kết thúc trò chơi(khi có người hết bài trên tay)
    // Người chơi sẽ được xếp hạng: Nhất, Nhì, Ba, ... cho đến người chơi cuối còn bài trên tay
    protected ArrayList<Integer> endOfGame(ArrayList<Integer> playerEndGame) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCardsInHand().isEmpty() && !playerEndGame.contains(i)) {
                playerEndGame.add(i);
                topWinners.add(players.get(i).getNameOfPlayer());
                System.out.println(players.get(i).getNameOfPlayer() + " got " +
                        switch (playerEndGame.size()) {
                            case 1 -> "First";
                            case 2 -> "Second";
                            case 3 -> "Third";
                            default -> "";
                        } + " place!");
                System.out.println();
                return playerEndGame;
            }
        }
        return playerEndGame;
    }
}