package gameplay;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.Objects;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Tạo menu chính
        MainMenu mainMenu = new MainMenu();
        primaryStage.setScene(mainMenu.createMainMenu(primaryStage));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Game Card - Team 12");
        primaryStage.show();

        // Phát nhạc nền
        playBackgroundMusic("/cardsimage/soundmain.mp3");
    }



    public void playBackgroundMusic(String musicFilePath) {
        try {
            Media music = new Media(Objects.requireNonNull(getClass().getResource(musicFilePath)).toExternalForm());
            mediaPlayer = new MediaPlayer(music); // Sử dụng biến toàn cục
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

    public static MediaPlayer mediaPlayer;
}
