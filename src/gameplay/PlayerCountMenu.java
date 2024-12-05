package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PlayerCountMenu {
    public Scene createPlayerCountScene(Stage stage, String game, boolean withPlayer) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Background
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/CardStart.jpg")));
        background.setFitWidth(1200);
        background.setFitHeight(675);

        // Title
        Button titleLabel = new Button("Select Number of Players");
        titleLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        titleLabel.setDisable(true);

        // Buttons for player count
        Button twoPlayersButton = createButton("2 Players", 200, 50, "-fx-background-color: linear-gradient(to bottom, #1D89F4, #1B62C5);");
        twoPlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 2, withPlayer)));

        Button threePlayersButton = createButton("3 Players", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F3B91D, #CF8A08);");
        threePlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 3, withPlayer)));

        Button fourPlayersButton = createButton("4 Players", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F45A4A, #D93324);");
        fourPlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 4, withPlayer)));

        // Back button
        Button backButton = createButton("Back", 200, 50, "-fx-background-color: linear-gradient(to bottom, #757575, #424242);");
        backButton.setOnMouseClicked(event -> stage.setScene(new ModeSelectionMenu().createModeSelectionScene(stage, game)));

        // Layout
        twoPlayersButton.setLayoutX(500);
        twoPlayersButton.setLayoutY(250);
        threePlayersButton.setLayoutX(500);
        threePlayersButton.setLayoutY(320);
        fourPlayersButton.setLayoutX(500);
        fourPlayersButton.setLayoutY(390);
        backButton.setLayoutX(500);
        backButton.setLayoutY(460);

        root.getChildren().addAll(background, twoPlayersButton, threePlayersButton, fourPlayersButton, backButton);
        return new Scene(root, 1200, 675);
    }

    private Button createButton(String text, double width, double height, String style) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setStyle(style + " -fx-background-radius: 5; -fx-border-radius: 10; -fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(style + " -fx-background-radius: 5; -fx-border-radius: 10; -fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #e0e0e0;"));
        button.setOnMouseExited(e -> button.setStyle(style + " -fx-background-radius: 5; -fx-border-radius: 10; -fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;"));
        return button;
    }
}
