package gamecardbaccarat;

import gameplay.GameSelectionMenu;
import gameplay.MainMenu;
import gameplay.ModeSelectionMenu;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class WinnerDisplay {
    public WinnerDisplay(Stage stage, AnchorPane gameRoot, PlayerBaccarat playerWin) {
        endGame(stage, gameRoot, playerWin);
    }

    private void endGame(Stage stage, AnchorPane gameRoot, PlayerBaccarat playerWin) {
        // Tạo nền mờ
        AnchorPane overlay = new AnchorPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlay.setPrefSize(gameRoot.getWidth(), gameRoot.getHeight());

        // Thêm nhãn "Winner"
        Label winnerLabel = new Label("Winner");
        winnerLabel.setTextFill(Color.GOLD);
        winnerLabel.setFont(new Font("Arial", 36));
        winnerLabel.setLayoutX(stage.getWidth() / 2 - (winnerLabel.getWidth()  / 2 + 60));
        winnerLabel.setLayoutY(stage.getHeight() / 2 - (winnerLabel.getHeight() / 2 + 100));
        winnerLabel.setAlignment(Pos.CENTER);


        // Thêm nhãn tên người chiến thắng
        Label playerLabel = new Label(playerWin.getNameOfPlayer());
        playerLabel.setTextFill(Color.GOLD);
        playerLabel.setFont(new Font("Arial", 36));
        playerLabel.setLayoutX(stage.getWidth() / 2 - (playerLabel.getWidth()  / 2 + 40));
        playerLabel.setLayoutY(stage.getHeight() / 2 - (playerLabel.getHeight() / 2 + 40));
        gameRoot.getChildren().add(overlay);

        // Thêm nút "Replay" màu xanh dương
        Button replayButton = MainMenu.createButton("Replay", "linear-gradient(to bottom, #4A90E2, #357ABD)", 100, 25);
        replayButton.setLayoutX((gameRoot.getWidth() / 2) - 150);
        replayButton.setLayoutY((gameRoot.getHeight() / 2) + 100);
        replayButton.setOnMouseClicked(event -> {
            gameRoot.getChildren().clear();
            stage.setScene(ModeSelectionMenu.createModeSelectionScene(stage, "Baccarat"));
        });

        // Thêm nút "Quit"
        Button quitButton = MainMenu.createButton("Quit", "linear-gradient(to bottom, #F45A4A, #D93324)", 100, 25);
        quitButton.setLayoutX((gameRoot.getWidth() / 2) + 50);
        quitButton.setLayoutY((gameRoot.getHeight() / 2) + 100);
        quitButton.setOnMouseClicked(event -> {
            stage.setScene(GameSelectionMenu.createGameSelectionScene(stage));
        });

        overlay.getChildren().addAll(replayButton, quitButton);

        // Thêm vương miện
        ImageView crownImageView = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/crown.png")));
        crownImageView.setFitWidth(300);
        crownImageView.setFitHeight(300);
        crownImageView.setLayoutX((gameRoot.getWidth() / 2) - 160);
        crownImageView.setLayoutY((gameRoot.getHeight() / 2) - 400);

        ImageView eggImageView = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/egg.png")));
        eggImageView.setFitWidth(400);
        eggImageView.setFitHeight(400);
        eggImageView.setLayoutX((gameRoot.getWidth() / 2) - 199);
        eggImageView.setLayoutY((gameRoot.getHeight() / 2) - 275);

        overlay.getChildren().addAll(eggImageView, crownImageView, winnerLabel, playerLabel);

        // Tạo hiệu ứng xuất hiện cho vương miện
        ScaleTransition crownScaleTransition = new ScaleTransition(Duration.millis(1000), crownImageView);
        crownScaleTransition.setFromX(0);
        crownScaleTransition.setFromY(0);
        crownScaleTransition.setToX(1);
        crownScaleTransition.setToY(1);

        // Tạo hiệu ứng lấp lánh cho vương miện
        Glow crownGlow = new Glow(0.3);
        crownImageView.setEffect(crownGlow);
        Timeline glowTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> crownGlow.setLevel(0.3)),
                new KeyFrame(Duration.seconds(1), e -> crownGlow.setLevel(0.9))
        );
        glowTimeline.setCycleCount(Timeline.INDEFINITE);
        glowTimeline.setAutoReverse(true);

        // Tạo hiệu ứng vàng rơi như mưa
        Timeline goldRainTimeline = new Timeline();
        goldRainTimeline.setCycleCount(Timeline.INDEFINITE);
        goldRainTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.1), event -> {
                    ImageView goldPiece = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/gold.png")));
                    goldPiece.setFitWidth(30);
                    goldPiece.setFitHeight(30);
                    goldPiece.setLayoutX(new Random().nextDouble() * gameRoot.getWidth());
                    goldPiece.setLayoutY(-30); // bắt đầu từ phía trên màn hình

                    overlay.getChildren().add(goldPiece);

                    TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), goldPiece);
                    fallTransition.setToY(gameRoot.getHeight() + 30); // rơi xuống dưới màn hình

                    fallTransition.setOnFinished(e -> overlay.getChildren().remove(goldPiece));
                    fallTransition.play();
                })
        );

        // Kết hợp các hiệu ứng với nhau
        SequentialTransition finalTransitions = new SequentialTransition(crownScaleTransition);
        finalTransitions.setOnFinished(e -> {
            glowTimeline.play();
            goldRainTimeline.play();
        });
        finalTransitions.play();
    }
}
