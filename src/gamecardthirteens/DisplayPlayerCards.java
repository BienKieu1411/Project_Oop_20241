package gamecardthirteens;

import deckofcards.Card;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class DisplayPlayerCards {
    private final int sceneWidth = 1200; // Chiều rộng Scene
    private final int sceneHeight = 675; // Chiều cao Scene
    private final int cardWidth = 84; // Kích thước bài ngang (7% Scene width)
    private final int cardHeight = 120; // Tỷ lệ bài là 1.4
    private final int gap = 30; // Khoảng cách giữa các lá bài

    public DisplayPlayerCards(AnchorPane gameRoot, ArrayList<PlayerThirteenS> playerThirteenS, int playerCount) {
        displayPlayerHands(gameRoot, playerThirteenS);
    }

    public void displayPlayerHands(AnchorPane gameRoot, ArrayList<PlayerThirteenS> players) {
        // Chỉ xóa các lá bài, giữ nguyên nền và nút
        gameRoot.getChildren().removeIf(node -> node instanceof Pane && "PlayerCardsPane".equals(node.getId()));
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

            for (int j = 0; j < playerHand.size(); j++) {
                Card card = playerHand.get(j);
                ImageView cardView = card.getFrontView(); // Lấy hình ảnh lá bài
                cardView.setFitWidth(cardWidth);
                cardView.setFitHeight(cardHeight);

                if (i == 1 || i == 3) { // Người chơi 2 và 4 (xoay ngang)
                    cardView.setRotate(90);
                    cardView.setLayoutX(offsetX + cardHeight / 2 - cardWidth / 2); // Điều chỉnh x sau khi xoay
                    cardView.setLayoutY(offsetY + j * gap);
                } else { // Người chơi 1 và 3
                    cardView.setLayoutX(offsetX + j * gap);
                    cardView.setLayoutY(offsetY);
                }

                playerPane.getChildren().add(cardView); // Thêm lá bài vào nhóm
            }

            gameRoot.getChildren().add(playerPane); // Thêm nhóm bài vào giao diện
        }
    }
}