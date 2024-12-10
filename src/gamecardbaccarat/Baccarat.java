package gamecardbaccarat;

import deckofcards.Card;
import deckofcards.Deck;
import gameplay.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import logicgame.Rules;
import logicgame.SetGame;
import playerofgame.Player;
import java.util.ArrayList;
import java.util.List;

public class Baccarat {
	protected Deck deckOfBaccarat;
	protected int numberOfPlayer;
	protected ArrayList<Player> playersBaccarat = new ArrayList<>();
	// Khỏi tạo Constructor: khi khởi tạo 1 đối tượng Baccarat mới sẽ chạy luôn chương trình
	public Baccarat(Stage stage, AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames) {
		// Số người chơi
		SetGame setGame = new SetGame();
		Rules rules = new Rules();
		numberOfPlayer = playerCount;
		deckOfBaccarat = new Deck(36);
		// Them nguoi choi
		setGame.setNumberOfPlayer(playerCount);
		setGame.addPlayer(withPlayer, playerNames, rules);
		playersBaccarat = setGame.getPlayers();
		SettingsMenu settingsMenu = SettingsMenu.getInstance(); // Lấy thể hiện duy nhất stage, gameRoot, playersBaccarat, 0
		if(settingsMenu.isImageMode()) {
			ShuffleEffect shuffleEffect = new ShuffleEffect(gameRoot, 3 * numberOfPlayer);
			shuffleEffect.startShuffle(() -> {
				// Sau khi hoàn tất tráo bài, bắt đầu chia bài
				setGame.dealCard(stage, gameRoot, deckOfBaccarat, 3, () -> {
					turnOfGame(stage, gameRoot);
				});
			});
		} else {
			setGame.dealCard(stage, gameRoot, deckOfBaccarat, 3, () -> {});
			turnOfGame(stage, gameRoot);
		}
	}

	public void turnOfGame(Stage stage, AnchorPane gameRoot) {
		Timeline timeline = new Timeline();
		int[] currentPlayerIndex = {0};
		KeyFrame turnFrame = new KeyFrame(Duration.seconds(1), event -> {
			Player currentPlayer = playersBaccarat.get(currentPlayerIndex[0]);
			currentPlayer.setCardsFaceUp();
			new DisplayPlayer(stage, gameRoot, playersBaccarat, 0);
			if (currentPlayerIndex[0] == numberOfPlayer - 1) {
				// Hiển thị thông tin người chiến thắng sau khi game kết thúc
				timeline.stop();
				Player playerwin = winnerBaccarat();
				new Winner(stage, gameRoot, playerwin.getNameOfPlayer());
			}
			// Di chuyển đến người chơi tiếp theo
			currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % playersBaccarat.size();
		});
		timeline.getKeyFrames().add(turnFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
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
