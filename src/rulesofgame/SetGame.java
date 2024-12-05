package rulesofgame;

import java.util.Scanner;

public class SetGame {
    protected int numberOfPlayer;

    public SetGame() {
    }

    // Tổng số người chơi là tổng của người và Bot
    public void setNumberOfPlayer(int countPlayer) {
        this.numberOfPlayer = countPlayer;
    }

    private final Scanner scanner = new Scanner(System.in);
}
