package gamecardthirteens;

import deckofcards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SetGameOfThirteenS{
    private int numberOfPersons, numberOfBots, numberOfPlayer;
    private ArrayList<PlayerThirteenS> playersThirteenS;
    public SetGameOfThirteenS() {
    }

    public void setNumberOfPersons() {
        // Kiểm tra nhập vào đúng định dạng int
        // Số người chơi: 1-4
        while (true) {
            System.out.print("Enter number of players: ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine();
                numberOfPersons = input;
                if (numberOfPersons >= 1 && numberOfPersons <= 4)
                    break;
            }
            System.out.println("Invalid input");
        }
    }

    public void setNumberOfBots() {
        // Kiểm tra nhập vào đúng định dạng int
        // Tổng số lượng người chơi và bot: 2-4
        while (true) {
            System.out.print("Enter number of bot: ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine();
                numberOfBots = input;
                int totalPlayers = numberOfPersons + numberOfBots;
                if (totalPlayers >= 2 && totalPlayers <= 4)
                    break;
            }
            System.out.println("Invalid input");
        }
    }

    // Tổng số người chơi là tổng của người và Bot
    public int setNumberOfPlayer() {
        setNumberOfPersons();
        setNumberOfBots();
        numberOfPlayer = numberOfBots + numberOfPersons;
        return numberOfPlayer;
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
