package gamecardthirteens;

import deckofcards.Card;

import java.util.ArrayList;

public class TurnOfThirteenS{
    private boolean[] checkTurn;
    private ArrayList<Card> cardPreTurn = new ArrayList<>();
    private boolean checkSkip = false;
    private final RulesOfThirteenS rules = new RulesOfThirteenS();
    private int numberOfPlayer;

    public TurnOfThirteenS() {
        super();
    }

    // lấy số người chơi
    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    // khởi tạo turn ban đầu, nếu ai có 3 Bích sẽ được đánh trước, nếu không ai có 3 Bích trên tay thì sẽ ngẫu nhiên người chơi đánh trước
    public void setTurn(ArrayList<PlayerThirteenS> playersThirteenS){
        checkTurn = new boolean[numberOfPlayer];
        for(int i = 0; i < numberOfPlayer; i++) {
            checkTurn[i] = false;
        }
        for(PlayerThirteenS player : playersThirteenS){
            for(Card card : player.getCardsInHand()){
                if(card.getRank() == 3 && card.getSuit() == 1){
                    checkTurn[playersThirteenS.indexOf(player)] = true;
                    return;
                }
            }
        }
    }

    // Set lại turn mới, mọi người skip trong turn trước sẽ được đánh lại trong turn mới
    public void resetTurn(ArrayList<PlayerThirteenS> playersThirteenS) {
        for (int i = 0; i < playersThirteenS.size(); ++i)
            checkTurn[i] = true;
    }

    // Lượt chơi sẽ kết thúc khi chỉ còn 1 người chưa Skip
    public boolean checkEndTurn(ArrayList<PlayerThirteenS> playersThirteenS) {
        int counter = 0;
        for (int i = 0; i < playersThirteenS.size(); ++i) {
            if (checkTurn[i])
                counter++;
        }
        return counter == 1;
    }

    // Lấy lựa chọn của người chơi
    public boolean playCards(PlayerThirteenS player) {
        ArrayList<Card> cards = new ArrayList<>();// Những lá bài chọn để đánh
        System.out.println("Select card (enter in format Rank-Suit, write on one line, separated by spaces) to play or enter 'Sort' to sort cards in hand or enter 'Skip' to skip turn:");
        player.setListCardPlayed();
        String listCardPlayed = player.getListCardPlayed();
        // Người chơi ấn Skip sẽ bỏ lượt
        if (listCardPlayed.equals("Skip")) {
            return true;
        }
        // Người chơi ấp Sort sẽ sắp xếp bài trên tay
        if (listCardPlayed.equals("Sort")) {
            player.sortCardsInHand();
        }

        // Lấy và kiểm tra bộ mà người chơi đánh xem có hợp lệ hay không
        for (String selectedCard : listCardPlayed.split(" ")) {
            String[] rankandsuit = selectedCard.split("-");
            if (rankandsuit.length != 2) {
                System.out.println("Invalid, please select again!");
                return false;
            }
            Card card = new Card(rankandsuit[0], rankandsuit[1]);
            if ((player.getCardsInHand()).contains(card)) {
                cards.add(card);
            } else {
                System.out.println("Invalid, please select again!");
                return false;
            }
        }

        if (rules.getTypeOfCards(cards).equals("Invalid")) {
            System.out.println("Invalid, please select again!");
            return false;
        }

        if (rules.checkCardsDrop(cards, cardPreTurn)) {
            System.out.print(player.getNameOfPlayer() + " plays cards: ");
            for (Card card : cards) {
                System.out.print(card.printRank() + "-" + card.printSuit() + " ");
                player.dropCard(card);
            }
            System.out.println();
            player.printCardInHand();
            System.out.println();
            this.cardPreTurn = cards;
            return true;
        }
        System.out.println("The cards you played are smaller!");
        return false;
    }

    // Bắt đầu turn chính của game, game chỉ dừng lại khi đã có xếp hạng của các người chơi tham gia
    public void turnOfGame(ArrayList<PlayerThirteenS> playersThirteenS) {
        int index = 0;
        // Nếu không ai có 3 bích thì sẽ random người đánh trước
        if(!checkEndTurn(playersThirteenS)){
            resetTurn(playersThirteenS);
            index++;
            System.out.println("-Turn " + index + " :");
        }
        boolean checkEndGame = false;
        while (!checkEndGame) {
            for (int i = 0; i < playersThirteenS.size(); i++) {
                if (checkTurn[i]) {
                    // Lần lượt các người chơi sẽ đánh bài
                    if (checkEndTurn(playersThirteenS)) {
                        index++;
                        System.out.println("-Turn " + index + " :");
                        resetTurn(playersThirteenS);
                        cardPreTurn.clear();
                    }
                    playersThirteenS.get(i).printCardInHand();
                    System.out.println(playersThirteenS.get(i).getNameOfPlayer() + " invites to choose: ");
                    String getSelection;
                    while (true) {
                        System.out.println("Choose 'Skip' or 'Sort' or 'Play'");
                        playersThirteenS.get(i).setSelection();
                        getSelection = playersThirteenS.get(i).getSelection(cardPreTurn);
                        if (getSelection.equals("Skip")) {
                            System.out.println();
                            checkTurn[i] = false;
                            break;
                        }
                        if (getSelection.equals("Sort")) {
                            playersThirteenS.get(i).sortCardsInHand();
                            playersThirteenS.get(i).printCardInHand();
                        }
                        if (getSelection.equals("Play")) {
                            boolean check = false;
                            while (!check) {
                                check = playCards(playersThirteenS.get(i));
                                if (checkSkip) {
                                    checkTurn[i] = false;
                                    checkSkip = false;
                                }
                            }
                            break;
                        }
                    }
                }
                rules.endOfGame(playersThirteenS, numberOfPlayer);
                // Nếu còn 1 người chơi, in ra xếp hạng của người chơi đó và kết thúc game
                if(playersThirteenS.size() == 1){
                    System.out.println(playersThirteenS.getFirst().getNameOfPlayer() + " got " +
                            switch (numberOfPlayer) {
                                case 2 -> "Second";
                                case 3 -> "Third";
                                case 4 -> "Fourth";
                                default -> "";
                            } + " place!");
                    checkEndGame = true;
                    break;
                }
            }
        }
    }
}