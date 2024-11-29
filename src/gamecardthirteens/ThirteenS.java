package gamecardthirteens;

public class ThirteenS extends RulesOfThirteenS {

    public ThirteenS() {
		SetGameOfThirteenS setGame = new SetGameOfThirteenS();
		// số người chơi
		super.numberOfPlayer = setGame.setNumberOfPlayer();
		// thêm người chơi
		super.playersThirteenS = setGame.addPlayer();
		// chia bài
		super.deckOfThirteenS = setGame.dealCard(deckOfThirteenS);
		// nếu có người chơi sở hữu 4 quân 2 trên tay thì sẽ thắng luôn và không xếp hạng các người chơi còn lại
		if (checkWinner() == -1) {
            TurnOfThirteenS newTurn = new TurnOfThirteenS();
			// tạo turn mới
			newTurn.setNumberOfPlayer(super.numberOfPlayer);
			newTurn.setTurn(playersThirteenS);
			// bắt đầu turn chính
			newTurn.turnOfGame(playersThirteenS);
		} else {
			System.out.println(playersThirteenS.get(checkWinner()).getNameOfPlayer() + " wins the game!");
		}
	}
}