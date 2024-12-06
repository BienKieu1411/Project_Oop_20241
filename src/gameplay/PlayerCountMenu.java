package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;

public class PlayerCountMenu {
    public Scene createPlayerCountScene(Stage stage, String game, boolean withPlayer) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Title
        Button titleLabel = new Button("Select Number of Players");
        titleLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        titleLabel.setDisable(true);

        // Buttons for player count
        Button twoPlayersButton = MainMenu.createButton("2 Players",  "linear-gradient(to bottom, #1D89F4, #1B62C5)", 200, 50);
        twoPlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 2, withPlayer)));

        Button threePlayersButton = MainMenu.createButton("3 Players",  "linear-gradient(to bottom, #F3B91D, #CF8A08)", 200, 50);
        threePlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 3, withPlayer)));

        Button fourPlayersButton = MainMenu.createButton("4 Players",  "linear-gradient(to bottom, #16B300, #14A300)", 200, 50);
        fourPlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 4, withPlayer)));

        Button backButton = MainMenu.createButton("Back",  "linear-gradient(to bottom, #F45A4A, #D93324)", 200, 50);
        backButton.setOnMouseClicked(event -> stage.setScene(new ModeSelectionMenu().createModeSelectionScene(stage, game)));

        MainMenu.addHoverEffect(Arrays.asList(twoPlayersButton, threePlayersButton, fourPlayersButton, backButton));

        // Layout
        twoPlayersButton.setLayoutX(500);
        twoPlayersButton.setLayoutY(250);
        threePlayersButton.setLayoutX(500);
        threePlayersButton.setLayoutY(320);
        fourPlayersButton.setLayoutX(500);
        fourPlayersButton.setLayoutY(390);
        backButton.setLayoutX(500);
        backButton.setLayoutY(460);

        root.getChildren().addAll(MainMenu.BACKGROUND_IMAGE, twoPlayersButton, threePlayersButton, fourPlayersButton, backButton);
        return new Scene(root, 1200, 675);
    }
}
