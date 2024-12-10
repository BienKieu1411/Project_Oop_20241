package gameplay;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Winner {
    public Winner(Stage stage, AnchorPane gameRoot, String playerWin) {
        endGame(stage, playerWin);
    }

    private void endGame(Stage stage, String playerWin) {
        // Tạo ảnh nền
        ImageView background = new ImageView(new Image(MainMenu.class.getResourceAsStream("/cardsimage/winnerbackground1.jpg")));
        background.setFitWidth(1200);
        background.setFitHeight(675);

        // Tạo layout chính
        AnchorPane overlay = new AnchorPane();
        overlay.setPrefSize(1200, 675);
        overlay.getChildren().add(background);

        // Tạo Text để hiển thị tên người chiến thắng với viền đen
        Text winnerText = new Text(" Winner: " + playerWin );
        winnerText.setFill(Color.GOLD);
        winnerText.setFont(new Font("Arial", 64));
        winnerText.setEffect(new Glow(1));
        winnerText.setStroke(Color.BLACK);  // Thêm viền đen
        winnerText.setStrokeWidth(1); // Điều chỉnh độ rộng của viền
        overlay.getChildren().add(winnerText);

        // Đặt tên người chiến thắng ra giữa màn hình sau khi giao diện sẵn sàng
        Platform.runLater(() -> {
            double centerX = (overlay.getWidth() - winnerText.getBoundsInLocal().getWidth()) / 2;
            double centerY = (overlay.getHeight() - winnerText.getBoundsInLocal().getHeight()) / 2;
            winnerText.setLayoutX(centerX);
            winnerText.setLayoutY(centerY + 64);
        });

        // Hiệu ứng nhấp nháy chữ
        FadeTransition textFlash = new FadeTransition(Duration.seconds(1), winnerText);
        textFlash.setFromValue(1.0);
        textFlash.setToValue(0.5);
        textFlash.setCycleCount(Animation.INDEFINITE);
        textFlash.setAutoReverse(true);
        textFlash.play();

        // Hiệu ứng pháo hoa
        Timeline fireworkEffect = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            Circle firework = new Circle(5, Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
            firework.setLayoutX(new Random().nextDouble() * overlay.getWidth());
            firework.setLayoutY(new Random().nextDouble() * overlay.getHeight());
            firework.setEffect(new DropShadow(30, Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256))));

            overlay.getChildren().add(firework);

            ScaleTransition expand = new ScaleTransition(Duration.seconds(1), firework);
            expand.setFromX(1);
            expand.setFromY(1);
            expand.setToX(5);
            expand.setToY(5);
            expand.setCycleCount(2);
            expand.setAutoReverse(true);
            expand.setOnFinished(e -> overlay.getChildren().remove(firework));
            expand.play();
        }));
        fireworkEffect.setCycleCount(Timeline.INDEFINITE);
        fireworkEffect.play();

        // Hiệu ứng duy băng rơi
        Timeline confettiRain = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
            ImageView confetti = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/ribbon`.png")));
            confetti.setFitWidth(27);
            confetti.setFitHeight(48);
            confetti.setLayoutX(new Random().nextDouble() * overlay.getWidth());
            confetti.setLayoutY(-20);
            overlay.getChildren().add(confetti);

            TranslateTransition fall = new TranslateTransition(Duration.seconds(5), confetti);
            fall.setToY(overlay.getHeight() + 20);
            fall.setOnFinished(e -> overlay.getChildren().remove(confetti));
            fall.play();

        }));
        confettiRain.setCycleCount(Timeline.INDEFINITE);
        confettiRain.play();

        Timeline confettiRain1 = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            ImageView confetti1 = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/ribbon.png")));
            confetti1.setFitWidth(27);
            confetti1.setFitHeight(48);
            confetti1.setLayoutX(new Random().nextDouble() * overlay.getWidth());
            confetti1.setLayoutY(-20);
            overlay.getChildren().add(confetti1);

            TranslateTransition fall = new TranslateTransition(Duration.seconds(3), confetti1);
            fall.setToY(overlay.getHeight() + 20);
            fall.setOnFinished(e -> overlay.getChildren().remove(confetti1));
            fall.play();

        }));
        confettiRain.setCycleCount(Timeline.INDEFINITE);
        confettiRain.play();

        // Tạo nút "Replay" và "Quit"
        Button replayButton = MainMenu.createButton("Replay", 100, 50);
        replayButton.setOnAction(event -> {
            // Hủy scene hiện tại trước khi chuyển sang scene mới
            stage.setScene(ModeSelectionMenu.createModeSelectionScene(stage, "Baccarat"));
            // Sau khi chuyển scene, giải phóng bộ nhớ (nếu cần)
            cleanupAndExit(replayButton);
        });

        Button quitButton = MainMenu.createButton("Quit", 100, 50);
        quitButton.setOnAction(event -> {
            // Hủy scene hiện tại trước khi chuyển sang scene mới
            stage.setScene(GameSelectionMenu.createGameSelectionScene(stage));
            // Sau khi chuyển scene, giải phóng bộ nhớ (nếu cần)
            cleanupAndExit(quitButton);
        });

        // Căn chỉnh các nút đối xứng
        replayButton.setLayoutX(400);
        replayButton.setLayoutY(550);

        quitButton.setLayoutX(650);
        quitButton.setLayoutY(550);

        overlay.getChildren().addAll(replayButton, quitButton);

        // Hiệu ứng xuất hiện giao diện
        overlay.setOpacity(0);
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(1), overlay);
        scaleIn.setFromX(0.5);
        scaleIn.setFromY(0.5);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), overlay);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ParallelTransition showTransition = new ParallelTransition(scaleIn, fadeIn);
        showTransition.play();

        // Gán scene mới
        Scene scene = new Scene(overlay);
        stage.setScene(scene);
        stage.show();
    }

    // Hủy sự kiện và giải phóng bộ nhớ
    private void cleanupAndExit(Button button) {
        button.setOnAction(null);  // Hủy sự kiện để tránh giữ tham chiếu không cần thiết
    }
}
