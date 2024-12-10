package gameplay;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class WinnerTop {
    private static final Image backgroundImage = new Image(WinnerTop.class.getResourceAsStream("/cardsimage/winnerbackground2.jpg"));

    public WinnerTop(Stage stage, AnchorPane gameRoot, ArrayList<String> topWinnersThirteenS) {
        endGame(stage, gameRoot, topWinnersThirteenS);
    }

    private void endGame(Stage stage, AnchorPane gameRoot, ArrayList<String> topWinnersThirteenS) {
        // Tạo ảnh nền
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1200);
        background.setFitHeight(675);

        // Tạo layout chính
        AnchorPane overlay = new AnchorPane();
        overlay.setPrefSize(1200, 675);
        overlay.getChildren().add(background);

        for (int i = 0; i < 3 ; i++) {
            String nameOfPlayer = topWinnersThirteenS.get(i);
            Label winnerLabel = new Label((i + 1) + ". " + nameOfPlayer);
            winnerLabel.setTextFill(Color.GOLD);
            winnerLabel.setFont(new Font("Arial", 36));
            winnerLabel.setAlignment(Pos.CENTER);
            winnerLabel.setEffect(new Glow(1));
            winnerLabel.setLayoutX(397 + 85 * (i + 1));
            winnerLabel.setLayoutY(79 + 169 * i);
            overlay.getChildren().add(winnerLabel);
        }

        ImageView topImage = new ImageView(new Image(Objects.requireNonNull(WinnerTop.class.getResourceAsStream("/cardsimage/topimage.png"))));
        topImage.setFitWidth(600);
        topImage.setFitHeight(600);
        topImage.setLayoutX(165);
        topImage.setLayoutY(0);
        overlay.getChildren().add(topImage);

        // Tạo nút "Replay" và "Quit"
        Button replayButton = MainMenu.createButton("Replay",100,50);
        AnchorPane.setBottomAnchor(replayButton, 50.0);
        AnchorPane.setLeftAnchor(replayButton, 400.0);
        replayButton.setOnAction(event -> stage.setScene(ModeSelectionMenu.createModeSelectionScene(stage, "ThirteenS")));

        Button quitButton = MainMenu.createButton("Quit", 100,50);
        AnchorPane.setBottomAnchor(quitButton, 50.0);
        AnchorPane.setLeftAnchor(quitButton, 650.0);
        quitButton.setOnAction(event -> stage.setScene(GameSelectionMenu.createGameSelectionScene(stage)));

        overlay.getChildren().addAll(replayButton, quitButton);

        // Hiệu ứng vàng rơi
        Timeline goldRainTimeline = new Timeline();
        goldRainTimeline.setCycleCount(Timeline.INDEFINITE);
        goldRainTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.2), event -> {
                    ImageView goldPiece = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/gold.png")));
                    goldPiece.setFitWidth(30);
                    goldPiece.setFitHeight(30);
                    goldPiece.setLayoutX(new Random().nextDouble() * overlay.getWidth());
                    goldPiece.setLayoutY(-30);

                    overlay.getChildren().add(goldPiece);

                    TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1.5), goldPiece);
                    fallTransition.setToY(overlay.getHeight() + 30);
                    fallTransition.setOnFinished(e -> overlay.getChildren().remove(goldPiece));
                    fallTransition.play();
                })
        );

        // Hiệu ứng bóng bay bay lên
        Timeline balloonTimeline = new Timeline();
        balloonTimeline.setCycleCount(Timeline.INDEFINITE);
        balloonTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.5), event -> {
                    Circle balloon = new Circle(15, Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                    balloon.setLayoutX(new Random().nextDouble() * overlay.getWidth());
                    balloon.setLayoutY(overlay.getHeight() + 15);

                    overlay.getChildren().add(balloon);

                    TranslateTransition balloonTransition = new TranslateTransition(Duration.seconds(4), balloon);
                    balloonTransition.setToY(-30);
                    balloonTransition.setOnFinished(e -> overlay.getChildren().remove(balloon));
                    balloonTransition.play();
                })
        );

        // Hiệu ứng pháo hoa
        Timeline fireworkTimeline = new Timeline();
        fireworkTimeline.setCycleCount(Timeline.INDEFINITE);
        fireworkTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(2), event -> {
                    Circle firework = new Circle(5, Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                    firework.setLayoutX(new Random().nextDouble() * overlay.getWidth());
                    firework.setLayoutY(new Random().nextDouble() * overlay.getHeight());

                    overlay.getChildren().add(firework);

                    ScaleTransition fireworkScaleTransition = new ScaleTransition(Duration.seconds(1), firework);
                    fireworkScaleTransition.setFromX(1);
                    fireworkScaleTransition.setFromY(1);
                    fireworkScaleTransition.setToX(5);
                    fireworkScaleTransition.setToY(5);
                    fireworkScaleTransition.setCycleCount(2);
                    fireworkScaleTransition.setAutoReverse(true);

                    fireworkScaleTransition.setOnFinished(e -> overlay.getChildren().remove(firework));
                    fireworkScaleTransition.play();
                })
        );

        // Bắt đầu hiệu ứng vàng, bóng và pháo hoa
        goldRainTimeline.play();
        balloonTimeline.play();
        fireworkTimeline.play();

        // Hiệu ứng xuất hiện hoành tráng cho scene
        overlay.setOpacity(0);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), overlay);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), overlay);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);
        parallelTransition.play();

        // Gán scene mới
        Scene scene = new Scene(overlay);
        stage.setScene(scene);
        stage.show();
    }
}
