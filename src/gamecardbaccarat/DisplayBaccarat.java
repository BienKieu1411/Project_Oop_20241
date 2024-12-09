package gamecardbaccarat;

import deckofcards.Card;
import gameplay.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import playerofgame.Player;
import java.util.ArrayList;

public class DisplayBaccarat {
    ArrayList<Card> cardsSelect = new ArrayList<>();
    ArrayList<Card> cardsCenter = new ArrayList<>();

    public DisplayBaccarat() {
    }

    public void displayPlayerHands(Stage stage, AnchorPane gameRoot, ArrayList<Player> players) {
        SettingsMenu settingsMenu = SettingsMenu.getInstance(); // Lấy thể hiện duy nhất
        DisplayMode modeSelect = settingsMenu.isImageMode() ? new ImageMode() : new TextMode();
        modeSelect.setCardsSelect(cardsSelect);
        modeSelect.setCardsCenter(cardsCenter);
        modeSelect.displayPlayerHands(stage, gameRoot, players, 0);
    }
}
