package gamecardthirteens;

import deckofcards.Card;
import gameplay.DealCardAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ThirteenS extends RulesOfThirteenS {
	private boolean[] checkTurn;
	private ArrayList<Card> cardPreTurn = new ArrayList<>();

	public ThirteenS(Stage stage, AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames) {
		// số người chơi
		super.numberOfPlayer = playerCount;
		// thêm người chơi
		addPlayer(withPlayer, playerNames);
		// chia bài
		dealCard(gameRoot, () -> {
			setTurn();
			turnOfGame(stage, gameRoot, playersThirteenS);
		});
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
	public void dealCard(AnchorPane gameRoot, Runnable onFinished) {
		deckOfThirteenS.shuffleDeck();
		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < numberOfPlayer; ++j){
				Card card = deckOfThirteenS.getCardTop();
				this.playersThirteenS.get(j).addCard(card);
			}
		}
		new DealCardAnimation(gameRoot, numberOfPlayer, 13, onFinished);
	}

	// khởi tạo turn ban đầu, nếu ai có 3 Bích sẽ được đánh trước, nếu không ai có 3 Bích trên tay thì sẽ ngẫu nhiên người chơi đánh trước
	public void setTurn(){
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
	public void resetTurn() {
		for (int i = 0; i < playersThirteenS.size(); ++i)
			checkTurn[i] = true;
	}

	// Lượt chơi sẽ kết thúc khi chỉ còn 1 người chưa Skip
	public boolean checkEndTurn() {
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
	public void turnOfGame(Stage stage, AnchorPane gameRoot, ArrayList<PlayerThirteenS> playersThirteenS) {
		if (checkEndTurn()) {
			resetTurn();
		}

		DisplayPlayerCards displayPlayerCards = new DisplayPlayerCards(stage, gameRoot, playersThirteenS, 0);
		Timeline gameTimeline = new Timeline();
		int[] currentPlayerIndex = {0};
		KeyFrame turnFrame = new KeyFrame(Duration.seconds(1), event -> {
			if (checkEndTurn()) {
				resetTurn();
				cardPreTurn.clear();
			}

			PlayerThirteenS currentPlayer = playersThirteenS.get(currentPlayerIndex[0]);
			if (checkTurn[currentPlayerIndex[0]]) {
				currentPlayer.setCardsFaceUp();
				displayPlayerCards.displayPlayerHands(stage, gameRoot, playersThirteenS, currentPlayerIndex[0]);
				// Hiển thị các nút hành động
				displayPlayerCards.showActionButtons(action -> {
                    switch (action) {
                        case "Skip" -> {
                            // Người chơi bỏ lượt
							displayPlayerCards.clearCardsSelect();
                            checkTurn[currentPlayerIndex[0]] = false;
                            endPlayerTurn(currentPlayer, currentPlayerIndex, playersThirteenS, gameTimeline);
                        }
                        case "Play" ->
                            // Xử lý hành động đánh bài của người chơi (ví dụ: đánh bài hợp lệ)
                                endPlayerTurn(currentPlayer, currentPlayerIndex, playersThirteenS, gameTimeline);
                        case "Sort" -> {
                            // Sắp xếp bài
                            currentPlayer.sortCardsInHand();
                            displayPlayerCards.displayPlayerHands(stage, gameRoot, playersThirteenS, currentPlayerIndex[0]);
                        }
                        case "Unselect" ->
                            // Hủy chọn bài (nếu có)
                                displayPlayerCards.displayPlayerHands(stage, gameRoot, playersThirteenS, currentPlayerIndex[0]);
                    }
					displayPlayerCards.clearActionButtons();
				});
			} else {
				displayPlayerCards.displayPlayerHands(stage, gameRoot, playersThirteenS, currentPlayerIndex[0]);
				endPlayerTurn(currentPlayer, currentPlayerIndex, playersThirteenS, gameTimeline);
			}
		});

		gameTimeline.getKeyFrames().add(turnFrame);
		gameTimeline.setCycleCount(Timeline.INDEFINITE);
		gameTimeline.play();
	}

	private void endPlayerTurn(PlayerThirteenS currentPlayer, int[] currentPlayerIndex, ArrayList<PlayerThirteenS> playersThirteenS, Timeline gameTimeline) {
		currentPlayer.setCardsFaceDown();
		currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % playersThirteenS.size();
		if (playersThirteenS.size() == 1) {
			gameTimeline.stop();
			System.out.println("Trò chơi kết thúc!");
		}
	}

}
/*
				if (checkTurn[i]) {
					// Lần lượt các người chơi sẽ đánh bài
					if (checkEndTurn()) {
						resetTurn();
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
				if (playersThirteenS.size() == 1) {
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
 */