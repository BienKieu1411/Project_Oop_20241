package gamecardbaccarat;

import playerofgame.Player;

public class PlayerBaccarat extends Player {
    private int moneyPlayer;// Số tiền của người chơi

    // Constructor
    public PlayerBaccarat() {
        super("Player");
        this.moneyPlayer = 0;
    }

    // Thêm tiền cho người chơi
    public void addMoneyPlayer(int moneyPlayer) {
        this.moneyPlayer += moneyPlayer;
    }

    // Tìm lá bài cao nhất trong tay người chơi
    public CardOfBaccarat biggestCardInHand(){
        CardOfBaccarat card = (CardOfBaccarat) cardsInHand.getFirst();
        for(int i = 1; i < cardsInHand.size(); i++){
            CardOfBaccarat cardinhand = (CardOfBaccarat) cardsInHand.get(i);
            if(compareCard(cardinhand, card)){
                card = cardinhand;
            }
        }
        return card;
    }

    // So Sánh 2 lá bài
    protected boolean compareCard(CardOfBaccarat card1, CardOfBaccarat card2){
        if(card1.getSuit() > card2.getSuit()) return true;
        if(card1.getSuit() < card2.getSuit()) return false;
        return card1.getRank() > card2.getRank();
    }

    // In ra người chơi + các lá bài
    public void printPlayer(){
        System.out.println("Player " + this.getNameOfPlayer() + " has " + moneyPlayer + "$");
    }

    // Geter và Seter Money
    public int getMoneyPlayer() {
        return moneyPlayer;
    }

    public void setMoneyPlayer(int moneyPlayer) {
        this.moneyPlayer = moneyPlayer;
    }

}