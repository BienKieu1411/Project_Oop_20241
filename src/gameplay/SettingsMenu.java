package gameplay;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;

public class SettingsMenu {
    // Sử dụng Singleton Pattern
    private static SettingsMenu instance = null;

    private boolean isImageMode = true;

    private SettingsMenu() {} // Đảm bảo không thể tạo đối tượng trực tiếp

    public static SettingsMenu getInstance() {
        if (instance == null) {
            instance = new SettingsMenu();
        }
        return instance;
    }

    public boolean isImageMode() {
        return isImageMode;
    }

    public void setImageMode(boolean isImageMode) {
        this.isImageMode = isImageMode;
    }

    public Scene createSettingsScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Background
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/CardStart.jpg")));
        background.setFitWidth(1200);
        background.setFitHeight(675);
        background.setPreserveRatio(true);

        // Toggle Mode Button
        Button toggleMode = MainMenu.createButton(isImageMode ? "Image Mode" : "Text Mode", 200, 50);
        toggleMode.setOnMouseClicked(event -> {
            setImageMode(!isImageMode);
            toggleMode.setText(isImageMode ? "Image Mode" : "Text Mode");
        });

        // Random Volume Button
        Button randomVolumeButton = MainMenu.createButton("Random Volume", 200, 50);
        randomVolumeButton.setOnMouseClicked(event -> {
            double randomVolume = new Random().nextDouble(); // Tạo âm lượng ngẫu nhiên từ 0.0 đến 1.0
            MainApplication.mediaPlayer.setVolume(randomVolume); // Đặt âm lượng cho MediaPlayer

        });

        // Back Button
        Button backButton = MainMenu.createButton("Back", 200, 50);
        backButton.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));

        MainMenu.addHoverEffect(Arrays.asList(toggleMode, randomVolumeButton, backButton));
        toggleMode.setLayoutX(500);
        toggleMode.setLayoutY(300);
        randomVolumeButton.setLayoutX(500);
        randomVolumeButton.setLayoutY(370);
        backButton.setLayoutX(500);
        backButton.setLayoutY(440);

        root.getChildren().addAll(background, toggleMode, randomVolumeButton, backButton);
        return new Scene(root, 1200, 675);
    }

    private Button createButton(String text, double width, double height, String style) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setStyle(style + " -fx-background-radius: 5; -fx-border-radius: 10; -fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        return button;
    }
}
