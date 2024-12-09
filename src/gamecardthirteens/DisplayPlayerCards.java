package gamecardthirteens;

import deckofcards.Card;
import gameplay.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import playerofgame.Player;

import java.util.ArrayList;
import java.util.function.Consumer;

public class DisplayPlayerCards {
    private final int sceneWidth = 1200;
    private HBox buttonBox = new HBox(10);
    private Consumer<String> onPlayerAction;
    private ArrayList<Card> cardsCenter = new ArrayList<>();
    private ArrayList<Card> cardsSelect = new ArrayList<>();

    public void setCardsCenter(ArrayList<Card> cardsCenter) {
        this.cardsCenter = cardsCenter;
    }

    public ArrayList<Card> getCardsSelect() {
        return cardsSelect;
    }

    public void clearCardsSelect() {
        for(Card card : cardsSelect)
            card.setSelected(false);
        this.cardsSelect.clear();
    }

    public DisplayPlayerCards(Stage stage, AnchorPane gameRoot, ArrayList<Player> players, int currentPlayerIndex) {
        displayPlayerHands(stage, gameRoot, players, currentPlayerIndex);
        addControlButtons(stage, gameRoot);
    }

    public void displayPlayerHands(Stage stage, AnchorPane gameRoot, ArrayList<Player> players, int index) {
        SettingsMenu settingsMenu = SettingsMenu.getInstance(); // Lấy thể hiện duy nhất
        DisplayMode modeSelect = settingsMenu.isImageMode() ? new ImageMode() : new TextMode();
        modeSelect.setCardsSelect(cardsSelect);
        modeSelect.setCardsCenter(cardsCenter);
        modeSelect.displayPlayerHands(stage, gameRoot, players, index);
    }

    private void addControlButtons(Stage stage, AnchorPane gameRoot) {
        buttonBox.setLayoutX((sceneWidth - 200) / 2.0);
        buttonBox.setLayoutY(450);
        buttonBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 2; -fx-padding: 5;");

        Button buttonPlay = createButton("Play", "blue");
        Button buttonSkip = createButton("Skip", "blue");
        Button buttonSort = createButton("Sort", "blue");
        Button buttonUnselect = createButton("Unselect", "blue");

        buttonPlay.setOnMouseClicked(event -> onPlayerAction.accept("Play"));
        buttonSkip.setOnMouseClicked(event -> onPlayerAction.accept("Skip"));
        buttonSort.setOnMouseClicked(event -> onPlayerAction.accept("Sort"));
        buttonUnselect.setOnMouseClicked(event -> onPlayerAction.accept("Unselect"));

        buttonBox.getChildren().addAll(buttonPlay, buttonSkip, buttonSort, buttonUnselect);
        gameRoot.getChildren().add(buttonBox);
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 5px;");
        return button;
    }

    public void showActionButtons(Consumer<String> actionHandler) {
        this.onPlayerAction = actionHandler;
    }
}