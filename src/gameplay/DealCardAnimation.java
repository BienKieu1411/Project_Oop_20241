package gameplay;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DealCardAnimation {
    private final int sceneWidth = 1200; // Chiều rộng Scene
    private final int sceneHeight = 675; // Chiều cao Scene
    private final int cardWidth = 84; // Kích thước bài ngang (7% Scene width)
    private final int cardHeight = 120; // Tỷ lệ bài là 1.4
    private final int gap = 30; // Khoảng cách giữa các lá bài

    public DealCardAnimation(AnchorPane gameRoot, int playerCount, int numberCards, Runnable onFinished) {
        dealCards(gameRoot, playerCount, numberCards, onFinished);
    }

    public void dealCards(AnchorPane gameRoot, int playerCount, int numberCards, Runnable onFinished) {
        SequentialTransition sequentialTransition = new SequentialTransition();

        for (int j = 0; j < numberCards; j++) {
            for (int i = 0; i < playerCount; i++) {
                double offsetX, offsetY;

                offsetY = switch (i) {
                    case 0 -> {
                        offsetX = (sceneWidth - (numberCards - 1) * gap - cardWidth) / 2.0;
                        yield sceneHeight - cardHeight - 20;
                    }
                    case 1 -> {
                        offsetX = sceneWidth - cardHeight - 20;
                        yield (sceneHeight - (numberCards - 1) * gap - cardWidth) / 2.0;
                    }
                    case 2 -> {
                        offsetX = (sceneWidth - (numberCards - 1) * gap - cardWidth) / 2.0;
                        yield 20;
                    }
                    case 3 -> {
                        offsetX = 20;
                        yield (sceneHeight - (numberCards - 1) * gap - cardWidth) / 2.0;
                    }
                    default -> throw new IllegalStateException("Unexpected player index: " + i);
                };
                ImageView cardBackView = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/BACK.png")));
                cardBackView.setFitWidth(cardWidth);
                cardBackView.setFitHeight(cardHeight);

                cardBackView.setId("CardImage");

                // Bắt đầu từ trung tâm bàn
                cardBackView.setLayoutX(sceneWidth / 2.0 - cardWidth / 2.0);
                cardBackView.setLayoutY(sceneHeight / 2.0 - cardHeight / 2.0);

                gameRoot.getChildren().add(cardBackView);

                // Hiệu ứng mờ dần (Fade In)
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(30), cardBackView); // Nhanh hơn
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);

                // Hiệu ứng di chuyển (Translate)
                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(50), cardBackView); // Nhanh hơn
                if (i == 1 || i == 3) { // Người chơi 2 và 4
                    translateTransition.setToX(offsetX + cardHeight / 2.0 - cardWidth / 2.0 - cardBackView.getLayoutX());
                    translateTransition.setToY(offsetY + j * gap - cardBackView.getLayoutY());
                    translateTransition.setOnFinished(event -> cardBackView.setRotate(90));
                } else { // Người chơi 1 và 3
                    translateTransition.setToX(offsetX + j * gap - cardBackView.getLayoutX());
                    translateTransition.setToY(offsetY - cardBackView.getLayoutY());
                }

                // Hiệu ứng phóng to
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), cardBackView); // Nhanh hơn
                scaleTransition.setFromX(0.7);
                scaleTransition.setFromY(0.7);
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);

                // Hiệu ứng nảy (Bounce)
                TranslateTransition bounceTransition = new TranslateTransition(Duration.millis(1), cardBackView); // Nhanh hơn
                bounceTransition.setByY(-5); // Nảy nhỏ hơn
                bounceTransition.setAutoReverse(true);
                bounceTransition.setCycleCount(2);

                // Gộp tất cả hiệu ứng
                ParallelTransition parallelTransition = new ParallelTransition(
                        fadeTransition,
                        translateTransition,
                        scaleTransition
                );

                SequentialTransition cardSequence = new SequentialTransition(parallelTransition, bounceTransition);
                sequentialTransition.getChildren().add(cardSequence);
            }
        }

        // Gọi callback khi animation hoàn thành
        sequentialTransition.setOnFinished(event -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });

        sequentialTransition.play();
    }
}
