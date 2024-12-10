package gameplay;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;


import java.util.Arrays;
import java.util.List;

public class MainMenu {
    // Tải trước hình nền để tránh tải lại nhiều lần
    public static final ImageView BACKGROUND_IMAGE = new ImageView(new Image(MainMenu.class.getResourceAsStream("/cardsimage/CardStart.jpg")));
    public static final ImageView TABLE = new ImageView(new Image(MainMenu.class.getResourceAsStream("/cardsimage/tableimage.jpg")));

    public Scene createMainMenu(Stage stage) {
        // AnchorPane chứa toàn bộ giao diện
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Tạo văn bản
        Text title = createText("♠♥Card Game♦♣");

        // Đợi cho đến khi root được hiển thị để lấy kích thước thực của title
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            title.setLayoutX((newVal.doubleValue() - title.getLayoutBounds().getWidth()) / 2);
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            title.setLayoutY((newVal.doubleValue() - title.getLayoutBounds().getHeight()) / 2 - 100);
        });

        // Tạo các nút
        Button playButton = createButton("PLAY", 200, 50);
        playButton.setOnMouseClicked(event -> stage.setScene(new GameSelectionMenu().createGameSelectionScene(stage)));

        Button settingButton = createButton("SETTING",  200, 50);
        settingButton.setOnMouseClicked(event -> stage.setScene(SettingsMenu.getInstance().createSettingsScene(stage)));

        Button quitButton = createButton("QUIT",  200, 50);
        quitButton.setOnMouseClicked(event -> stage.close());

        // Thêm hiệu ứng động cho các nút
        addHoverEffect(Arrays.asList(playButton, settingButton, quitButton));

        // Bố trí các nút theo chiều ngang
        HBox buttonContainer = new HBox(20);
        buttonContainer.getChildren().addAll(playButton, settingButton, quitButton);
        buttonContainer.setLayoutX(280);
        buttonContainer.setLayoutY(400);

        // Thêm các thành phần vào root
        root.getChildren().addAll(BACKGROUND_IMAGE, buttonContainer, title);

        // Tạo và trả về Scene
        return new Scene(root, 1200, 675);
    }


    public static Button createButton(String text, int width, int height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);

        // Gradient nền vàng kim
        String goldenGradient = "-fx-background-color: linear-gradient(to bottom, #FFD700, #FFC400, #E6AC00);" +
                " -fx-background-radius: 15;" +
                " -fx-border-color: gold;" +
                " -fx-border-width: 3;" +
                " -fx-border-radius: 15;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;" +
                " -fx-font-size: 16;";

        button.setStyle(goldenGradient);

        // Hiệu ứng bóng bên ngoài (DropShadow)
        DropShadow outerShadow = new DropShadow();
        outerShadow.setColor(Color.GOLD);
        outerShadow.setRadius(15);
        outerShadow.setOffsetX(0);
        outerShadow.setOffsetY(0);
        outerShadow.setSpread(0.2); // Độ lan tỏa ánh sáng
        button.setEffect(outerShadow);

        // Hiệu ứng bóng bên trong (InnerShadow)
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.rgb(255, 215, 0, 0.8));
        innerShadow.setRadius(5);
        innerShadow.setChoke(0.4);

        // Thêm sự kiện tương tác chuột
        button.setOnMouseEntered(event -> button.setEffect(innerShadow));
        button.setOnMouseExited(event -> button.setEffect(outerShadow));

        // Hiệu ứng lấp lánh
        Timeline sparkleTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(outerShadow.radiusProperty(), 15)),
                new KeyFrame(Duration.millis(500), new KeyValue(outerShadow.radiusProperty(), 25)),
                new KeyFrame(Duration.millis(1000), new KeyValue(outerShadow.radiusProperty(), 15))
        );
        sparkleTimeline.setCycleCount(Timeline.INDEFINITE);
        sparkleTimeline.setAutoReverse(true); // Hiệu ứng dao động
        sparkleTimeline.play();

        // Thêm hiệu ứng ánh sáng lấp lánh di chuyển trên nút
        Rectangle sparkle = new Rectangle(width, height);
        sparkle.setFill(Color.rgb(255, 255, 255, 0.2));
        GaussianBlur sparkleBlur = new GaussianBlur(10);
        sparkle.setEffect(sparkleBlur);

        Timeline lightAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(sparkle.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(500), new KeyValue(sparkle.opacityProperty(), 0.5)),
                new KeyFrame(Duration.millis(1000), new KeyValue(sparkle.opacityProperty(), 0))
        );
        lightAnimation.setCycleCount(Timeline.INDEFINITE);
        lightAnimation.play();

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

    public static Text createText(String content) {
        // Tạo đối tượng Text
        Text text = new Text(content);
        text.setFont(Font.font("Arial", 50));
        text.setFill(Color.GOLD); // Màu vàng kim loại

        // Hiệu ứng viền đen (DropShadow đen)
        DropShadow blackOutline = new DropShadow();
        blackOutline.setColor(Color.BLACK); // Viền đen
        blackOutline.setRadius(5); // Độ dày viền
        blackOutline.setSpread(0.5); // Mức độ phủ viền
        blackOutline.setOffsetX(0); // Không dịch chuyển ngang
        blackOutline.setOffsetY(0); // Không dịch chuyển dọc

        // Hiệu ứng bóng vàng nhẹ (DropShadow vàng)
        DropShadow goldenGlow = new DropShadow();
        goldenGlow.setColor(Color.YELLOW); // Màu vàng nhẹ
        goldenGlow.setRadius(10); // Giảm độ chói
        goldenGlow.setSpread(0.3); // Giảm lan tỏa ánh sáng
        goldenGlow.setInput(blackOutline); // Thêm viền đen vào hiệu ứng

        text.setEffect(goldenGlow); // Gán hiệu ứng vào chữ

        // Tạo hiệu ứng nhấp nháy (thay đổi độ sáng)
        Timeline blinkEffect = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(text.opacityProperty(), 1)),  // Sáng hoàn toàn
                new KeyFrame(Duration.millis(500), new KeyValue(text.opacityProperty(), 0.8)), // Mờ nhẹ
                new KeyFrame(Duration.millis(1000), new KeyValue(text.opacityProperty(), 1)) // Quay lại sáng
        );
        blinkEffect.setCycleCount(Timeline.INDEFINITE); // Lặp lại vô hạn
        blinkEffect.play();

        return text;
    }

}
