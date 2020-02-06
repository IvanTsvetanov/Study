package Exercise2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SecondMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set the title
        primaryStage.setTitle("My Function Calculator");

        //Create panes
        GridPane mainPane = new GridPane();
        HBox startingValues = new HBox();
        //Remove later
        mainPane.setGridLinesVisible(true);


        //Create components
        Label x = new Label("x=");
        TextField startingValue = new TextField("1");

        //Add components
        startingValues.getChildren().addAll(x, startingValues);

        mainPane.add(startingValues, 0 ,0);

        //Display
        Scene scene = new Scene(mainPane, 500, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
