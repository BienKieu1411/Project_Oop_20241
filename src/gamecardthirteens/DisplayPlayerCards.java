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

public class DisplayPlayerCards {
    private final int sceneWidth = 1200; // Chiều rộng Scene
    private final int sceneHeight = 675; // Chiều cao Scene
    private final int cardWidth = 84;
    private final int cardHeight = 120;
    private final int gap = 30; // Khoảng cách giữa các lá bài
    private Button buttonPlay = new Button("Play");
    private Button buttonDiscard = new Button("Skip Turn");
    private Button buttonSort = new Button("Sort");
    private Button buttonUnselectCard = new Button("Unselect Card");
    private AnchorPane Root = new AnchorPane();
    private ArrayList<PlayerThirteenS> players = new ArrayList<>();
    private int index;

    public DisplayPlayerCards(Stage stage, AnchorPane gameRoot, ArrayList<PlayerThirteenS> players, int i) {
        this.Root = gameRoot;
        this.players = players;
        this.index = i;
        displayPlayerHands(stage, gameRoot, players, i);
        addControlButtons(stage, gameRoot);
    }

    public void displayPlayerHands(Stage stage, AnchorPane gameRoot, ArrayList<PlayerThirteenS> players, int index) {
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
        Button buttonQuit = new Button("Quit Game");
        buttonQuit.setStyle("-fx-background-color: linear-gradient(to bottom, #D41920, #C7171E); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-padding: 5px; " +
                "-fx-background-radius: 2px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 1);");
        buttonQuit.setLayoutX(sceneWidth - 60); // Đặt vị trí nút QUIT ở góc phải dưới màn hình
        buttonQuit.setLayoutY(3);

        // Thêm sự kiện để quay về MainMenu
        buttonQuit.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));
        gameRoot.getChildren().add(buttonQuit);
    }

    private void addControlButtons(Stage stage, AnchorPane gameRoot) {
        HBox buttonBox = new HBox(10);
        buttonBox.setLayoutX((sceneWidth - 300) / 2); // Đặt vào giữa màn hình theo chiều ngang
        buttonBox.setLayoutY(450); // Đặt vị trí phía trên người chơi thứ nhất một chút
        buttonBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 10; -fx-padding: 10;");
        buttonBox.getChildren().addAll(buttonPlay, buttonDiscard, buttonSort, buttonUnselectCard);

        // Thêm các sự kiện hành động cho các nút
        buttonPlay.setOnMouseClicked(event -> {
            playSelectedCards();
            // Cập nhật trạng thái các nút
            resetButtonStates();
            buttonPlay.setStyle("-fx-background-color: green;");
        });

        buttonDiscard.setOnMouseClicked(event -> {
            discardSelectedCards();
            // Cập nhật trạng thái các nút
            resetButtonStates();
            buttonDiscard.setStyle("-fx-background-color: red;");
        });

        buttonSort.setOnMouseClicked(event -> {
            sortCards(stage, gameRoot, players.get(index));
            // Cập nhật trạng thái các nút
            resetButtonStates();
            buttonSort.setStyle("-fx-background-color: blue;");
        });

        buttonUnselectCard.setOnMouseClicked(event -> {
            unselectAllCards();
            // Cập nhật trạng thái các nút
            resetButtonStates();
            buttonUnselectCard.setStyle("-fx-background-color: orange;");
        });

        // Thêm HBox chứa các nút vào gameRoot
        gameRoot.getChildren().add(buttonBox);
    }

    private void resetButtonStates() {
        // Đặt lại trạng thái của các nút với hiệu ứng đẹp hơn
        buttonPlay.setStyle(getButtonStyle());
        buttonDiscard.setStyle(getButtonStyle());
        buttonSort.setStyle(getButtonStyle());
        buttonUnselectCard.setStyle(getButtonStyle());
    }

    private String getButtonStyle() {
        return "-fx-background-color: linear-gradient(to bottom, #3c8dbc, #2c73ab); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px; " +
                "-fx-background-radius: 5px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 1); " +
                "-fx-background-insets: 0,1,2,3; " +
                "-fx-background-radius: 5,4,3,2;";
    }


    private void playSelectedCards() {
        // Thực hiện hành động khi chơi bài
    }

    private void discardSelectedCards() {
        // Thực hiện hành động khi bỏ bài
    }

    private void sortCards(Stage stage, AnchorPane gameRoot ,PlayerThirteenS player) {
        System.out.println("Cards sorted.");
        player.sortCardsInHand();
        displayPlayerHands(stage, gameRoot, players, index);
    }

    private void unselectAllCards() {
        // Thực hiện hành động khi bỏ chọn tất cả các lá bài
        System.out.println("All cards unselected.");
        // Logic bỏ chọn tất cả các lá bài đã được chọn
    }
}