package gameplay;

import deckofcards.Card;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import playerofgame.Player;

import java.util.ArrayList;

public interface DisplayMode {
    void setCardsSelect(ArrayList<Card> cardsSelect);
    void setCardsCenter(ArrayList<Card> cardsCenter);
    void displayPlayerHands(Stage stage, AnchorPane gameRoot, ArrayList<Player> players, int index);
}
