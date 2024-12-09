package logicgame;

import deckofcards.Card;
import deckofcards.Deck;
import gameplay.DealCardAnimation;
import javafx.scene.layout.AnchorPane;
import playerofgame.Bot;
import playerofgame.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetGame {
    protected int numberOfPlayer;
    ArrayList<Player> players = new ArrayList<>();
    void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }
    // Thêm người chơi vào trò chơi
    public void addPlayer(boolean playWithPlayer, List<String> playerNames) {
        if (playWithPlayer) {
            for (int i = 0; i < numberOfPlayer; i++) {
                String nameOfPerson = playerNames.get(i);  //tên người chơi
                Player person = new Player(nameOfPerson, false);
                players.add(person);
            }
            Collections.shuffle(players);
        } else {
            Player person = new Player("You", false);
            players.add(person);
            // Dùng upcasting và ghi đè để tạo bot
            for (int i = 0; i < numberOfPlayer - 1; i++) {
                String nameOfBot = "Bot" + (i + 1);
                Player bot = new Bot(nameOfBot, true);
                players.add(bot);
            }
        }
    }

    // Chia bài cho người chơi, mỗi người 13 lá
    public ArrayList<Player> dealCard(AnchorPane gameRoot, Deck deck, Runnable onFinished) {
        deck.shuffleDeck();
        for (int i = 0; i < 13; ++i) {
            for (int j = 0; j < numberOfPlayer; ++j) {
                Card card = deck.getCardTop();
                this.players.get(j).addCard(card);
            }
        }
        new DealCardAnimation(gameRoot, numberOfPlayer, 13, onFinished);
        return this.players;
    }
}
