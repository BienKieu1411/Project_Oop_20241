package gameplay;

import deckofcards.Card;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import playerofgame.Player;

import java.util.ArrayList;

public class TextMode implements DisplayMode {
    private final int sceneWidth = 1200;
    private final int sceneHeight = 675;
    private final int cardWidth = 40;
    private final int cardHeight = 25;
    private final int gap = 45;
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
                    offsetY = sceneHeight - 50;
                    break;
                case 1: // Bên phải
                    offsetX = sceneWidth - 50;
                    offsetY = (sceneHeight - (playerHand.size() - 1) * gap - cardHeight) / 2.0;
                    break;
                case 2: // Trên cùng
                    offsetX = (sceneWidth - (playerHand.size() - 1) * gap - cardWidth) / 2.0;
                    offsetY = 50;
                    break;
                case 3: // Bên trái
                    offsetX = 50;
                    offsetY = (sceneHeight - (playerHand.size() - 1) * gap - cardHeight) / 2.0;
                    break;
                default:
                    throw new IllegalStateException("Unexpected player index: " + i);
            }

            // Tạo các thẻ bài
            for (int j = 0; j < playerHand.size(); j++) {
                Card card = playerHand.get(j);
                Text cardText = card.isFaceUp() ? new Text(card.toString()) : new Text("Back");
                cardText.setFont(new Font("Garamond", 20));
                // Đặt vị trí thẻ bài
                double width = cardText.getBoundsInLocal().getWidth();
                double height = cardText.getBoundsInLocal().getHeight();
                switch (i){
                    case 0:
                        if(card.isSelected()){
                            cardText.setLayoutX(offsetX + j * gap);
                            cardText.setLayoutY(offsetY - 40);
                        }
                        else {
                            cardText.setLayoutX(offsetX + j * gap);
                            cardText.setLayoutY(offsetY);
                        }
                        break;
                    case 1:
                        if(card.isSelected()){
                            cardText.setRotate(90);
                            cardText.setLayoutX(offsetX + height / 2.0 - width / 2.0 - 40);
                            cardText.setLayoutY(offsetY + j * gap);
                        }
                        else {
                            cardText.setRotate(90);
                            cardText.setLayoutX(offsetX + height / 2.0 - width / 2.0);
                            cardText.setLayoutY(offsetY + j * gap);
                        }
                        break;
                    case 2:
                        if(card.isSelected()){
                            cardText.setLayoutX(offsetX + j * gap);
                            cardText.setLayoutY(offsetY + 40);
                        }
                        else {
                            cardText.setLayoutX(offsetX + j * gap);
                            cardText.setLayoutY(offsetY);
                        }
                        break;
                    default:
                        if(card.isSelected()){
                            cardText.setRotate(-90);
                            cardText.setLayoutX(offsetX + height / 2.0 - width / 2.0 + 40);
                            cardText.setLayoutY(offsetY + j * gap);
                        }
                        else {
                            cardText.setRotate(-90);
                            cardText.setLayoutX(offsetX + height / 2.0 - width / 2.0);
                            cardText.setLayoutY(offsetY + j * gap);
                        }
                }
                if(i == index){
                    cardText.setOnMouseClicked(event -> {
                        if (card.isSelected()) {
                            card.setSelected(false);
                            cardsSelect.remove(card);
                        } else {
                            card.setSelected(true);
                            cardsSelect.add(card);
                        }
                        displayPlayerHands(stage, gameRoot, players, index); // Hiển thị lại bài
                    });
                }
                playerCardsPane.getChildren().add(cardText);
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

        // Thêm sự kiện để quay về MainMenu
        buttonQuit.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));
        gameRoot.getChildren().add(buttonQuit);
        displayCenterCards(playerCardsPane);
    }
    public void displayCenterCards(Pane playerCardsPane) {
        if (!cardsCenter.isEmpty()) {
            double offsetX = (sceneWidth - (cardsCenter.size() - 1) * 70 - 40) / 2.0;
            double offsetY = (sceneHeight - 22.5) / 2.0;

            for (int i = 0; i < cardsCenter.size(); i++) {
                Card card = cardsCenter.get(i);
                Text cardText = new Text(card.toString());
                cardText.setFont(new Font("Garamond", 30));
                // Timeline để thay đổi gradient
                Timeline timeline = new Timeline();

                // Tạo các keyframes để di chuyển gradient
                for (int j = 0; j <= 100; j++) {
                    int shift = j;
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(j * 20), event -> {
                        // Tính toán điểm màu động
                        LinearGradient gradient = new LinearGradient(
                                0, 0, 1, 0, true, CycleMethod.REPEAT,
                                new Stop((0.0 + shift / 100.0) % 1, Color.RED),
                                new Stop((0.16 + shift / 100.0) % 1, Color.ORANGE),
                                new Stop((0.33 + shift / 100.0) % 1, Color.YELLOW),
                                new Stop((0.5 + shift / 100.0) % 1, Color.GREEN),
                                new Stop((0.66 + shift / 100.0) % 1, Color.BLUE),
                                new Stop((0.83 + shift / 100.0) % 1, Color.INDIGO),
                                new Stop((1.0 + shift / 100.0) % 1, Color.VIOLET)
                        );
                        // Áp dụng gradient động cho Text
                        cardText.setFill(gradient);
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Lặp vô tận
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
                cardText.setLayoutX(offsetX + i * 70);
                cardText.setLayoutY(offsetY);
                if (!playerCardsPane.getChildren().contains(cardText)) {
                    playerCardsPane.getChildren().add(cardText);
                }
            }
        }
    }
}
