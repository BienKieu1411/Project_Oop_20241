package gameplay;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Arrays;
import java.util.List;

public class MainMenu {

    // Tải trước hình nền để tránh tải lại nhiều lần
    public static final ImageView BACKGROUND_IMAGE = new ImageView(new Image(MainMenu.class.getResourceAsStream("/cardsimage/CardStart.jpg")));
    public Scene createMainMenu(Stage stage) {

        // AnchorPane chứa toàn bộ giao diện
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Hình nền
/*        ImageView background = BACKGROUND_IMAGE;
        background.setFitWidth(1200);
        background.setFitHeight(675);
        background.setPreserveRatio(false);*/

        // Tạo các nút
        Button playButton = createButton("PLAY", "linear-gradient(to bottom, #1D89F4, #1B62C5)", 200, 50);
        playButton.setOnMouseClicked(event -> stage.setScene(new GameSelectionMenu().createGameSelectionScene(stage)));

        Button settingButton = createButton("SETTING", "linear-gradient(to bottom, #F3B91D, #CF8A08)", 200, 50);
        settingButton.setOnMouseClicked(event -> stage.setScene(new SettingsMenu().createSettingsScene(stage)));

        Button quitButton = createButton("QUIT", "linear-gradient(to bottom, #F45A4A, #D93324)", 200, 50);
        quitButton.setOnMouseClicked(event -> stage.close());

        // Thêm hiệu ứng động cho các nút
        addHoverEffect(Arrays.asList(playButton, settingButton, quitButton));

        // Bố trí các nút theo chiều ngang
        HBox buttonContainer = new HBox(20);
        buttonContainer.getChildren().addAll(playButton, settingButton, quitButton);
        buttonContainer.setLayoutX(280);
        buttonContainer.setLayoutY(400);

        // Thêm các thành phần vào root
        root.getChildren().addAll(BACKGROUND_IMAGE, buttonContainer);

        // Tạo và trả về Scene
        return new Scene(root, 1200, 675);
    }

    // Hàm tạo nút với kiểu dáng tùy chỉnh
    public static Button createButton(String text, String backgroundStyle, int width, int hight) {
        Button button = new Button(text);
        button.setPrefSize(width, hight);
        button.setStyle("-fx-background-color: " + backgroundStyle + ";" +
                " -fx-background-radius: 5;" +
                " -fx-border-radius: 10;" +
                " -fx-font-size: 16;" +
                " -fx-font-weight: bold;" +
                " -fx-text-fill: white;");
        return button;
    }

    // Hàm thêm hiệu ứng động khi di chuột qua nút
    public static void addHoverEffect(List<Button> buttons) {
        for (Button button : buttons) {
            button.setOnMouseEntered(event -> { ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
                scaleTransition.setToX(1.1);
                scaleTransition.setToY(1.1);
                scaleTransition.play();
            });
            button.setOnMouseExited(event -> { ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
                scaleTransition.setToX(1.0);
                scaleTransition.setToY(1.0);
                scaleTransition.play(); });
        }

    }
}
