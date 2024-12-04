package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameController {
    public static void startGame(Stage stage, String game, int playerCount, boolean withPlayer) {
        System.out.println("Starting game: " + game);
        System.out.println("Mode: " + (withPlayer ? "With Player" : "With Bot"));
        System.out.println("Player Count: " + playerCount);

        AnchorPane gameRoot = new AnchorPane();
        gameRoot.setPrefSize(1200, 675);

        // Placeholder for game interface
            Button backToMainMenu = new Button("Quit");
        backToMainMenu.setPrefSize(45, 15);
        backToMainMenu.setStyle("-fx-background-color: linear-gradient(to bottom, #F45A4A, #D93324); -fx-background-radius: 5; "
                + "-fx-border-radius: 10; -fx-font-size: 8; -fx-font-weight: bold; -fx-text-fill: white;");
        backToMainMenu.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));

        AnchorPane.setTopAnchor(backToMainMenu, 0.0);
        AnchorPane.setRightAnchor(backToMainMenu, 0.0);

        gameRoot.getChildren().add(backToMainMenu);
        stage.setScene(new Scene(gameRoot, 1200, 675));
    }
}
