package gameplay;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ShuffleEffect {

    private static final String CARD_BACK_IMAGE_PATH = "/cardsimage/BACK.png";
    private static final int CARD_WIDTH = 84;
    private static final int CARD_HEIGHT = 120;

    private final List<ImageView> cardViews = new ArrayList<>();
    private final Pane root;
    private final double centerX;
    private final double centerY;

    public ShuffleEffect(Pane root, int cardCount) {
        this.root = root;
        this.centerX = root.getWidth() / 2;
        this.centerY = root.getHeight() / 2;

        // Tạo các lá bài
        Image cardBackImage = new Image(getClass().getResourceAsStream(CARD_BACK_IMAGE_PATH));
        for (int i = 0; i < cardCount; i++) {
            ImageView cardView = new ImageView(cardBackImage);
            cardView.setFitWidth(CARD_WIDTH);
            cardView.setFitHeight(CARD_HEIGHT);
            cardView.setLayoutX(centerX - (CARD_WIDTH / 2));
            cardView.setLayoutY(centerY - (CARD_HEIGHT / 2));
            cardViews.add(cardView);
            root.getChildren().add(cardView);
        }
    }

    public void startShuffle(Runnable onFinished) {
        SequentialTransition shuffleSequence = new SequentialTransition();

        // Bước 1: Dàn bài ra hàng ngang
        ParallelTransition spreadEffect = new ParallelTransition();
        for (int i = 0; i < cardViews.size(); i++) {
            ImageView card = cardViews.get(i);
            TranslateTransition spread = new TranslateTransition(Duration.seconds(0.8), card);
            spread.setToX((i - cardViews.size() / 2.0) * (CARD_WIDTH + 10));
            spread.setToY(Math.sin(i * Math.PI / 6) * 50); // Dàn ngang với hiệu ứng sóng
            spreadEffect.getChildren().add(spread);
        }
        shuffleSequence.getChildren().add(spreadEffect);

        // Bước 2: Gộp bài lại mà không di chuyển lá bài ở giữa
        ParallelTransition gatherEffect = new ParallelTransition();
        for (int i = 0; i < cardViews.size(); i++) {
            ImageView card = cardViews.get(i);
            TranslateTransition gather = new TranslateTransition(Duration.seconds(0.8), card);
            gather.setToX(0);
            gather.setToY(0);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.8), card);
            rotate.setByAngle(360); // Xoay khi gộp lại

            gatherEffect.getChildren().addAll(gather, rotate);
        }
        shuffleSequence.getChildren().add(gatherEffect);

        // Bước 3: Đảm bảo hiệu ứng chạy tuần tự và không bị dừng giữa chừng
        shuffleSequence.setOnFinished(event -> {
            // Sau khi hoàn thành hiệu ứng gộp bài, xóa tất cả các lá bài
            root.getChildren().removeAll(cardViews);
            // Đảm bảo rằng callback chỉ được gọi khi hoàn tất
            if (onFinished != null) {
                onFinished.run(); // Callback khi hiệu ứng hoàn tất
            }
        });

        // Bắt đầu hiệu ứng
        shuffleSequence.play();
    }
}
