package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainMenu {
    public static BaccaratDataManager baccarat;

    public Scene createMainMenu(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Background
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/CardStart.jpg")));
        background.setFitWidth(1200);
        background.setFitHeight(675);
        background.setPreserveRatio(true);

        // Buttons
        Button playButton = createButton("PLAY", 200, 50, "-fx-background-color: linear-gradient(to bottom, #1D89F4, #1B62C5);");
        playButton.setOnMouseClicked(event -> stage.setScene(new GameSelectionMenu().createGameSelectionScene(stage)));

        Button settingButton = createButton("SETTING", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F3B91D, #CF8A08);");
        settingButton.setOnMouseClicked(event -> stage.setScene(new SettingsMenu().createSettingsScene(stage)));

        Button quitButton = createButton("QUIT", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F45A4A, #D93324);");
        quitButton.setOnMouseClicked(event -> stage.close());

        // Button Layout
        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(playButton, settingButton, quitButton);
        hBox.setLayoutX(300);
        hBox.setLayoutY(400);

        root.getChildren().addAll(background, hBox);
        return new Scene(root, 1200, 675);
    }

    private Button createButton(String text, double width, double height, String style) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setStyle(style + " -fx-background-radius: 5; -fx-border-radius: 10; -fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        return button;
    }
}
