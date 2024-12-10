package gameplay;

import gamecardbaccarat.Baccarat;
import gamecardthirteenn.ThirteenN;
import gamecardthirteens.ThirteenS;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class GamePlay {
    private Scene scene; // Lưu trữ Scene duy nhất
    private AnchorPane gameRoot;

    public GamePlay(Stage stage, int playerCount, boolean withPlayer, List<String> playerNames, String nameGame) {
        initializeGame(stage, playerCount, withPlayer, playerNames, nameGame);
    }

    private void initializeGame(Stage stage, int playerCount, boolean withPlayer, List<String> playerNames, String nameGame) {
        // Tạo hoặc tái sử dụng AnchorPane
        if (gameRoot == null) {
            gameRoot = new AnchorPane();
            gameRoot.setPrefSize(1200, 675);

            // Thêm hình nền một lần
            SettingsMenu settingsMenu = SettingsMenu.getInstance(); // Lấy thể hiện duy nhất
            ImageView background = settingsMenu.isImageMode() ?  MainMenu.TABLE : MainMenu.TABLE_TEXT;
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
        switch (nameGame) {
            case "ThirteenS":
                ThirteenS thirteenS = new ThirteenS(stage, gameRoot, playerCount, withPlayer, playerNames, nameGame);
                break;
            case "ThirteenN":
                ThirteenN thirteenN = new ThirteenN(stage, gameRoot, playerCount, withPlayer, playerNames, nameGame);
                break;
            case "Baccarat":
                Baccarat baccarat = new Baccarat(stage,gameRoot, playerCount, withPlayer, playerNames);
        }
        // Đặt Scene vào Stage
        stage.setScene(scene);
    }
}
