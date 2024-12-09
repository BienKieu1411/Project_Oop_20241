package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

public class PlayerCountMenu {
    public Scene createPlayerCountScene(Stage stage, String game, boolean withPlayer) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        Text text = MainMenu.createText("Select Number of Players");
        // Đợi cho đến khi root được hiển thị để lấy kích thước thực của text
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            text.setLayoutX((newVal.doubleValue() - text.getLayoutBounds().getWidth()) / 2);
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            text.setLayoutY((newVal.doubleValue() - text.getLayoutBounds().getHeight()) / 2 - 100);
        });
        // Buttons for player count
        Button twoPlayersButton = MainMenu.createButton("2 Players",   200, 50);
        twoPlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 2, withPlayer)));

        Button threePlayersButton = MainMenu.createButton("3 Players",   200, 50);
        threePlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 3, withPlayer)));

        Button fourPlayersButton = MainMenu.createButton("4 Players",   200, 50);
        fourPlayersButton.setOnMouseClicked(event -> stage.setScene(new PlayerNameInputMenu().createPlayerNameInputScene(stage, game, 4, withPlayer)));

        Button backButton = MainMenu.createButton("Back",   200, 50);
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

        root.getChildren().addAll(MainMenu.BACKGROUND_IMAGE, twoPlayersButton, threePlayersButton, fourPlayersButton, backButton, text);
        return new Scene(root, 1200, 675);

    }
}
