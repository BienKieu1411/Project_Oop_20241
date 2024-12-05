package gamecardbaccarat;

import rulesofgame.SetGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SetGameBaccarat extends SetGame {
    private ArrayList<PlayerBaccarat> playersBaccarat;
    private int moneyPlayer;

    public SetGameBaccarat() {
    }

    // Thêm người chơi vào trò chơi
    public ArrayList<PlayerBaccarat> addPlayer() {
        this.playersBaccarat = new ArrayList<>();
        for (int i = 0; i < numberOfPlayer; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            String nameOfPerson = scanner.nextLine();  // Nhập tên người chơi
            PlayerBaccarat person = new PlayerBaccarat(nameOfPerson);
            person.addMoneyPlayer(this.moneyPlayer);
            playersBaccarat.add(person);
        }
        // Dùng upcasting và ghi đè để tạo bot
        for (int i = 0; i < numberOfPlayer; i++) {
            String nameOfBot = "Bot" + (i + 1);
            PlayerBaccarat bot = new BotBaccarat(nameOfBot);
            bot.addMoneyPlayer(this.moneyPlayer);
            playersBaccarat.add(bot);
        }
        Collections.shuffle(playersBaccarat);
        return playersBaccarat;
    }
    // Nhập vào số tiền ban đầu cho người chơi
    public void setMoney() {
        System.out.print("Enter each player's starting amount ($): ");
        int money = scanner.nextInt();
        scanner.nextLine();
        this.moneyPlayer = money;
    }

    // Chia bài cho người chơi
    public DeckOfBaccarat dealCard(DeckOfBaccarat deckOfBaccarat) {
        deckOfBaccarat.shuffleDeck();
        for (int i = 0; i < 3; ++i) {
            System.out.println("- Deal cards in turn " + (i + 1) + ": ");
            for (int j = 0; j < numberOfPlayer; ++j) {
                playersBaccarat.get(j).addCard(deckOfBaccarat.getCardTop());
                playersBaccarat.get(j).printCardInHand();
            }
        }
        return deckOfBaccarat;
    }

    private final Scanner scanner = new Scanner(System.in);
}
