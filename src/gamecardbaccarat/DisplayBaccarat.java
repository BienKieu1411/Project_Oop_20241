package gamecardbaccarat;

import deckofcards.Card;
import gameplay.MainMenu;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DisplayBaccarat {
    private final int SCENE_WIDTH = 1200; // Chiều rộng Scene
    private final int SCENE_HEIGHT = 675; // Chiều cao Scene
    private final int CARD_WIDTH = 84; // Kích thước bài ngang (7% Scene width)
    private final int CARD_HEIGHT = 120; // Tỷ lệ bài là 1.4
    private final int GAP = 30; // Khoảng cách giữa các lá bài

    // Thêm nút QUIT để quay về MainMenu
    Button quitButton = new Button("Quit Game");

    public DisplayBaccarat() {
    }

    public void displayPlayerHands(Stage stage, AnchorPane gameRoot, ArrayList<PlayerBaccarat> players) {
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
            double offsetY = switch (i) {
                case 0 -> {
                    offsetX = (SCENE_WIDTH - (playerHand.size() - 1) * GAP - CARD_WIDTH) / 2.0;
                    yield SCENE_HEIGHT - CARD_HEIGHT - 20;
                }
                case 1 -> {
                    offsetX = SCENE_WIDTH - CARD_HEIGHT - 20;
                    yield (SCENE_HEIGHT - (playerHand.size() - 1) * GAP - CARD_WIDTH) / 2.0;
                }
                case 2 -> {
                    offsetX = (SCENE_WIDTH - (playerHand.size() - 1) * GAP - CARD_WIDTH) / 2.0;
                    yield 20;
                }
                case 3 -> {
                    offsetX = 20;
                    yield (SCENE_HEIGHT - (playerHand.size() - 1) * GAP - CARD_WIDTH) / 2.0;
                }
                default -> throw new IllegalStateException("Unexpected player index: " + i);
            };

            // Đặt vị trí các lá bài theo các góc
            for (int j = 0; j < playerHand.size(); j++) {
                Card card = playerHand.get(j);
                ImageView cardView = card.getCurrentView(); // Lấy view của lá bài (tái sử dụng nếu đã tồn tại)
                cardView.setFitWidth(CARD_WIDTH);
                cardView.setFitHeight(CARD_HEIGHT);

                if (i == 1 || i == 3) { // Người chơi 2, 4 (xoay ngang)
                    cardView.setRotate(90);
                    cardView.setLayoutX(offsetX + CARD_HEIGHT / 2.0 - CARD_WIDTH / 2.0);
                    cardView.setLayoutY(offsetY + j * GAP);
                } else { // Người chơi 1, 3
                    cardView.setLayoutX(offsetX + j * GAP);
                    cardView.setLayoutY(offsetY);
                }
                playerCardsPane.getChildren().add(cardView);
            }
        }

        quitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #D41920, #C7171E); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-padding: 5px; " +
                "-fx-background-radius: 2px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 1);");
        quitButton.setLayoutX(SCENE_WIDTH - 60); // Đặt vị trí nút QUIT ở góc phải dưới màn hình
        quitButton.setLayoutY(3);

        // Thêm sự kiện để quay về MainMenu
        quitButton.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));
        gameRoot.getChildren().add(quitButton);
    }
}
