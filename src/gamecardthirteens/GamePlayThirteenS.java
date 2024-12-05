package gamecardthirteens;

import gameplay.MainMenu;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.List;

public class GamePlayThirteenS {
    public GamePlayThirteenS(Stage stage, int playerCount, boolean withPlayer, List<String> playerNames) {
        gamePlay(stage, playerCount, withPlayer, playerNames);
    }
    public void gamePlay(Stage stage, int playerCount, boolean withPlayer, List<String> playerNames) {
        AnchorPane gameRoot = new AnchorPane();
        gameRoot.setPrefSize(1200, 675);

        // Hình nền
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/cardsimage/tablepoker.png")));
        background.setFitWidth(1200);
        background.setFitHeight(675);
        background.setPreserveRatio(false);

        // Thêm phần tử vào AnchorPane
        gameRoot.getChildren().addAll(background);
        ThirteenS thirteenS = new ThirteenS(gameRoot, playerCount, withPlayer, playerNames);
        // Gán gameRoot vào Scene
        Scene scene = new Scene(gameRoot, 1200, 675);
        stage.setScene(scene);
    }
}