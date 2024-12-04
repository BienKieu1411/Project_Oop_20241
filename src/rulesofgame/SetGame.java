package rulesofgame;

import java.util.Scanner;

public class SetGame {
    protected int numberOfPersons, numberOfBots, numberOfPlayer;

    public SetGame() {
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

    private final Scanner scanner = new Scanner(System.in);
}
