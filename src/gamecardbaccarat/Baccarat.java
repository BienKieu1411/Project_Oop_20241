package gamecardbaccarat;

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

public class Baccarat extends RulesOfBaccarat {
	// Khỏi tạo Constructor: khi khởi tạo 1 đối tượng Baccarat mới sẽ chạy luôn chương trình
	public Baccarat(Stage stage,AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames) {
		// Số người chơi
		super.numberOfPlayer = playerCount;
		// Them nguoi choi
		addPlayer(withPlayer, playerNames);
		// Chia bai
		DisplayBaccarat displayBaccarat = new DisplayBaccarat();

		dealCard(gameRoot, () -> {
			Timeline timeline = new Timeline();
			int[] currentPlayerIndex = {0};
			KeyFrame turnFrame = new KeyFrame(Duration.seconds(1), event -> { PlayerBaccarat currentPlayer = playersBaccarat.get(currentPlayerIndex[0]);
				currentPlayer.setCardsFaceUp();
				displayBaccarat.displayPlayerHands(stage, gameRoot, playersBaccarat); // Kiểm tra kết thúc game
				if (currentPlayerIndex[0] == numberOfPlayer - 1) {
					 // Hiển thị thông tin người chiến thắng sau khi game kết thúc
					timeline.stop();
					PlayerBaccarat playerwin = super.winnerBaccarat();
					new WinnerDisplay(stage, gameRoot, playerwin);
				}
				// Di chuyển đến người chơi tiếp theo
				currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % playersBaccarat.size();
				});
			timeline.getKeyFrames().add(turnFrame);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play(); });
	}

	// Thêm người chơi vào trò chơi
	public void addPlayer(boolean playWithPlayer, List<String> playerNames) {
		this.playersBaccarat = new ArrayList<>();
		if (playWithPlayer) {
			for (int i = 0; i < numberOfPlayer; i++) {
				String nameOfPerson = playerNames.get(i);  //tên người chơi
				PlayerBaccarat person = new PlayerBaccarat(nameOfPerson, false);
				playersBaccarat.add(person);
			}
			Collections.shuffle(playersBaccarat);
		}
		else{
			PlayerBaccarat person = new PlayerBaccarat("You", false);
			playersBaccarat.add(person);
			// Dùng upcasting và ghi đè để tạo bot
			for (int i = 0; i < numberOfPlayer - 1; i++) {
				String nameOfBot = "Bot" + (i + 1);
				PlayerBaccarat bot = new BotBaccarat(nameOfBot, true);
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
		new DealCardAnimation(gameRoot, numberOfPlayer, 3, onFinished);
	}

}