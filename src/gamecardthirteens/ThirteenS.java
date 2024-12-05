package gamecardthirteens;

import deckofcards.Card;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThirteenS extends RulesOfThirteenS {
	private boolean[] checkTurn;
	private ArrayList<Card> cardPreTurn = new ArrayList<>();
	private boolean checkSkip = false;

    public ThirteenS(AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames) {
		// số người chơi
		super.numberOfPlayer = playerCount;
		// thêm người chơi
		addPlayer(withPlayer, playerNames);
		// chia bài
		dealCard();
		new DisplayPlayerCards(gameRoot, super.playersThirteenS);
		// nếu có người chơi sở hữu 4 quân 2 trên tay thì sẽ thắng luôn và không xếp hạng các người chơi còn lại
	}

	// Thêm người chơi vào trò chơi
	public void addPlayer(boolean playWithPlayer, List<String> playerNames) {
		this.playersThirteenS = new ArrayList<>();
		if (playWithPlayer) {
			for (int i = 0; i < numberOfPlayer; i++) {
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
			for (int i = 0; i < numberOfPlayer - 1; i++) {
				String nameOfBot = "Bot" + (i + 1);
				PlayerThirteenS bot = new BotThirteenS(nameOfBot);
				playersThirteenS.add(bot);
			}
		}
	}

	// Chia bài cho người chơi, mỗi người 13 lá
	public void dealCard() {
		deckOfThirteenS.shuffleDeck();
		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < numberOfPlayer; ++j){
				Card card = deckOfThirteenS.getCardTop();
				this.playersThirteenS.get(j).addCard(card);
			}
		}
	}
	// lấy số người chơi
	public void setNumberOfPlayer(int numberOfPlayer) {
		this.numberOfPlayer = numberOfPlayer;
	}

	// khởi tạo turn ban đầu, nếu ai có 3 Bích sẽ được đánh trước, nếu không ai có 3 Bích trên tay thì sẽ ngẫu nhiên người chơi đánh trước
	public void setTurn(ArrayList<PlayerThirteenS> playersThirteenS){
		checkTurn = new boolean[numberOfPlayer];
		for(int i = 0; i < numberOfPlayer; i++) {
			checkTurn[i] = false;
		}
		for(PlayerThirteenS player : playersThirteenS){
			for(Card card : player.getCardsInHand()){
				if(card.getRank() == 3 && card.getSuit() == 1){
					checkTurn[playersThirteenS.indexOf(player)] = true;
					return;
				}
			}
		}
	}

	// Set lại turn mới, mọi người skip trong turn trước sẽ được đánh lại trong turn mới
	public void resetTurn(ArrayList<PlayerThirteenS> playersThirteenS) {
		for (int i = 0; i < playersThirteenS.size(); ++i)
			checkTurn[i] = true;
	}

	// Lượt chơi sẽ kết thúc khi chỉ còn 1 người chưa Skip
	public boolean checkEndTurn(ArrayList<PlayerThirteenS> playersThirteenS) {
		int counter = 0;
		for (int i = 0; i < playersThirteenS.size(); ++i) {
			if (checkTurn[i])
				counter++;
		}
		return counter == 1;
	}
	// Lấy lựa chọn của người chơi
	public boolean playCards(PlayerThirteenS player, ArrayList<Card> listCardPlayed, String selected) {
		System.out.println("Select card (enter in format Rank-Suit, write on one line, separated by spaces) to play or enter 'Sort' to sort cards in hand or enter 'Skip' to skip turn:");
		player.setListCardPlayed(listCardPlayed);
		// Người chơi ấn Skip sẽ bỏ lượt
		if (selected.equals("Skip")) {
			return true;
		}
		// Người chơi ấp Sort sẽ sắp xếp bài trên tay
		if (selected.equals("Sort")) {
			player.sortCardsInHand();
		}
		if (getTypeOfCards(listCardPlayed).equals("Invalid")) {
			System.out.println("Invalid, please select again!");
			return false;
		}

		if (checkCardsDrop(listCardPlayed, cardPreTurn)) {
			for (Card card : listCardPlayed) {
				player.dropCard(card);
			}
			this.cardPreTurn = listCardPlayed;
			return true;
		}
		return false;
	}

	// Bắt đầu turn chính của game, game chỉ dừng lại khi đã có xếp hạng của các người chơi tham gia
	public void turnOfGame(AnchorPane gameRoot, ArrayList<PlayerThirteenS> playersThirteenS, String selected, ArrayList<Card> listCardPlayed) {
		int index = 0;
		// Nếu không ai có 3 bích thì sẽ random người đánh trước
		if(!checkEndTurn(playersThirteenS)){
			resetTurn(playersThirteenS);
			index++;
			System.out.println("-Turn " + index + " :");
		}
		boolean checkEndGame = false;
		while (!checkEndGame) {
			for (int i = 0; i < playersThirteenS.size(); i++) {
				if (checkTurn[i]) {
					// Lần lượt các người chơi sẽ đánh bài
					if (checkEndTurn(playersThirteenS)) {
						index++;
						System.out.println("-Turn " + index + " :");
						resetTurn(playersThirteenS);
						cardPreTurn.clear();
					}
					while (true) {
						if (selected.equals("Skip")) {
							System.out.println();
							checkTurn[i] = false;
							break;
						}
						if (selected.equals("Sort")) {
							playersThirteenS.get(i).sortCardsInHand();
						}
						if (selected.equals("Play")) {
							boolean check = false;
							while (!check) {
								check = playCards(playersThirteenS.get(i), listCardPlayed, selected);
								if (checkSkip) {
									checkTurn[i] = false;
									checkSkip = false;
								}
							}
							break;
						}
					}
				}
				endOfGame(playersThirteenS, numberOfPlayer);
				// Nếu còn 1 người chơi, in ra xếp hạng của người chơi đó và kết thúc game
				if(playersThirteenS.size() == 1){
					System.out.println(playersThirteenS.getFirst().getNameOfPlayer() + " got " +
							switch (numberOfPlayer) {
								case 2 -> "Second";
								case 3 -> "Third";
								case 4 -> "Fourth";
								default -> "";
							} + " place!");
					checkEndGame = true;
					break;
				}
			}
		}
	}
}