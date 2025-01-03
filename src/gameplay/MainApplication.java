package gameplay;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.Objects;

public class MainApplication extends Application {
    public static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        // Tạo menu chính
        MainMenu mainMenu = new MainMenu();
        primaryStage.setScene(mainMenu.createMainMenu(primaryStage));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Game Card - Team 12");

        // Đặt biểu tượng cho taskbar và title bar
        setApplicationIcon(primaryStage, "/cardsimage/icongame.png");

        primaryStage.show();

        // Phát nhạc nền
        playBackgroundMusic("/cardsimage/soundmain.mp3");
    }

    public void setApplicationIcon(Stage stage, String iconPath) {
        try {
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Không thể tải biểu tượng: " + e.getMessage());
        }
    }

    public void playBackgroundMusic(String musicFilePath) {
        try {
            Media music = new Media(Objects.requireNonNull(getClass().getResource(musicFilePath)).toExternalForm());
            mediaPlayer = new MediaPlayer(music);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Không thể phát nhạc: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
