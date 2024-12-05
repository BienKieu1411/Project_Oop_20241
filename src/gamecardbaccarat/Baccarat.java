package gamecardbaccarat;

public class Baccarat extends RulesOfBaccarat {
	// Khỏi tạo Constructor: khi khởi tạo 1 đối tượng Baccarat mới sẽ chạy luôn chương trình
	public Baccarat() {
		SetGameBaccarat setGame = new SetGameBaccarat();
		setGame.setMoney();
		super.playersBaccarat = setGame.addPlayer();
		winnerBaccarat();
		printPlayerInformation();
	}

	// In ra thông tin Người chơi
	public void printPlayerInformation() {
		for (int i = 0; i < numberOfPlayer; ++i)
			playersBaccarat.get(i).printPlayer();
	}
}