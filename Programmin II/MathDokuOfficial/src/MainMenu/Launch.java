package MainMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
    Responsible for launching and managing the Main Menu.
 */

public class Launch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Main Menu");

        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(50);
        mainPane.setPadding(new Insets(10));

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.setPadding(new Insets(15, 0, 0,0));

        VBox exitBtnBox = new VBox();
        exitBtnBox.setAlignment(Pos.CENTER);

        //Create components to navigate through the Main Menu
        // & add them all to the main pane.

        //Create the Main Menu text
        Text titleField = new Text("MAIN MENU");
        titleField.setStyle("-fx-font: 50px Tahoma;\n" +
                "    -fx-fill: linear-gradient(from 0% 0% to 100% 150%, repeat, red 0%, green 50%);\n" +
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

        buttonBox.getChildren().addAll(btnLoadExample,
                btnLoadFromFile,
                btnLoadFromText);
        mainPane.add(buttonBox, 0, 1);

        exitBtnBox.getChildren().add(btnExit);
        mainPane.add(exitBtnBox, 0, 2);

        //Basic scene logic
        Scene mainScene = new Scene(mainPane, 300, 400);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
