package gamecardthirteens;

import deckofcards.Card;
import rulesofgame.SetGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SetGameOfThirteenS extends SetGame {
    private ArrayList<PlayerThirteenS> playersThirteenS;

    public SetGameOfThirteenS() {
    }

    // Thêm người chơi vào trò chơi
    public ArrayList<PlayerThirteenS> addPlayer() {
        this.playersThirteenS = new ArrayList<>();
        for (int i = 0; i < numberOfPersons; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            String nameOfPerson = scanner.nextLine();  // Nhập tên người chơi
            PlayerThirteenS person = new PlayerThirteenS(nameOfPerson);
            person.setNameOfPlayer(nameOfPerson);
            playersThirteenS.add(person);
        }
        // Dùng upcasting và ghi đè để tạo bot
        for (int i = 0; i < numberOfBots; i++) {
            String nameOfBot = "Bot" + (i + 1);
            PlayerThirteenS bot = new BotThirteenS(nameOfBot);
            playersThirteenS.add(bot);
        }
        Collections.shuffle(playersThirteenS);
        return playersThirteenS;
    }

    // Chia bài cho người chơi, mỗi người 13 lá
    protected DeckOfThirteenS dealCard(DeckOfThirteenS deckOfThirteenS) {
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
    private final Scanner scanner = new Scanner(System.in);
}
