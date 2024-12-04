package gameplay;

import gamecardbaccarat.Baccarat;
import gamecardthirteens.ThirteenS;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerCountMenu {

    public Scene createPlayerCountScene(Stage stage, String game, boolean withPlayer) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Background
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/CardStart.jpg")));
        background.setFitWidth(1200);
        background.setFitHeight(675);
        background.setPreserveRatio(true);

        // Buttons
        Button twoPlayersButton = createButton("2 Players", 200, 50, "-fx-background-color: linear-gradient(to bottom, #1D89F4, #1B62C5);");
        twoPlayersButton.setOnMouseClicked(event -> {
            MainMenu.baccarat = new BaccaratDataManager(2, 1000); // Khởi tạo Baccarat với 2 người chơi
            startBaccaratGameplay(stage);
        });

        Button threePlayersButton = createButton("3 Players", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F3B91D, #CF8A08);");
        threePlayersButton.setOnMouseClicked(event -> {
            MainMenu.baccarat = new BaccaratDataManager(3, 1000); // Khởi tạo Baccarat với 3 người chơi
            startBaccaratGameplay(stage);
        });

        Button fourPlayersButton = createButton("4 Players", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F45A4A, #D93324);");
        fourPlayersButton.setOnMouseClicked(event -> {
            MainMenu.baccarat = new BaccaratDataManager(4, 1000); // Khởi tạo Baccarat với 4 người chơi
            startBaccaratGameplay(stage);
        });

        Button backButton = createButton("Back", 200, 50, "-fx-background-color: linear-gradient(to bottom, #F45A4A, #D93324);");
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
        return button;
    }

    private void startBaccaratGameplay(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameplay/BaccaratGameplay.fxml"));
            Parent root = loader.load();

            BaccaratGameplay baccaratGameplayController = loader.getController();
            baccaratGameplayController.initializeGame(MainMenu.baccarat); // Truyền thông tin game

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
