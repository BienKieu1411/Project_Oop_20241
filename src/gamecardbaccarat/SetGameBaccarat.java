package gamecardbaccarat;

import rulesofgame.SetGame;
import java.util.ArrayList;
import java.util.Collections;

public class SetGameBaccarat extends SetGame {
    private ArrayList<PlayerBaccarat> playersBaccarat;
    private int moneyPlayer;

    private static SetGameBaccarat instance;

    public static SetGameBaccarat getInstance() {
        if (instance == null) {
            instance = new SetGameBaccarat();
        }
        return instance;
    }

    // Thêm người chơi vào trò chơi
    public ArrayList<PlayerBaccarat> addPlayer() {
        this.playersBaccarat = new ArrayList<>();
        // Tạo người chơi
        for (int i = 0; i < numberOfPersons; i++) {
            String nameOfPerson = "Player " + (i + 1);
            PlayerBaccarat person = new PlayerBaccarat(nameOfPerson);
            person.addMoneyPlayer(this.moneyPlayer);
            playersBaccarat.add(person);
        }
        // Tạo bot
        for (int i = 0; i < numberOfBots; i++) {
            String nameOfBot = "Bot" + (i + 1);
            PlayerBaccarat bot = new BotBaccarat(nameOfBot);
            bot.addMoneyPlayer(this.moneyPlayer);
            playersBaccarat.add(bot);
        }
        // Trộn lại danh sách người chơi và bot
        Collections.shuffle(playersBaccarat);
        return playersBaccarat;
    }

    // Phương thức thiết lập số tiền ban đầu cho người chơi
    public void setMoney(int money) {
        this.moneyPlayer = money;
    }

    // Phương thức chia bài cho người chơi
    public DeckOfBaccarat dealCard(DeckOfBaccarat deckOfBaccarat) {
        // Xáo trộn bộ bài trước khi chia
        deckOfBaccarat.shuffleDeck();
        // Chia bài cho người chơi và bot
        for (int i = 0; i < 3; ++i) {
            System.out.println("- Deal cards in turn " + (i + 1) + ": ");
            for (int j = 0; j < playersBaccarat.size(); ++j) {
                playersBaccarat.get(j).addCard(deckOfBaccarat.getCardTop());
                playersBaccarat.get(j).printCardInHand();
            }
        }
        return deckOfBaccarat;
    }

    public ArrayList<PlayerBaccarat> getPlayersBaccarat() {
        return playersBaccarat;
    }
}
