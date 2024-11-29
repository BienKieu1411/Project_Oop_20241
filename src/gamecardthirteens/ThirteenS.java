package gamecardthirteens;

public class ThirteenS extends RulesOfThirteenS {

    public ThirteenS() {
		SetGameOfThirteenS setGame = new SetGameOfThirteenS();
		super.numberOfPlayer = setGame.setNumberOfPlayer();
		super.playersThirteenS = setGame.addPlayer();
		super.deckOfThirteenS = setGame.dealCard(deckOfThirteenS);
		if (checkWinner() == -1) {
            TurnOfThirteenS newTurn = new TurnOfThirteenS();
			newTurn.setNumberOfPlayer(super.numberOfPlayer);
			newTurn.setTurn(playersThirteenS);
			newTurn.turnOfGame(playersThirteenS);
		} else {
			System.out.println(playersThirteenS.get(checkWinner()).getNameOfPlayer() + " wins the game!");
		}
	}
}