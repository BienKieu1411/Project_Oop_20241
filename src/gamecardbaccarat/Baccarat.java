package gamecardbaccarat;

import java.util.Scanner;

public class Baccarat extends RulesOfBaccarat {

	// Khỏi tạo Constructor: khi khởi tạo 1 đối tượng Baccarat mới sẽ chạy luôn chương trình
	public Baccarat() {
		setNumberOfPlayer();
		setMoney();
		addPlayer();
		dealCard();
		winnerBaccarat();
		printPlayerInformation();
	}

	// Lấy số lượng người chơi
	public void setNumberOfPlayer() {
		int numberOfPersons;
		do {
			System.out.print("Enter number of players: ");
			numberOfPersons = scanner.nextInt();
			scanner.nextLine();
		} while (numberOfPersons < 2);
		this.numberOfPlayer = numberOfPersons;
	}

	// Nhập vào số tiền ban đầu cho người chơi
	public void setMoney() {
		System.out.print("Enter each player's starting amount ($): ");
		int money = scanner.nextInt();
		scanner.nextLine();
		this.moneyPlayer = money;
	}

	// Thêm người chơi vào game
	public void addPlayer() {
		for (int i = 0; i < numberOfPlayer; i++) {
			System.out.print("Player " + (i + 1) + ": ");
			String name = scanner.nextLine();  // Nhập tên người chơi
			PlayerBaccarat person = new PlayerBaccarat();
			person.setNameOfPlayer(name);
			person.setMoneyPlayer(this.moneyPlayer);
			playersBaccarat.add(person);
		}
	}

	// Chia bài cho người chơi
	public void dealCard() {
		deckOfBaccarat.shuffleDeck();
		for (int i = 0; i < 3; ++i) {
			System.out.println("- Deal cards in turn " + (i + 1) + ": ");
			for (int j = 0; j < numberOfPlayer; ++j) {
				playersBaccarat.get(j).addCard(deckOfBaccarat.getCardTop());
				playersBaccarat.get(j).printCardInHand();
			}
		}
	}

	// In ra thông tin Người chơi
	public void printPlayerInformation() {
		for (int i = 0; i < numberOfPlayer; ++i)
			playersBaccarat.get(i).printPlayer();
	}

	// Scannner dùng để nhập các giá trị người chơi truyền vào
	private final Scanner scanner = new Scanner(System.in);
}