package gameplay;

import deckofcards.Card;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import playerofgame.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class DisplayPlayer {
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

    public DisplayPlayer(Stage stage, AnchorPane gameRoot, ArrayList<Player> players, int currentPlayerIndex) {
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
        buttonBox.setLayoutX((sceneWidth - 400) / 2.0);
        buttonBox.setLayoutY(415);
        buttonBox.setStyle("-fx-background-color: rgba(0,0,0,0); -fx-background-radius: 2; -fx-padding: 5;");

        Button buttonPlay = MainMenu.createButton("Play",75, 37);
        Button buttonSkip = MainMenu.createButton("Skip", 75, 37);
        Button buttonSort = MainMenu.createButton("Sort", 75, 37);;
        Button buttonUnselect = MainMenu.createButton("Unselect", 100, 37);
        MainMenu.addHoverEffect(Arrays.asList(buttonPlay, buttonSkip, buttonSort, buttonUnselect));

        MainMenu.addHoverEffect(Arrays.asList(buttonPlay, buttonSkip, buttonSort, buttonUnselect));
        buttonPlay.setOnMouseClicked(event -> onPlayerAction.accept("Play"));
        buttonSkip.setOnMouseClicked(event -> onPlayerAction.accept("Skip"));
        buttonSort.setOnMouseClicked(event -> onPlayerAction.accept("Sort"));
        buttonUnselect.setOnMouseClicked(event -> onPlayerAction.accept("Unselect"));

        buttonBox.getChildren().addAll(buttonPlay, buttonSkip, buttonSort, buttonUnselect);
        gameRoot.getChildren().add(buttonBox);
    }

    public void showActionButtons(Consumer<String> actionHandler) {
        this.onPlayerAction = actionHandler;
    }
}
