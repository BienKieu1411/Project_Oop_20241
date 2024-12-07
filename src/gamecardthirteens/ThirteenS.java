package gamecardthirteens;

import deckofcards.Card;
import gameplay.DealCardAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThirteenS extends RulesOfThirteenS {
	private boolean[] checkTurn;
	private ArrayList<Card> cardPreTurn = new ArrayList<>();
	private int startTurn;

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
		int counter = 0, index = 0;
		for (int i = 0; i < playersThirteenS.size(); ++i) {
			if (checkTurn[i]){
				counter++;
				index = i;
			}
		}
		if(counter == 1){
			startTurn = index;
			return true;
		}
		return false;
	}

	// Lấy lựa chọn của người chơi
	public boolean playCards(PlayerThirteenS player, ArrayList<Card> listCardPlayed) {
		if (checkCardsDrop(listCardPlayed, cardPreTurn)) {
			for (Card card : listCardPlayed) {
				player.dropCard(card); // Xóa bài khỏi tay người chơi
			}
			this.cardPreTurn = new ArrayList<>(listCardPlayed); // Cập nhật bài đã đánh
			return true;
		}
		return false;
	}


	// Bắt đầu turn chính của game, game chỉ dừng lại khi đã có xếp hạng của các người chơi tham gia
	public void turnOfGame(Stage stage, AnchorPane gameRoot, ArrayList<PlayerThirteenS> playersThirteenS) {
		DisplayPlayerCards displayPlayerCards = new DisplayPlayerCards(stage, gameRoot, playersThirteenS, 0);
		Timeline gameTimeline = new Timeline();
		int[] currentPlayerIndex = {0};
		if (checkEndTurn()) {
			resetTurn();
			currentPlayerIndex[0] = startTurn;
		}
		KeyFrame turnFrame = new KeyFrame(Duration.seconds(1), event -> {
			if (checkEndTurn()) {
				resetTurn();
				currentPlayerIndex[0] = startTurn;
				cardPreTurn.clear();
				displayPlayerCards.setCardsCenter(cardPreTurn);
			}

			PlayerThirteenS currentPlayer = playersThirteenS.get(currentPlayerIndex[0]);
			if (checkTurn[currentPlayerIndex[0]]) {
				currentPlayer.setCardsFaceUp();
				displayPlayerCards.displayPlayerHands(stage, gameRoot, playersThirteenS, currentPlayerIndex[0]);
				// Hiển thị các nút hành động
				displayPlayerCards.showActionButtons(action -> {
                    switch (action) {
						case "Skip" -> {
							displayPlayerCards.clearCardsSelect();
							checkTurn[currentPlayerIndex[0]] = false;
							// Giữ nguyên bài trung tâm khi người chơi bỏ lượt
							displayPlayerCards.setCardsCenter(cardPreTurn);
							endPlayerTurn(currentPlayer, currentPlayerIndex, playersThirteenS, gameTimeline);
						}

						case "Play" -> {
							ArrayList<Card> cards = displayPlayerCards.getCardsSelect();
							currentPlayer.setListCardPlayed(cards);
							if (playCards(currentPlayer, currentPlayer.getListCardPlayed())) {
								displayPlayerCards.setCardsCenter(new ArrayList<>(cardPreTurn)); // Hiển thị bài trung tâm
								displayPlayerCards.clearCardsSelect();
								endPlayerTurn(currentPlayer, currentPlayerIndex, playersThirteenS, gameTimeline);
							} else {
								System.out.println("Invalid move. Try again.");
							}
							displayPlayerCards.clearCardsSelect();
						}
                        case "Sort" -> {
                            currentPlayer.sortCardsInHand();
                            displayPlayerCards.displayPlayerHands(stage, gameRoot, playersThirteenS, currentPlayerIndex[0]);
                        }
                        case "Unselect" ->{
                            displayPlayerCards.clearCardsSelect();
							displayPlayerCards.displayPlayerHands(stage, gameRoot, playersThirteenS, currentPlayerIndex[0]);
						}
                    }
					displayPlayerCards.clearActionButtons();
				});
			} else {
				displayPlayerCards.displayPlayerHands(stage, gameRoot, playersThirteenS, 0);
				endPlayerTurn(currentPlayer, currentPlayerIndex, playersThirteenS, gameTimeline);
			}
		});

		gameTimeline.getKeyFrames().add(turnFrame);
		gameTimeline.setCycleCount(Timeline.INDEFINITE);
		gameTimeline.play();
	}

	private void endPlayerTurn(PlayerThirteenS currentPlayer, int[] currentPlayerIndex, ArrayList<PlayerThirteenS> playersThirteenS, Timeline gameTimeline) {
		currentPlayer.setCardsFaceDown();
		endOfGame(playersThirteenS, numberOfPlayer);
		currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % playersThirteenS.size();
		if (playersThirteenS.size() == 1) {
			gameTimeline.stop();
			System.out.println("Trò chơi kết thúc!");
		}
	}

}
/*
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