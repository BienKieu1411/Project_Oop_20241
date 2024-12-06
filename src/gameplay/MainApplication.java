package gameplay;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu();
        primaryStage.setScene(mainMenu.createMainMenu(primaryStage));
        primaryStage.setTitle("Game Card - Team 12");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
