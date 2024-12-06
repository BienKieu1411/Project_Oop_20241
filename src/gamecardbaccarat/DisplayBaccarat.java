package gamecardbaccarat;

import deckofcards.Card;
import gamecardthirteens.PlayerThirteenS;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class DisplayBaccarat {
    private final int sceneWidth = 1200; // Chiều rộng Scene
    private final int sceneHeight = 675; // Chiều cao Scene
    private final int cardWidth = 84; // Kích thước bài ngang (7% Scene width)
    private final int cardHeight = 120; // Tỷ lệ bài là 1.4
    private final int gap = 30; // Khoảng cách giữa các lá bài

    public DisplayBaccarat(AnchorPane gameRoot, ArrayList<PlayerBaccarat> playerBaccarat, int playerCount) {
        dealCards(gameRoot, playerBaccarat);
    }

    public void dealCards(AnchorPane gameRoot, ArrayList<PlayerBaccarat> players) {
        // Chỉ xóa các lá bài cũ, giữ nguyên nền và nút
        gameRoot.getChildren().removeIf(node -> node instanceof Pane && "PlayerCardsPane".equals(node.getId()));

        SequentialTransition sequentialTransition = new SequentialTransition();

        // Lấy số lượng lá bài mỗi người chơi
        int numOfCards = players.get(0).getCardsInHand().size();

        for (int j = 0; j < numOfCards; j++) {
            for (int i = 0; i < players.size(); i++) {
                ArrayList<Card> playerHand = players.get(i).getCardsInHand(); // Lấy bài của người chơi
                Pane playerPane = new Pane(); // Tạo nhóm lá bài cho từng người chơi
                playerPane.setId("PlayerCardsPane");
                double offsetX, offsetY;

                switch (i) {
                    case 0: // Người chơi 1 - Dưới cùng
                        offsetX = (sceneWidth - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                        offsetY = sceneHeight - cardHeight - 20;
                        break;
                    case 1: // Người chơi 2 - Bên phải
                        offsetX = sceneWidth - cardHeight - 20;
                        offsetY = (sceneHeight - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                        break;
                    case 2: // Người chơi 3 - Trên cùng
                        offsetX = (sceneWidth - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                        offsetY = 20;
                        break;
                    case 3: // Người chơi 4 - Bên trái
                        offsetX = 20;
                        offsetY = (sceneHeight - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected player index: " + i);
                }

                Card card = playerHand.get(j);
                ImageView cardBackView = card.getBackView(); // Lấy hình ảnh mặt sau của lá bài

                cardBackView.setFitWidth(cardWidth);
                cardBackView.setFitHeight(cardHeight);

                // Bắt đầu từ trung tâm bàn
                cardBackView.setLayoutX(sceneWidth / 2 - cardWidth / 2);
                cardBackView.setLayoutY(sceneHeight / 2 - cardHeight / 2);

                gameRoot.getChildren().add(cardBackView);

                TranslateTransition transition = new TranslateTransition(Duration.millis(100), cardBackView); // Điều chỉnh thời gian để thay đổi tốc độ
                if (i == 1 || i == 3) { // Người chơi 2 và 4
                    transition.setToX(offsetX + cardHeight / 2 - cardWidth / 2 - cardBackView.getLayoutX()); // Điều chỉnh x sau khi xoay
                    transition.setToY(offsetY + j * gap - cardBackView.getLayoutY());
                    transition.setOnFinished(event -> cardBackView.setRotate(90)); // Xoay 90 độ khi đến tay người chơi
                } else { // Người chơi 1 và 3
                    transition.setToX(offsetX + j * gap - cardBackView.getLayoutX());
                    transition.setToY(offsetY - cardBackView.getLayoutY());
                }
                sequentialTransition.getChildren().add(transition);
            }
        }
        sequentialTransition.play();
    }
    public void displayPlayerHands(AnchorPane gameRoot, ArrayList<PlayerBaccarat> players) {
        Pane playerCardsPane = (Pane) gameRoot.lookup("#PlayerCardsPane");
        if (playerCardsPane == null) {
            playerCardsPane = new Pane();
            playerCardsPane.setId("PlayerCardsPane");
            gameRoot.getChildren().add(playerCardsPane);
        }

        playerCardsPane.getChildren().clear(); // Xóa bài cũ, giữ nguyên `ImageView` đã sử dụng

        for (int i = 0; i < players.size(); i++) {
            ArrayList<Card> playerHand = players.get(i).getCardsInHand();
            double offsetX;
            double offsetY;

            // Đặt vị trí các lá bài theo các góc
            switch (i) {
                case 0: // Dưới cùng
                    offsetX = (sceneWidth - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                    offsetY = sceneHeight - cardHeight - 20;
                    break;
                case 1: // Bên phải
                    offsetX = sceneWidth - cardHeight - 20;
                    offsetY = (sceneHeight - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                    break;
                case 2: // Trên cùng
                    offsetX = (sceneWidth - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                    offsetY = 20;
                    break;
                case 3: // Bên trái
                    offsetX = 20;
                    offsetY = (sceneHeight - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                    break;
                default:
                    throw new IllegalStateException("Unexpected player index: " + i);
            }

            // Đặt vị trí các lá bài
            for (int j = 0; j < playerHand.size(); j++) {
                Card card = playerHand.get(j);
                ImageView cardView = card.getCurrentView(); // Lấy view của lá bài (tái sử dụng nếu đã tồn tại)
                cardView.setFitWidth(cardWidth);
                cardView.setFitHeight(cardHeight);

                if (i == 1 || i == 3) { // Người chơi 2, 4 (xoay ngang)
                    cardView.setRotate(90);
                    cardView.setLayoutX(offsetX + cardHeight / 2 - cardWidth / 2);
                    cardView.setLayoutY(offsetY + j * gap);
                } else { // Người chơi 1, 3
                    cardView.setLayoutX(offsetX + j * gap);
                    cardView.setLayoutY(offsetY);
                }
                playerCardsPane.getChildren().add(cardView);
            }
        }
    }
}
