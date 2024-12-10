package gameplay;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;


public class GameSelectionMenu {
    public static Scene createGameSelectionScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1200, 675);

        // Buttons
        // Tạo ImageView cho các nút thay thế
        ImageView baccarat = createImageView("/cardsimage/baccarat.png", 300, 300);
        baccarat.setLayoutX(75);
        baccarat.setLayoutY(137.5);
        baccarat.setOnMouseClicked(event -> stage.setScene(new ModeSelectionMenu().createModeSelectionScene(stage, "Baccarat")));

        ImageView thirteenS = createImageView("/cardsimage/thirteenS.png", 300, 300);
        thirteenS.setLayoutX(450);
        thirteenS.setLayoutY(137.5);
        thirteenS.setOnMouseClicked(event -> stage.setScene(new ModeSelectionMenu().createModeSelectionScene(stage, "ThirteenS")));

        ImageView thirteenN = createImageView("/cardsimage/ThirteenN.jpg", 300, 300);
        thirteenN.setLayoutX(825);
        thirteenN.setLayoutY(137.5);
        thirteenN.setOnMouseClicked(event -> stage.setScene(new ModeSelectionMenu().createModeSelectionScene(stage, "ThirteenN")));

        // Nút quay lại
        Button backButton = MainMenu.createButton("Back", 200, 50);
        backButton.setLayoutX((root.getPrefWidth() - backButton.getPrefWidth()) / 2); // Ở giữa màn hình
        backButton.setLayoutY(550); // Dưới cùng
        backButton.setOnMouseClicked(event -> stage.setScene(new MainMenu().createMainMenu(stage)));

        // Thêm hiệu ứng di chuột vào hình ảnh
        addHoverEffectToImageView(List.of(baccarat, thirteenS, thirteenN));

        // Thêm các thành phần vào root
        root.getChildren().addAll(MainMenu.BACKGROUND_IMAGE, baccarat, thirteenS,thirteenN, backButton);

        return new Scene(root, 1200, 675);

    }

    // Hàm tạo ImageView
    private static ImageView createImageView(String imagePath, double width, double height) {
        Image image = new Image(MainMenu.class.getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    // Thêm hiệu ứng di chuột vào ImageView
    private static void addHoverEffectToImageView(List<ImageView> images) {
        for (ImageView image : images) {
            // Phóng to khi di chuột vào
            image.setOnMouseEntered(event -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), image);
                scaleTransition.setToX(1.1);
                scaleTransition.setToY(1.1);
                scaleTransition.play();
            });

            // Trở về kích thước ban đầu khi chuột rời đi
            image.setOnMouseExited(event -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), image);
                scaleTransition.setToX(1.0);
                scaleTransition.setToY(1.0);
                scaleTransition.play();
            });
        }
    }
}
