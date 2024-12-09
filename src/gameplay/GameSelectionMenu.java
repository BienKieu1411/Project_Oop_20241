package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.Arrays;

public class GameSelectionMenu {
    public static Scene createGameSelectionScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Buttons
        Button baccaratButton = MainMenu.createButton("Baccarat", "linear-gradient(to bottom, #1D89F4, #1B62C5)", 200, 50);
        baccaratButton.setOnMouseClicked(event -> stage.setScene(new ModeSelectionMenu().createModeSelectionScene(stage, "Baccarat")));

        Button thirteenSButton = MainMenu.createButton("ThirteenS",  "linear-gradient(to bottom, #F3B91D, #CF8A08)", 200, 50);
        thirteenSButton.setOnMouseClicked(event -> stage.setScene(new ModeSelectionMenu().createModeSelectionScene(stage, "ThirteenS")));

        Button backButton = MainMenu.createButton("Back", "linear-gradient(to bottom, #F45A4A, #D93324)", 200, 50);
        backButton.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));

        // Set vị trí cho các nút
        baccaratButton.setLayoutX(500);
        baccaratButton.setLayoutY(300);
        thirteenSButton.setLayoutX(500);
        thirteenSButton.setLayoutY(370);
        backButton.setLayoutX(500);
        backButton.setLayoutY(440);

        // Thêm hiệu ứng cho nút
        MainMenu.addHoverEffect(Arrays.asList(baccaratButton,thirteenSButton,backButton));

        root.getChildren().addAll(MainMenu.BACKGROUND_IMAGE, baccaratButton, thirteenSButton, backButton);
        return new Scene(root, 1200, 675);
    }
}
