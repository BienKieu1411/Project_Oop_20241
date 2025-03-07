package logicgame;

import deckofcards.Card;
import deckofcards.Deck;
import gameplay.DealCardAnimation;
import gameplay.SettingsMenu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import playerofgame.Bot;
import playerofgame.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetGame {
    protected int numberOfPlayer;
    public ArrayList<Player> players = new ArrayList<>();
    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }
    public ArrayList<Player> getPlayers() {
        return this.players;
    }
    // Thêm người chơi vào trò chơi
    public void addPlayer(boolean playWithPlayer, List<String> playerNames, Rules rules) {
        if (playWithPlayer) {
            for (int i = 0; i < numberOfPlayer; i++) {
                String nameOfPerson = playerNames.get(i);  //tên người chơi
                Player person = new Player(nameOfPerson, false, rules);
                players.add(person);
            }
            Collections.shuffle(players);
        } else {
            Player person = new Player("You", false, rules);
            players.add(person);
            // Dùng upcasting và ghi đè để tạo bot
            for (int i = 0; i < numberOfPlayer - 1; i++) {
                String nameOfBot = "Bot" + (i + 1);
                Player bot = new Bot(nameOfBot, true, rules);
                players.add(bot);
            }
        }
    }

    // Chia bài cho người chơi, mỗi người 13 lá
    public void dealCard(Stage stage, AnchorPane gameRoot, Deck deck, int numCards, Runnable onFinished) {
        deck.shuffleDeck();
        for (int i = 0; i < numCards; ++i) {
            for (int j = 0; j < numberOfPlayer; ++j) {
                Card card = deck.getCardTop();
                this.players.get(j).addCard(card);
            }
        }
        SettingsMenu settingsMenu = SettingsMenu.getInstance(); // Lấy thể hiện duy nhất
        if(settingsMenu.isImageMode()){
            new DealCardAnimation(gameRoot, numberOfPlayer, numCards, onFinished);
        }
    }
}
