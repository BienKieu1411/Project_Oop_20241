import gamecardbaccarat.Baccarat;
import gamecardthirteens.ThirteenS;
import java.util.Scanner;

public class Main{
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("- Enter 1 to start a Baccarat game.");
        System.out.println("- Enter 2 to start a Thirteen game.");
        int selectedGame;
        do{
            System.out.print("Select game to start: ");
            selectedGame  = scanner.nextInt();
        }while (selectedGame < 1 || selectedGame > 2);
        switch (selectedGame) {
            case 1:
                Baccarat baccarat = new Baccarat();
                break;
            case 2:
                ThirteenS thirteenS = new ThirteenS();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedGame);
        }
    }
}