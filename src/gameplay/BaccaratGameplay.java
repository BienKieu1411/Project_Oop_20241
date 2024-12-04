package gameplay;

import deckofcards.Card;
import gamecardbaccarat.DeckOfBaccarat;
import gamecardbaccarat.PlayerBaccarat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.IOException;

public class BaccaratGameplay {
    DeckOfBaccarat deck = new DeckOfBaccarat();

    private BaccaratDataManager dataManager;

    @FXML
    AnchorPane baccaratGameplay;

    public void initializeGame(BaccaratDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @FXML
    private HBox player1Cards, player3Cards;

    @FXML
    private VBox player2Cards, player4Cards;

    @FXML
    private StackPane player1Info, player2Info, player3Info, player4Info;

    @FXML
    private Label winnerNotification;

    private void updatePlayerInfo(PlayerBaccarat player, StackPane infoPane) {
        Label info = new Label(player.getNameOfPlayer() );
        info.setFont(new Font("Book Antiqua", 20));
        infoPane.getChildren().clear();
        infoPane.getChildren().add(info);
    }

    private void updatePlayerCards(HBox hbox, PlayerBaccarat player) {
        hbox.getChildren().clear();
        for (Card card : player.getCardsInHand()) {
            ImageView cardView = new ImageView(card.getFrontImage());
            cardView.setFitHeight(145.2);
            cardView.setFitWidth(100);
            hbox.getChildren().add(cardView);
        }
    }

    private void updatePlayerCards(VBox vbox, PlayerBaccarat player) {
        vbox.getChildren().clear();
        for (Card card : player.getCardsInHand()) {
            ImageView cardView = new ImageView(card.getFrontImage());
            cardView.setFitHeight(145.2);
            cardView.setFitWidth(100);
            vbox.getChildren().add(cardView);
        }
    }

    @FXML
    private void startGame(ActionEvent event) throws IOException {
        MainMenu.baccarat.dealCard(deck);
        int numberOfPlayers = MainMenu.baccarat.getNumberOfPlayers();

        // Hiển thị bài của từng người chơi
        for (int i = 0; i < numberOfPlayers; ++i) {
            PlayerBaccarat player = MainMenu.baccarat.getPlayersBaccarat().get(i);
            String nameOfPlayer = player.getNameOfPlayer();

            // Xử lý HBox hoặc VBox để hiển thị bài của từng người
            if (i % 2 == 0) { // HBox
                HBox cardsInHand = (HBox) baccaratGameplay.getChildren().get(i);
                cardsInHand.getChildren().clear();
                for (Card card : player.getCardsInHand()) {
                    ImageView cardDisplay = new ImageView(card.getFrontImage());
                    cardDisplay.setFitHeight(145.2);
                    cardDisplay.setFitWidth(100);
                    cardsInHand.getChildren().add(cardDisplay);
                }
            } else { // VBox
                VBox cardsInHand = (VBox) baccaratGameplay.getChildren().get(i);
                cardsInHand.getChildren().clear();
                for (Card card : player.getCardsInHand()) {
                    ImageView cardDisplay = new ImageView(card.getFrontImage());
                    cardDisplay.setFitHeight(145.2);
                    cardDisplay.setFitWidth(100);
                    cardsInHand.getChildren().add(cardDisplay);
                }
            }

            // Cập nhật thông tin người chơi
            StackPane playerInformationLayout;
            switch (i) {
                case 0 -> playerInformationLayout = (StackPane) baccaratGameplay.lookup("#player1StackPane");
                case 1 -> playerInformationLayout = (StackPane) baccaratGameplay.lookup("#player2StackPane");
                case 2 -> playerInformationLayout = (StackPane) baccaratGameplay.lookup("#player3StackPane");
                case 3 -> playerInformationLayout = (StackPane) baccaratGameplay.lookup("#player4StackPane");
                default -> {
                    continue;
                }
            }

            playerInformationLayout.getChildren().clear(); // Xóa nội dung cũ
            Label playerInfoLabel = new Label(nameOfPlayer);
            playerInfoLabel.setFont(new Font("Book Antiqua", 20));
            playerInformationLayout.getChildren().add(playerInfoLabel);
        }
    }

}

