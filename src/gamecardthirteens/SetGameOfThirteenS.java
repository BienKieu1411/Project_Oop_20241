package gamecardthirteens;

import deckofcards.Card;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import rulesofgame.SetGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetGameOfThirteenS extends SetGame {
    public ArrayList<PlayerThirteenS> playersThirteenS;

    public SetGameOfThirteenS() {
    }

    // Thêm người chơi vào trò chơi
    public ArrayList<PlayerThirteenS> addPlayer(boolean playWithPlayer, List<String> playerNames) {
        this.playersThirteenS = new ArrayList<>();
        if (playWithPlayer) {
            for (int i = 0; i < numberOfPlayer; i++) {
                System.out.print("Player " + (i + 1) + ": ");
                String nameOfPerson = playerNames.get(i);  //tên người chơi
                PlayerThirteenS person = new PlayerThirteenS(nameOfPerson);
                playersThirteenS.add(person);
            }
            Collections.shuffle(playersThirteenS);
        }
        else{
            PlayerThirteenS person = new PlayerThirteenS("You");
            playersThirteenS.add(person);
            // Dùng upcasting và ghi đè để tạo bot
            for (int i = 0; i < numberOfPlayer-1; i++) {
                String nameOfBot = "Bot" + (i + 1);
                PlayerThirteenS bot = new BotThirteenS(nameOfBot);
                playersThirteenS.add(bot);
            }
        }
        return playersThirteenS;
    }

    // Chia bài cho người chơi, mỗi người 13 lá
    public DeckOfThirteenS dealCard(AnchorPane gameRoot, DeckOfThirteenS deckOfThirteenS) {
        deckOfThirteenS.shuffleDeck();
        for (int i = 0; i < 13; ++i) {
            System.out.println("- Deal cards in turn " + (i + 1) + ": ");
            for (int j = 0; j < numberOfPlayer; ++j) {
                Card card = deckOfThirteenS.getCardTop();
                this.playersThirteenS.get(j).addCard(card);
                this.playersThirteenS.get(j).printCardInHand();
            }
        }
        return deckOfThirteenS;
    }
}
