package gamecardthirteens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ThirteenS extends RulesOfThirteenS {

	private ArrayList<CardOfThirteenS> cardPreTurn = new ArrayList<>();
	private boolean[] checkTurn;
	private boolean checkSkip = false;
	private int numberOfPersons, numberOfBots;

	public ThirteenS() {
		setNumberOfPlayer();
		addPlayer();
		dealCard();
		if (checkWinner() == -1) {
			turnOfGame();
		} else {
			System.out.println(playersThirteenS.get(checkWinner()).getNameOfPlayer() + " wins the game!");
		}
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

	public void setNumberOfPlayer() {
		setNumberOfPersons();
		setNumberOfBots();
		super.numberOfPlayer = this.numberOfPersons + this.numberOfBots;
		checkTurn = new boolean[numberOfPlayer];
	}

	public void addPlayer() {
		for (int i = 0; i < numberOfPersons; i++) {
			System.out.print("Player " + (i + 1) + ": ");
			String nameOfPerson = scanner.nextLine();  // Nhập tên người chơi
			PlayerThirteenS person = new PlayerThirteenS(nameOfPerson);
			person.setNameOfPlayer(nameOfPerson);
			playersThirteenS.add(person);
		}
		for (int i = 0; i < numberOfBots; i++) {
			String nameOfBot = "Bot " + Integer.toString(i + 1);
			PlayerThirteenS bot = new BotThirteenS(nameOfBot);
			playersThirteenS.add(bot);
		}
		Collections.shuffle(playersThirteenS);
	}

	protected void dealCard() {
		deckOfThirteenS.shuffleDeck();
		for (int i = 0; i < 13; ++i) {
			System.out.println("- Deal cards in turn " + (i + 1) + ": ");
			for (int j = 0; j < numberOfPlayer; ++j) {
				CardOfThirteenS card = (CardOfThirteenS) deckOfThirteenS.getCardTop();
				playersThirteenS.get(j).addCard(card);
				playersThirteenS.get(j).printCardInHand();
			}
		}
	}

	public void resetTurn() {
		for (int i = 0; i < numberOfPlayer; ++i)
			checkTurn[i] = true;
	}


	//
	public boolean checkEndTurn() {
		int counter = 0;
		for (int i = 0; i < numberOfPlayer; ++i) {
			if (checkTurn[i])
				counter++;
		}
		return counter == 1;
	}

	public boolean playCards(PlayerThirteenS player) {

		ArrayList<CardOfThirteenS> cards = new ArrayList<>();// Những lá bài chọn để đánh

		System.out.println("Select card (enter in format Rank-Suit, write on one line, separated by spaces) to play or enter 'Sort' to sort cards in hand or enter 'Skip' to skip turn:");

		player.setListCardPlayed();

		String listCardPlayed = player.getListCardPlayed();
		if (listCardPlayed.equals("Skip")) {
			checkSkip = true;
			return true;
		}
		if (listCardPlayed.equals("Sort")) {
			player.sortCardsInHand();
		}

		for (String selectedCard : listCardPlayed.split(" ")) {
			String[] rankandsuit = selectedCard.split("-");
			if (rankandsuit.length != 2) {
				System.out.println("Invalid, please select again!");
				return false;
			}
			CardOfThirteenS card = new CardOfThirteenS(rankandsuit[0], rankandsuit[1]);
			if ((player.getCardsInHand()).contains(card)) {
				cards.add(card);
			} else {
				System.out.println("Invalid, please select again!");
				return false;
			}
		}


		if (getTypeOfCards(cards).equals("Invalid")) {
			System.out.println("Invalid, please select again!");
			return false;
		}

		if (checkCardsDrop(cards, cardPreTurn)) {
			System.out.print(player.getNameOfPlayer() + " plays cards: ");
			for (CardOfThirteenS card : cards) {
				System.out.print(card.printRank() + "-" + card.printSuit() + " ");
				player.dropCard(card);
			}
			System.out.println();
			player.printCardInHand();
			this.cardPreTurn = cards;
			return true;
		}
		System.out.println("The cards you played are smaller!");
		return false;
	}

	public void turnOfGame() {
		int index = 1;
		resetTurn();
		System.out.println("- Turn" + index + ":");
		boolean checkEndGame = false;
		while (!checkEndGame) {
			for (int i = 0; i < numberOfPlayer; i++) {
				if (checkTurn[i]) {
					if (checkEndTurn()) {
						index++;
						System.out.println("-Turn " + index + " :");
						resetTurn();
						cardPreTurn.clear();
					}
					playersThirteenS.get(i).printCardInHand();
					System.out.println(playersThirteenS.get(i).getNameOfPlayer() + " invites to choose: ");
					String getSelection;
					while (true) {
						System.out.println("Choose 'Skip' or 'Sort' or 'Play'");
						playersThirteenS.get(i).setSelection();
						getSelection = playersThirteenS.get(i).getSelection(cardPreTurn);
						if (getSelection.equals("Skip")) {
							checkTurn[i] = false;
							break;
						}
						if (getSelection.equals("Sort")) {
							playersThirteenS.get(i).sortCardsInHand();
							playersThirteenS.get(i).printCardInHand();
						}
						if (getSelection.equals("Play")) {
							boolean check = false;
							while (!check) {
								check = playCards(playersThirteenS.get(i));
								if (checkSkip) {
									checkTurn[i] = false;
									checkSkip = false;
								}
							}
							break;
						}
					}
				}
				System.out.println();
				if (endOfGame() != -1) {
					checkEndGame = true;
					break;
				}
			}
		}
		System.out.println(playersThirteenS.get(endOfGame()).getNameOfPlayer() + " wins the game!");
	}

	private final Scanner scanner = new Scanner(System.in);

}