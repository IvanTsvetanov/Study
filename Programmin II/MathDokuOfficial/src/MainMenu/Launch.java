package MainMenu;

import Puzzles.ExamplePuzzle;
import Puzzles.LoadFromFile;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

import static HelperClasses.Toolbox.timer;

/*
    Responsible for launching and managing the Main Menu.
 */

public class Launch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Main Menu");
        //Set the icon for the window.
        primaryStage.getIcons().add(new Image("file:src\\Images\\icon.png"));

        //Create the panes to build up the Main Menu.
        //The main pane - holds the structure of the whole window scene.
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(50);
        mainPane.setPadding(new Insets(10));
        mainPane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 150%, repeat, mediumpurple 0%, gray 50%);");

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));

        VBox exitBtnBox = new VBox();
        exitBtnBox.setAlignment(Pos.CENTER);

        //Create components to navigate through the Main Menu
        // & add them all to the main pane.

        //Create the Main Menu text
        Text titleField = new Text("MAIN MENU");
        titleField.setStyle("-fx-font: 50px Tahoma;\n" +
                "    -fx-fill: linear-gradient(from 0% 0% to 100% 150%, repeat, beige 20%, beige 50%);\n" +
                "    -fx-stroke: black;\n" +
                "    -fx-stroke-width: 1;");
        mainPane.add(titleField, 0, 0);

        //Create the buttons to navigate the menu
        Button btnLoadExample = new Button("Load Example Puzzle");
        btnLoadExample.setMinSize(200, 40);

        Button btnLoadFromFile = new Button("Load from File");
        btnLoadFromFile.setMinSize(200, 40);

        Button btnLoadFromText = new Button("Load from Text");
        btnLoadFromText.setMinSize(200, 40);

        Button btnExit = new Button("Exit");
        btnExit.setMinSize(200, 40);
        //Handler for exiting the Main Menu
        btnExit.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to exit?");

            alert.setTitle("Exit confirmation");
            alert.setHeaderText("Confirmation");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage stage = (Stage) btnExit.getScene().getWindow();
                stage.close();
            }
            timer.cancel();
        });

        buttonBox.getChildren().addAll(btnLoadExample,
                btnLoadFromFile,
                btnLoadFromText);
        mainPane.add(buttonBox, 0, 1);

        exitBtnBox.getChildren().add(btnExit);
        mainPane.add(exitBtnBox, 0, 2);

        //Change the scene to the example puzzle scene.
        btnLoadExample.setOnMouseClicked(e -> {
            ExamplePuzzle examplePuzzle = new ExamplePuzzle();
            primaryStage.setScene(examplePuzzle.buildExampleScene());
            primaryStage.setMinWidth(720);
            primaryStage.setMinHeight(670);
            //Automatically stop the timer when exiting.
            primaryStage.setOnCloseRequest(event -> {
                timer.cancel();
            });
        });

        //Change the scene to the puzzle loaded from file scene.
        btnLoadFromFile.setOnMouseClicked(e -> {
            LoadFromFile loadFromFile = new LoadFromFile();
            //Open the file chooser so the user can select a file from which to import the puzzle.
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File workFile = fileChooser.showOpenDialog(primaryStage);
            String path = workFile.getAbsolutePath();

            //Change the scene to the selected puzzle.
            primaryStage.setScene(loadFromFile.buildSceneFromFile(path));
            primaryStage.setMinWidth(720);
            primaryStage.setMinHeight(670);
            //Automatically stop the timer when exiting.
            primaryStage.setOnCloseRequest(event -> {
                timer.cancel();
            });
        });

        //Basic scene logic
        Scene mainScene = new Scene(mainPane, 300, 450);
        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(450);
        mainPane.requestFocus();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
