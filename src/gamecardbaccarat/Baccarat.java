package gamecardbaccarat;

import deckofcards.Card;
import gamecardbaccarat.BotBaccarat;
import gamecardbaccarat.PlayerBaccarat;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baccarat extends RulesOfBaccarat {
	private boolean[] checkTurn;
	// Khỏi tạo Constructor: khi khởi tạo 1 đối tượng Baccarat mới sẽ chạy luôn chương trình
	public Baccarat(AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames) {
		// Số người chơi
		super.numberOfPlayer = playerCount;
		// Them nguoi choi
		addPlayer(withPlayer, playerNames);
		// Chia bai
		dealCard();

		new DisplayBaccarat(gameRoot,playersBaccarat,playerCount);
	}

	// Thêm người chơi vào trò chơi
	public void addPlayer(boolean playWithPlayer, List<String> playerNames) {
		this.playersBaccarat = new ArrayList<>();
		if (playWithPlayer) {
			for (int i = 0; i < numberOfPlayer; i++) {
				String nameOfPerson = playerNames.get(i);  //tên người chơi
				PlayerBaccarat person = new PlayerBaccarat(nameOfPerson);
				playersBaccarat.add(person);
			}
			Collections.shuffle(playersBaccarat);
		}
		else{
			PlayerBaccarat person = new PlayerBaccarat("You");
			playersBaccarat.add(person);
			// Dùng upcasting và ghi đè để tạo bot
			for (int i = 0; i < numberOfPlayer - 1; i++) {
				String nameOfBot = "Bot" + (i + 1);
				PlayerBaccarat bot = new BotBaccarat(nameOfBot);
				playersBaccarat.add(bot);
			}
		}
	}

	// Chia bài cho người chơi, mỗi người 13 lá
	public void dealCard() {
		this.deckOfBaccarat.shuffleDeck();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < numberOfPlayer; ++j){
				Card card = deckOfBaccarat.getCardTop();
				this.playersBaccarat.get(j).addCard(card);
			}
		}
	}

}