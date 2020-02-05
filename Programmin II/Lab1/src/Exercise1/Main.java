package Exercise1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Set the title
        primaryStage.setTitle("Simple Form");

        //Set the panes properties
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setPadding(new Insets(25, 25, 25, 25));

        //Create the components
        TextField textField = new TextField();
        textField.setPrefWidth(170);
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        //Add components to the panes
        mainPane.add(textField, 0, 0);
        buttonBox.getChildren().addAll(submitButton, cancelButton);
        mainPane.add(buttonBox, 0, 1);


        //Add the pane to the scene, scene to the stage and display
        Scene scene = new Scene(mainPane, 250, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
