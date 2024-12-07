package gamecardthirteens;

import deckofcards.Card;
import gameplay.MainMenu;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.function.Consumer;

public class DisplayPlayerCards {
    private final int sceneWidth = 1200;
    private final int sceneHeight = 675;
    private final int cardWidth = 84;
    private final int cardHeight = 120;
    private final int gap = 30;
    private HBox buttonBox = new HBox(10);
    private Consumer<String> onPlayerAction;
    private ArrayList<Card> cardsCenter = new ArrayList<>();
    private ArrayList<Card> cardsSelect = new ArrayList<>();

    public ArrayList<Card> getCardsCenter() {
        return cardsCenter;
    }

    public void setCardsCenter(ArrayList<Card> cardsCenter) {
        this.cardsCenter = cardsCenter;
    }

    public ArrayList<Card> getCardsSelect() {
        return cardsSelect;
    }

    public void clearCardsSelect() {
        for(Card card : cardsSelect)
            card.setSelected(false);
        this.cardsSelect.clear();
    }

    public DisplayPlayerCards(Stage stage, AnchorPane gameRoot, ArrayList<PlayerThirteenS> players, int currentPlayerIndex) {
        displayPlayerHands(stage, gameRoot, players, currentPlayerIndex);
        addControlButtons(stage, gameRoot);
    }

    public void displayPlayerHands(Stage stage, AnchorPane gameRoot, ArrayList<PlayerThirteenS> players, int index) {
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
                if(i == index){
                    cardView.setOnMouseClicked(event -> {
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

        // Thêm sự kiện để quay về MainMenu
        buttonQuit.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));
        gameRoot.getChildren().add(buttonQuit);
    }


    private void addControlButtons(Stage stage, AnchorPane gameRoot) {
        buttonBox.setLayoutX((sceneWidth - 200) / 2);
        buttonBox.setLayoutY(450);
        buttonBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 2; -fx-padding: 5;");

        Button buttonPlay = createButton("Play", "blue");
        Button buttonSkip = createButton("Skip", "blue");
        Button buttonSort = createButton("Sort", "blue");
        Button buttonUnselect = createButton("Unselect", "blue");

        buttonPlay.setOnMouseClicked(event -> onPlayerAction.accept("Play"));
        buttonSkip.setOnMouseClicked(event -> onPlayerAction.accept("Skip"));
        buttonSort.setOnMouseClicked(event -> onPlayerAction.accept("Sort"));
        buttonUnselect.setOnMouseClicked(event -> onPlayerAction.accept("Unselect"));

        buttonBox.getChildren().addAll(buttonPlay, buttonSkip, buttonSort, buttonUnselect);
        gameRoot.getChildren().add(buttonBox);
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 5px;");
        return button;
    }

    public void showActionButtons(Consumer<String> actionHandler) {
        this.onPlayerAction = actionHandler;
    }

    public void clearActionButtons(){
        this.onPlayerAction = null;
    }
}