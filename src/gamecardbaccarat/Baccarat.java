package gamecardbaccarat;

public class Baccarat extends RulesOfBaccarat {
	// In ra thông tin Người chơi
	public void printPlayerInformation() {
		for (int i = 0; i < numberOfPlayer; ++i)
			playersBaccarat.get(i).printPlayer();
	}
}