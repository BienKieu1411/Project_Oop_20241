package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;

public class ModeSelectionMenu {
    public Scene createModeSelectionScene(Stage stage, String game) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

/*        // Background
        ImageView background = MainMenu.BACKGROUND_IMAGE;
        background.setPreserveRatio(true);*/

        // Buttons
        Button withPlayerButton = MainMenu.createButton("Play with Player", "linear-gradient(to bottom, #1D89F4, #1B62C5)", 200, 50);
        withPlayerButton.setOnMouseClicked(event -> stage.setScene(new PlayerCountMenu().createPlayerCountScene(stage, game, true)));

        Button withBotButton = MainMenu.createButton("Play with Bot", "linear-gradient(to bottom, #F3B91D, #CF8A08)", 200, 50);
        withBotButton.setOnMouseClicked(event -> stage.setScene(new PlayerCountMenu().createPlayerCountScene(stage, game, false)));

        Button backButton = MainMenu.createButton("Back", "linear-gradient(to bottom, #F45A4A, #D93324)", 200, 50);
        backButton.setOnMouseClicked(event -> stage.setScene(new GameSelectionMenu().createGameSelectionScene(stage)));

        // Thêm hiệu ứng cho nút
        MainMenu.addHoverEffect(Arrays.asList(withPlayerButton, withBotButton,backButton));

        // Set vị trí cho các nút
        withPlayerButton.setLayoutX(500);
        withPlayerButton.setLayoutY(300);
        withBotButton.setLayoutX(500);
        withBotButton.setLayoutY(370);
        backButton.setLayoutX(500);
        backButton.setLayoutY(440);

        root.getChildren().addAll(MainMenu.BACKGROUND_IMAGE, withPlayerButton, withBotButton, backButton);
        return new Scene(root, 1200, 675);
    }

}
