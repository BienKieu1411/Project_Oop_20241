package gameplay;

import deckofcards.Card;
import gamecardthirteens.PlayerThirteenS;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;

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

                // Đặt ID để phân biệt các lá bài
                cardBackView.setId("CardImage");

                // Bắt đầu từ trung tâm bàn
                cardBackView.setLayoutX(sceneWidth / 2.0 - cardWidth / 2.0);
                cardBackView.setLayoutY(sceneHeight / 2.0 - cardHeight / 2.0);

                gameRoot.getChildren().add(cardBackView);

                TranslateTransition transition = new TranslateTransition(Duration.millis(75), cardBackView); // Điều chỉnh thời gian để thay đổi tốc độ
                if (i == 1 || i == 3) { // Người chơi 2 và 4
                    transition.setToX(offsetX + cardHeight / 2.0 - cardWidth / 2.0 - cardBackView.getLayoutX()); // Điều chỉnh x sau khi xoay
                    transition.setToY(offsetY + j * gap - cardBackView.getLayoutY());
                    transition.setOnFinished(event -> cardBackView.setRotate(90)); // Xoay 90 độ khi đến tay người chơi
                } else { // Người chơi 1 và 3
                    transition.setToX(offsetX + j * gap - cardBackView.getLayoutX());
                    transition.setToY(offsetY - cardBackView.getLayoutY());
                }
                sequentialTransition.getChildren().add(transition);
            }
        }
        // Gọi callback khi animation hoàn thành
        sequentialTransition.setOnFinished(event -> {
            gameRoot.getChildren().removeIf(node -> node instanceof ImageView && "CardImage".equals(node.getId()));
            if (onFinished != null) {
                onFinished.run();
            }
        });

        sequentialTransition.play();
    }
}