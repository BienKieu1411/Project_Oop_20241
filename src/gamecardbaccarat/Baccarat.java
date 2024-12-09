package gamecardbaccarat;

import deckofcards.Card;
import deckofcards.Deck;
import gameplay.DealCardAnimation;
import gameplay.DisplayPlayer;
import gameplay.SettingsMenu;
import gameplay.WinnerDisplay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import playerofgame.Bot;
import playerofgame.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baccarat {
	protected Deck deckOfBaccarat;
	protected int numberOfPlayer;
	protected ArrayList<Player> playersBaccarat = new ArrayList<>();
	// Khỏi tạo Constructor: khi khởi tạo 1 đối tượng Baccarat mới sẽ chạy luôn chương trình
	public Baccarat(Stage stage,AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames) {
		// Số người chơi
		numberOfPlayer = playerCount;
		deckOfBaccarat = new Deck(36);
		// Them nguoi choi
		addPlayer(withPlayer, playerNames);
		// Chia bai
		SettingsMenu settingsMenu = SettingsMenu.getInstance(); // Lấy thể hiện duy nhất
		if(settingsMenu.isImageMode()){
			dealCard(gameRoot, () -> {
				Timeline timeline = new Timeline();
				int[] currentPlayerIndex = {0};
				KeyFrame turnFrame = new KeyFrame(Duration.seconds(1), event -> {
					Player currentPlayer = playersBaccarat.get(currentPlayerIndex[0]);
					currentPlayer.setCardsFaceUp();
					DisplayPlayer displayBaccarat = new DisplayPlayer(stage, gameRoot, playersBaccarat, 0);
					if (currentPlayerIndex[0] == numberOfPlayer - 1) {
						// Hiển thị thông tin người chiến thắng sau khi game kết thúc
						timeline.stop();
						Player playerwin = winnerBaccarat();
						new WinnerDisplay(stage, gameRoot, playerwin);
					}
					// Di chuyển đến người chơi tiếp theo
					currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % playersBaccarat.size();
				});
				timeline.getKeyFrames().add(turnFrame);
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.play(); });
		}
		else {
			dealCard(gameRoot, () -> {});
			Timeline timeline = new Timeline();
			int[] currentPlayerIndex = {0};
			KeyFrame turnFrame = new KeyFrame(Duration.seconds(1), event -> {
				Player currentPlayer = playersBaccarat.get(currentPlayerIndex[0]);
				currentPlayer.setCardsFaceUp();
				DisplayPlayer displayBaccarat = new DisplayPlayer(stage, gameRoot, playersBaccarat, 0);				if (currentPlayerIndex[0] == numberOfPlayer - 1) {
					// Hiển thị thông tin người chiến thắng sau khi game kết thúc
					timeline.stop();
					Player playerwin = winnerBaccarat();
					new WinnerDisplay(stage, gameRoot, playerwin);
				}
				// Di chuyển đến người chơi tiếp theo
				currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % playersBaccarat.size();
			});
			timeline.getKeyFrames().add(turnFrame);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
		}
	}

	// Thêm người chơi vào trò chơi
	public void addPlayer(boolean playWithPlayer, List<String> playerNames) {
		this.playersBaccarat = new ArrayList<>();
		if (playWithPlayer) {
			for (int i = 0; i < numberOfPlayer; i++) {
				String nameOfPerson = playerNames.get(i);  //tên người chơi
				Player person = new Player(nameOfPerson, false);
				playersBaccarat.add(person);
			}
			Collections.shuffle(playersBaccarat);
		}
		else{
			Player person = new Player("You", false);
			playersBaccarat.add(person);
			// Dùng upcasting và ghi đè để tạo bot
			for (int i = 0; i < numberOfPlayer - 1; i++) {
				String nameOfBot = "Bot" + (i + 1);
				Player bot = new Bot(nameOfBot, true);
				playersBaccarat.add(bot);
			}
		}
	}

	// Chia bài cho người chơi
	public void dealCard(AnchorPane gameRoot, Runnable onFinished) {
		this.deckOfBaccarat.shuffleDeck();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < numberOfPlayer; ++j){
				Card card = deckOfBaccarat.getCardTop();
				this.playersBaccarat.get(j).addCard(card);
			}
		}
		SettingsMenu settingsMenu = SettingsMenu.getInstance(); // Lấy thể hiện duy nhất
		if(settingsMenu.isImageMode()){
			new DealCardAnimation(gameRoot, numberOfPlayer, 3, onFinished);
		}
	}
	// Tìm ra người thắng cuộc
	public Player winnerBaccarat() {
		int index = 0;
		int maxPoint = 0;
		// So sánh điểm
		for (int i = 0; i < numberOfPlayer; i++) {
			int point = getScoreOfPlayer(playersBaccarat.get(i));
			if (point > maxPoint) {
				maxPoint = point;
				index = i;
			}
		}
		// Cộng tiền cho người thắng
		for (int i = 0; i < numberOfPlayer; ++i) {
			if (i != index) {
				playersBaccarat.get(index).addMoneyPlayer(playersBaccarat.get(i).getMoneyPlayer());
				playersBaccarat.get(i).setMoneyPlayer(0);
			}
		}
		return playersBaccarat.get(index);
	}

	public CardOfBaccarat biggestCardInHand(Player player) {
		Card currentCard = player.getCardsInHand().getFirst();
        CardOfBaccarat card = new CardOfBaccarat(currentCard.printRank(), currentCard.printSuit());
        for (int i = 1; i < player.getCardsInHand().size(); i++) {
			currentCard = player.getCardsInHand().get(i);
			CardOfBaccarat cardinhand;
            cardinhand = new CardOfBaccarat(currentCard.printRank(), currentCard.printSuit());
            if (cardinhand.compareCard(card) > 0)
				card = cardinhand;
		}
		return card;
	}

	// Tính điểm của người chơi
	public int getScoreOfPlayer(Player player) {
		CardOfBaccarat biggestCard = biggestCardInHand(player);
		int scoreOfPlayer = 0;
		for (int i = 0; i < 3; ++i) {
			CardOfBaccarat cardinhand = new CardOfBaccarat(player.getCardsInHand().get(i).printRank(), player.getCardsInHand().get(i).printSuit());
			scoreOfPlayer += cardinhand.getRank();
			if (scoreOfPlayer > 10)
				scoreOfPlayer -= 10;
		}
		// Tính điểm theo trọng số : điểm cao nhất -> lá bài có chất cao nhất -> lá bài có điểm cao nhất
		return 100 * scoreOfPlayer + 10 * biggestCard.getSuit() + biggestCard.getRank();
	}
}