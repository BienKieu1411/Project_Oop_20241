package gameplay;

import deckofcards.Card;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import playerofgame.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageMode implements DisplayMode{
    private final int sceneWidth = 1200;
    private final int sceneHeight = 675;
    private final int cardWidth = 84;
    private final int cardHeight = 120;
    private final int gap = 30;
    private ArrayList<Card> cardsCenter = new ArrayList<>();
    private ArrayList<Card> cardsSelect = new ArrayList<>();

    public void setCardsSelect(ArrayList<Card> cardsSelect) {
        this.cardsSelect = cardsSelect;
    }

    public void setCardsCenter(ArrayList<Card> cardsCenter) {
        this.cardsCenter = cardsCenter;
    }

    @Override
    public void displayPlayerHands(Stage stage, AnchorPane gameRoot, ArrayList<Player> players, int index) {
        Pane playerCardsPane = (Pane) gameRoot.lookup("#PlayerCardsPane");
        if (playerCardsPane == null) {
            playerCardsPane = new Pane();
            playerCardsPane.setId("PlayerCardsPane");
            gameRoot.getChildren().add(playerCardsPane);
        }

        playerCardsPane.getChildren().clear(); // Xóa bài cũ

        for (int i = 0; i < players.size(); i++) {
            ArrayList<Card> playerHand = players.get(i).getCardsInHand();
            double offsetX;
            double offsetY;

            // Tính toán vị trí bài theo từng người chơi
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

            // Tạo các thẻ bài
            for (int j = 0; j < playerHand.size(); j++) {
                Card card = playerHand.get(j);
                ImageView cardView = card.getCurrentView();
                cardView.setFitWidth(cardWidth);
                cardView.setFitHeight(cardHeight);

                // Đặt vị trí thẻ bài
                switch (i){
                    case 0:
                        if(card.isSelected()){
                            cardView.setLayoutX(offsetX + j * gap);
                            cardView.setLayoutY(offsetY - 40);
                        }
                        else {
                            cardView.setLayoutX(offsetX + j * gap);
                            cardView.setLayoutY(offsetY);
                        }
                        break;
                    case 1:
                        if(card.isSelected()){
                            cardView.setRotate(90);
                            cardView.setLayoutX(offsetX + cardHeight / 2.0 - cardWidth / 2.0 - 40);
                            cardView.setLayoutY(offsetY + j * gap);
                        }
                        else {
                            cardView.setRotate(90);
                            cardView.setLayoutX(offsetX + cardHeight / 2.0 - cardWidth / 2.0);
                            cardView.setLayoutY(offsetY + j * gap);
                        }
                        break;
                    case 2:
                        if(card.isSelected()){
                            cardView.setLayoutX(offsetX + j * gap);
                            cardView.setLayoutY(offsetY + 40);
                        }
                        else {
                            cardView.setLayoutX(offsetX + j * gap);
                            cardView.setLayoutY(offsetY);
                        }
                        break;
                    default:
                        if(card.isSelected()){
                            cardView.setRotate(-90);
                            cardView.setLayoutX(offsetX + cardHeight / 2.0 - cardWidth / 2.0 + 40);
                            cardView.setLayoutY(offsetY + j * gap);
                        }
                        else {
                            cardView.setRotate(-90);
                            cardView.setLayoutX(offsetX + cardHeight / 2.0 - cardWidth / 2.0);
                            cardView.setLayoutY(offsetY + j * gap);
                        }
                }

                // Tạo Label hiển thị tên người chơi
                Label playerNameLabel = new Label(players.get(i).getNameOfPlayer());
                playerNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF; "
                        + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.8), 2, 0, 0, 1);");
                playerNameLabel.setId("PlayerNameLabel_" + i);
                // Đặt vị trí cho tên người chơi
                switch (i) {
                    case 0: // Dưới cùng
                        playerNameLabel.setLayoutX(sceneWidth / 2.0 - playerNameLabel.prefWidth(-1) / 2.0);
                        playerNameLabel.setLayoutY(sceneHeight - 18); // Gần cạnh dưới
                        break;
                    case 1:// Bên phải
                        playerNameLabel.setRotate(90);
                        playerNameLabel.setLayoutX(sceneWidth - 23); // Cách cạnh phải
                        playerNameLabel.setLayoutY(sceneHeight / 2.0 - playerNameLabel.prefHeight(-1) / 2.0);
                        break;
                    case 2: // Trên cùng
                        playerNameLabel.setLayoutX(sceneWidth / 2.0 - playerNameLabel.prefWidth(-1) / 2.0);
                        playerNameLabel.setLayoutY(2); // Cách cạnh trên
                        break;
                    case 3: // Bên trái
                        playerNameLabel.setRotate(-90);
                        playerNameLabel.setLayoutX(-5); // Cách cạnh trái
                        playerNameLabel.setLayoutY(sceneHeight / 2.0 - playerNameLabel.prefHeight(-1) / 2.0);
                        break;
                }
                // Thêm Label vào giao diện
                gameRoot.getChildren().add(playerNameLabel);

                if (i == index) {
                    cardView.setOnMouseClicked(event -> {
                        if (card.isSelected()) {
                            card.setSelected(false);
                            cardsSelect.remove(card);

                        } else {
                            card.setSelected(true);
                            cardsSelect.add(card);
                        }

                        // Hiển thị lại bài
                        displayPlayerHands(stage, gameRoot, players, index);
                    });
                }

                playerCardsPane.getChildren().add(cardView);
            }
        }
        // Nút thoát game
        Button buttonQuit = new Button("Quit Game");
        buttonQuit.setStyle("-fx-background-color: linear-gradient(to bottom, #D41920, #C7171E); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-padding: 5px; " +
                "-fx-background-radius: 2px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 1);");
        buttonQuit.setLayoutX(sceneWidth - 60);
        buttonQuit.setLayoutY(3);
        MainMenu.addHoverEffect(Arrays.asList(buttonQuit));

        // Thêm sự kiện để quay về MainMenu
        buttonQuit.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));
        gameRoot.getChildren().add(buttonQuit);
        displayCenterCards(playerCardsPane);
    }

    public void displayCenterCards(Pane playerCardsPane) {
        if (!cardsCenter.isEmpty()) {
            double offsetX = (sceneWidth - (cardsCenter.size() - 1) * gap - cardWidth) / 2.0;
            double offsetY = (sceneHeight - cardHeight) / 2.0;

            for (int i = 0; i < cardsCenter.size(); i++) {
                Card card = cardsCenter.get(i);
                ImageView cardView = card.getFrontView();
                cardView.setFitWidth(cardWidth);
                cardView.setFitHeight(cardHeight);
                cardView.setLayoutX(offsetX + i * gap);
                cardView.setLayoutY(offsetY);

                if (!playerCardsPane.getChildren().contains(cardView)) {
                    playerCardsPane.getChildren().add(cardView);
                }
            }
        }
    }
}
