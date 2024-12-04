package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModeSelectionMenu {
    public Scene createModeSelectionScene(Stage stage, String game) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Background
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/CardStart.jpg")));
        background.setFitWidth(1200);
        background.setFitHeight(675);
        background.setPreserveRatio(true);

        // Buttons
        Button withPlayerButton = createButton("Play with Player", 200, 50, "-fx-background-color: linear-gradient(to bottom, #1D89F4, #1B62C5);");
        withPlayerButton.setOnMouseClicked(event -> stage.setScene(new PlayerCountMenu().createPlayerCountScene(stage, game, true)));

        Button withBotButton = createButton("Play with Bot", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F3B91D, #CF8A08);");
        withBotButton.setOnMouseClicked(event -> stage.setScene(new PlayerCountMenu().createPlayerCountScene(stage, game, false)));

        Button backButton = createButton("Back", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F45A4A, #D93324);");
        backButton.setOnMouseClicked(event -> stage.setScene(new GameSelectionMenu().createGameSelectionScene(stage)));

        // Layout
        withPlayerButton.setLayoutX(500);
        withPlayerButton.setLayoutY(300);
        withBotButton.setLayoutX(500);
        withBotButton.setLayoutY(370);
        backButton.setLayoutX(500);
        backButton.setLayoutY(440);

        root.getChildren().addAll(background, withPlayerButton, withBotButton, backButton);
        return new Scene(root, 1200, 675);
    }

    private Button createButton(String text, double width, double height, String style) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setStyle(style + " -fx-background-radius: 5; -fx-border-radius: 10; -fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        return button;
    }
}
