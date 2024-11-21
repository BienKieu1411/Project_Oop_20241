package deckofcards;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
    protected ArrayList<Card> deck;

    // Constructor
    public Deck(int numberCards) {
        this.deck = new ArrayList<Card>();
        String[] suit = {"C", "D", "H", "S"};
        String[] rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (int i = 0; i < rank.length; ++i) {
            for (int j = 0; j < 4; ++j) {
                Card card = new Card(rank[i], suit[j]);
                deck.add(card);
                if(deck.size() == numberCards) break;
            }
            if(deck.size() == numberCards) break;
        }
    }

    // Phương thức xáo trộn bộ bài
    public void shuffleDeck() {
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = ThreadLocalRandom.current().nextInt(i + 1); // Sử dụng ThreadLocalRandom
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    // Lấy ra 1 quân bìa ở trên cùng bộ bài
    public Card getCardTop() {
        return deck.remove(deck.size() - 1);
    }
}
