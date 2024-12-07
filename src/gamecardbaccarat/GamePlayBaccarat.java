package gamecardbaccarat;

import gameplay.MainMenu;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.List;


public class GamePlayBaccarat {
    private static final Image tablePokerImage = new Image(GamePlayBaccarat.class.getResourceAsStream("/cardsimage/tablepoker.png"));
    private Scene scene; // Lưu trữ Scene duy nhất
    private AnchorPane gameRoot;

    public GamePlayBaccarat(Stage stage, int playerCount, boolean withPlayer, List<String> playerNames) {
        initializeGame(stage, playerCount, withPlayer, playerNames);
    }

    private void initializeGame(Stage stage, int playerCount, boolean withPlayer, List<String> playerNames) {
        // Tạo hoặc tái sử dụng AnchorPane
        if (gameRoot == null) {
            gameRoot = new AnchorPane();
            gameRoot.setPrefSize(1200, 675);

            // Thêm hình nền một lần
            ImageView background = new ImageView(tablePokerImage);
            background.setFitWidth(1200);
            background.setFitHeight(675);
            background.setPreserveRatio(false);
            gameRoot.getChildren().add(background);
        }

        // Tạo hoặc tái sử dụng Scene
        if (scene == null) {
            scene = new Scene(gameRoot, 1200, 675);
        }

        // Cập nhật nội dung trò chơi
        Baccarat baccarat = new Baccarat(stage,gameRoot, playerCount, withPlayer, playerNames);

        // Đặt Scene vào Stage
        stage.setScene(scene);
    }
}