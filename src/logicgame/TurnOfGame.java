package logicgame;

import deckofcards.Card;
import deckofcards.Deck;
import gamecardthirteenn.RuleOfThirteenN;
import gameplay.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import playerofgame.Player;
import java.util.ArrayList;
import java.util.List;

public class TurnOfGame {
    protected int numberOfPlayer;
    protected Deck deck;
    protected ArrayList<Player> players = new ArrayList<>();
    private boolean[] checkTurn;
    private ArrayList<Card> cardPreTurn = new ArrayList<>();
    private int startTurn;
    private ArrayList<Integer> playerEndGame = new ArrayList<Integer>();
    private Rules rules = new Rules();

    public void turnGame (Stage stage, AnchorPane gameRoot, int playerCount, boolean withPlayer, List<String> playerNames, String gameName) {
        // số người chơi
        SetGame setGame = new SetGame();
        switch (gameName) {
            case "ThirteenS":
                Rules rulesS = new Rules();
                rules = rulesS;
                break;
            case "ThirteenN":
                Rules rulesN = new RuleOfThirteenN();
                rules = rulesN;
                break;
        }
        numberOfPlayer = playerCount;
        deck = new Deck(52);
        // thêm người chơi
        setGame.setNumberOfPlayer(playerCount);
        setGame.addPlayer(withPlayer, playerNames);
        // chia bài
        SettingsMenu settingsMenu = SettingsMenu.getInstance();
        if (settingsMenu.isImageMode()) {
            // Nếu chế độ hình ảnh bật
            ShuffleEffect shuffleEffect = new ShuffleEffect(gameRoot, 52);
            shuffleEffect.startShuffle(() -> {
                // Sau khi xáo bài hoàn tất, gọi dealCard để chia bài
                setGame.dealCard(stage, gameRoot, deck, () -> {
                    gameRoot.getChildren().removeIf(node -> node instanceof ImageView && "CardImage".equals(node.getId()));
                    players = setGame.players;
                    setTurn();
                    turnOfGame(stage, gameRoot, players);
                });
            });
        } else {
            // Nếu chế độ hình ảnh không bật, trực tiếp chia bài
            setGame.dealCard(stage, gameRoot, deck, () -> {
            });
            players = setGame.players;
            gameRoot.getChildren().removeIf(node -> node instanceof ImageView && "CardImage".equals(node.getId()));
            setTurn();
            turnOfGame(stage, gameRoot, players);
        }
    }

    // khởi tạo turn ban đầu, nếu ai có 3 Bích sẽ được đánh trước, nếu không ai có 3 Bích trên tay thì sẽ ngẫu nhiên người chơi đánh trước
    public void setTurn() {
        checkTurn = new boolean[numberOfPlayer];
        for (int i = 0; i < numberOfPlayer; i++) {
            checkTurn[i] = false;
        }
        for (Player player : players) {
            for (Card card : player.getCardsInHand()) {
                if (card.getRank() == 3 && card.getSuit() == 1) {
                    checkTurn[players.indexOf(player)] = true;
                    return;
                }
            }
        }
    }

    // Set lại turn mới, mọi người skip trong turn trước sẽ được đánh lại trong turn mới
    public void resetTurn() {
        for (int i = 0; i < players.size(); ++i) checkTurn[i] = true;
        if(playerEndGame.isEmpty()) return;
        for (int i = 0; i < playerEndGame.size(); ++i) checkTurn[playerEndGame.get(i)] = false;
    }

    // Lượt chơi sẽ kết thúc khi chỉ còn 1 người chưa Skip
    public boolean checkEndTurn() {
        int counter = 0, index = 0;
        for (int i = 0; i < players.size(); ++i) {
            if (checkTurn[i]) {
                counter++;
                index = i;
            }
        }
        if (counter == 1) {
            startTurn = index;
            return true;
        }
        return false;
    }

    // Lấy lựa chọn của người chơi
    public boolean playCards(Player player, ArrayList<Card> listCardPlayed) {
        if (rules.checkCardsDrop(listCardPlayed, cardPreTurn)) {
            for (Card card : listCardPlayed) {
                player.dropCard(card); // Xóa bài khỏi tay người chơi
            }
            this.cardPreTurn = new ArrayList<>(listCardPlayed); // Cập nhật bài đã đánh
            return true;
        }
        return false;
    }


    // Bắt đầu turn chính của game, game chỉ dừng lại khi đã có xếp hạng của các người chơi tham gia
    public void turnOfGame(Stage stage, AnchorPane gameRoot, ArrayList<Player> players) {
        DisplayPlayer displayPlayerCards = new DisplayPlayer(stage, gameRoot, players, 0);
        Timeline gameTimeline = new Timeline();
        int[] currentPlayerIndex = {0};
        if (checkEndTurn()) {
            resetTurn();
            currentPlayerIndex[0] = startTurn;
        } else resetTurn();
        KeyFrame turnFrame = new KeyFrame(Duration.seconds(1), event -> {
            if(players.size() == 1){
                gameTimeline.stop();
                rules.endOfGame(playerEndGame);
                if(numberOfPlayer < 3)
                    new WinnerThirteenS(stage,gameRoot,rules.topWinners);
                else new WinnerDisplay(stage,gameRoot, rules.topWinners.getFirst());
            }
            if (checkEndTurn()) {
                resetTurn();
                currentPlayerIndex[0] = startTurn;
                cardPreTurn.clear();
                displayPlayerCards.setCardsCenter(cardPreTurn);
            }
            Player currentPlayer = players.get(currentPlayerIndex[0]);
            if (checkTurn[currentPlayerIndex[0]]) {
                currentPlayer.setCardsFaceUp();
                displayPlayerCards.displayPlayerHands(stage, gameRoot, players, currentPlayerIndex[0]);
                // Hiển thị các nút hành động
                displayPlayerCards.showActionButtons(action -> {
                    if (!currentPlayer.getIsBot()) {
                        switch (action) {
                            case "Skip" -> {
                                displayPlayerCards.clearCardsSelect();
                                checkTurn[currentPlayerIndex[0]] = false;
                                // Giữ nguyên bài trung tâm khi người chơi bỏ lượt
                                displayPlayerCards.setCardsCenter(cardPreTurn);
                                endPlayerTurn(currentPlayer, currentPlayerIndex, players, gameTimeline);
                            }
                            case "Play" -> {
                                ArrayList<Card> cards = displayPlayerCards.getCardsSelect();
                                currentPlayer.setListCardPlayed(cards);
                                if (playCards(currentPlayer, currentPlayer.getListCardPlayed())) {
                                    displayPlayerCards.setCardsCenter(new ArrayList<>(cardPreTurn)); // Hiển thị bài trung tâm
                                    displayPlayerCards.clearCardsSelect();
                                    endPlayerTurn(currentPlayer, currentPlayerIndex, players, gameTimeline);
                                } else {
                                    System.out.println("Invalid move. Try again.");
                                }
                                displayPlayerCards.clearCardsSelect();
                            }
                            case "Sort" -> {
                                currentPlayer.sortCardsInHand();
                                displayPlayerCards.displayPlayerHands(stage, gameRoot, players, currentPlayerIndex[0]);
                            }
                            case "Unselect" -> {
                                displayPlayerCards.clearCardsSelect();
                                displayPlayerCards.displayPlayerHands(stage, gameRoot, players, currentPlayerIndex[0]);
                            }
                        }
                    }
                });
                if(currentPlayer.getIsBot()) {
                    String chooseOfBot = currentPlayer.getSelection(cardPreTurn);
                    if (chooseOfBot.equals("Sort")) {
                        currentPlayer.sortCardsInHand();
                        displayPlayerCards.displayPlayerHands(stage, gameRoot, players, currentPlayerIndex[0]);
                    } else if (chooseOfBot.equals("Skip")) {
                        checkTurn[currentPlayerIndex[0]] = false;
                        // Giữ nguyên bài trung tâm khi người chơi bỏ lượt
                        displayPlayerCards.setCardsCenter(cardPreTurn);
                        endPlayerTurn(currentPlayer, currentPlayerIndex, players, gameTimeline);
                    } else {
                        cardPreTurn = currentPlayer.getListCardPlayed();
                        for (Card card : cardPreTurn) {
                            currentPlayer.getCardsInHand().remove(card);
                        }
                        displayPlayerCards.setCardsCenter(new ArrayList<>(cardPreTurn)); // Hiển thị bài trung tâm
                        displayPlayerCards.displayPlayerHands(stage, gameRoot, players, currentPlayerIndex[0]);
                        endPlayerTurn(currentPlayer, currentPlayerIndex, players, gameTimeline);
                    }
                }
            } else {
                displayPlayerCards.displayPlayerHands(stage, gameRoot, players, 0);
                endPlayerTurn(currentPlayer, currentPlayerIndex, players, gameTimeline);
            }
        });

        gameTimeline.getKeyFrames().add(turnFrame);
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        gameTimeline.play();
    }

    private void endPlayerTurn(Player currentPlayer, int[] currentPlayerIndex, ArrayList<Player> players, Timeline gameTimeline) {
        currentPlayer.setCardsFaceDown();
        currentPlayerIndex[0] = (currentPlayerIndex[0] + 1) % players.size();
        rules.endOfGame(playerEndGame);
        if (players.size() == 1) {
            playerEndGame.add(currentPlayerIndex[0]);
            gameTimeline.stop();
        }
    }
}