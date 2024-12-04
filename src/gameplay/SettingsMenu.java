package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsMenu {
    private boolean isImageMode = true;

    public Scene createSettingsScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Background
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/CardStart.jpg")));
        background.setFitWidth(1200);
        background.setFitHeight(675);
        background.setPreserveRatio(true);

        // Toggle Mode Button
        Button toggleMode = createButton(isImageMode ? "Image Mode" : "Text Mode", 200, 50, "-fx-background-color: linear-gradient(to bottom, #1D89F4, #1B62C5);");
        toggleMode.setOnMouseClicked(event -> {
            isImageMode = !isImageMode;
            toggleMode.setText(isImageMode ? "Image Mode" : "Text Mode");
        });

        // Back Button
        Button backButton = createButton("Back", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F45A4A, #D93324);");
        backButton.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));

        toggleMode.setLayoutX(500);
        toggleMode.setLayoutY(300);
        backButton.setLayoutX(500);
        backButton.setLayoutY(370);

        root.getChildren().addAll(background, toggleMode, backButton);
        return new Scene(root, 1200, 675);
    }

    private Button createButton(String text, double width, double height, String style) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setStyle(style + " -fx-background-radius: 5; -fx-border-radius: 10; -fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        return button;
    }
}
