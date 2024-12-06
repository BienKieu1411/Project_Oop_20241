package gameplay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class PlayerNameInputMenu {

    public Scene createPlayerNameInputScene(Stage stage, String game, int playerCount, boolean withPlayer) {

        // Create the root VBox container
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        // Title
        Label title = new Label("Game Setup");
        title.setFont(Font.font("Arial", 32));
        title.setTextFill(Color.web("#F4D03F"));
        title.setEffect(new DropShadow(10, Color.BLACK));

        VBox inputBox = new VBox(15);
        inputBox.setAlignment(Pos.CENTER);
        List<TextField> nameFields = new ArrayList<>();

        if (withPlayer) {
            title.setText("Enter Player Names");

            // Generate name input fields
            for (int i = 0; i < playerCount; i++) {
                HBox fieldContainer = new HBox(10);
                fieldContainer.setAlignment(Pos.CENTER);

                Label playerLabel = new Label("Player " + (i + 1) + ":");
                playerLabel.setFont(Font.font("Arial", 18));
                playerLabel.setTextFill(Color.WHITE);

                TextField nameField = new TextField();
                nameField.setPromptText("Enter name");
                nameField.setPrefWidth(300);
                nameField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-font-size: 14;");
                nameFields.add(nameField);
                nameField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        int currentIndex = nameFields.indexOf(nameField);
                        if (currentIndex + 1 < nameFields.size()) {
                            nameFields.get(currentIndex + 1).requestFocus();
                        } else {
                            System.out.println("All fields filled or final action triggered.");
                        }
                    }
                });

                fieldContainer.getChildren().addAll(playerLabel, nameField);
                inputBox.getChildren().add(fieldContainer);
            }
        } else {
            title.setText("Game with Bots");
        }

        // Start button
        Button startButton = new Button("Start Game");
        startButton.setFont(Font.font("Arial", 18));
        startButton.setTextFill(Color.WHITE);
        startButton.setStyle("-fx-background-color: linear-gradient(to bottom, #1D89F4, #1B62C5); -fx-background-radius: 10; -fx-font-size: 16px;");
        startButton.setOnMouseEntered(event -> startButton.setStyle("-fx-background-color: linear-gradient(to bottom, #1B62C5, #1D89F4); -fx-background-radius: 10; -fx-font-size: 16px;"));
        startButton.setOnMouseExited(event -> startButton.setStyle("-fx-background-color: linear-gradient(to bottom, #1D89F4, #1B62C5); -fx-background-radius: 10; -fx-font-size: 16px;"));

        startButton.setOnAction(event -> {
            if (withPlayer) {
                List<String> playerNames = new ArrayList<>();
                boolean allValid = true;

                for (int i = 0; i < playerCount; i++) {
                    String name = nameFields.get(i).getText().trim();
                    if (name.isEmpty()) {
                        nameFields.get(i).setStyle("-fx-border-color: red; -fx-background-radius: 10; -fx-border-radius: 10; -fx-font-size: 14;");
                        allValid = false;
                    } else {
                        playerNames.add(name);
                    }
                }

                if (allValid) {
                    GameController.startGame(stage, game, playerCount, true, playerNames);
                }
            } else {
                List<String> playerNames = new ArrayList<>();
                playerNames.add("Player");
                GameController.startGame(stage, game, playerCount, false, playerNames);
            }
        });

        // Back button
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", 18));
        backButton.setTextFill(Color.WHITE);
        backButton.setStyle("-fx-background-color: linear-gradient(to bottom, #E57373, #C62828); -fx-background-radius: 10; -fx-font-size: 16px;");
        backButton.setOnMouseEntered(event -> backButton.setStyle("-fx-background-color: linear-gradient(to bottom, #C62828, #E57373); -fx-background-radius: 10; -fx-font-size: 16px;"));
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-background-color: linear-gradient(to bottom, #E57373, #C62828); -fx-background-radius: 10; -fx-font-size: 16px;"));

        backButton.setOnMouseClicked(event -> stage.setScene(new PlayerCountMenu().createPlayerCountScene(stage, game, withPlayer)));

        // Set the background and add all components
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(MainMenu.BACKGROUND_IMAGE, root);

        // Add the components to the root VBox
        root.getChildren().addAll(title, inputBox, startButton, backButton);

        return new Scene(stackPane, 1200, 675);
    }


}
