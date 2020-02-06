package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Set the title
        primaryStage.setTitle("My Function Calculator");

        //Create panes
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER_LEFT);
        mainPane.setHgap(20);
        mainPane.setVgap(20);
        mainPane.setPadding(new Insets(20, 20, 20, 20));

        HBox startingValues = new HBox();
        startingValues.setSpacing(15);
        startingValues.setAlignment(Pos.CENTER);

        VBox plusMinusOne = new VBox();

        VBox functions = new VBox();
        functions.setSpacing(5);
        functions.setAlignment(Pos.CENTER);

        HBox endValues = new HBox();
        endValues.setSpacing(15);
        endValues.setAlignment(Pos.CENTER);

        //Remove later
        //mainPane.setGridLinesVisible(true);

        //Create components
        //In startingValues (HBox)
        Label x = new Label("x=");
        x.setMinWidth(15);
        TextField startingValue = new TextField("1");
        startingValue.prefWidthProperty().bind(mainPane.widthProperty());

        //In plusMinusOne (VBox)
        Button increment = new Button ("+1");
        increment.setMinSize(32.4, 32.4);
        Button decrement = new Button ("-1");
        decrement.setMinSize(32.4, 32.4);

        //Function text
        Label function = new Label ("Function: ");
        function.setMinWidth(60);
        function.setAlignment(Pos.CENTER);

        //In functions (VBox)
        RadioButton xtimesX = new RadioButton("x*x");
        xtimesX.setMinWidth(100);
        RadioButton sinX = new RadioButton("sin(x)");
        sinX.setMinWidth(100);
        RadioButton cosX = new RadioButton("cos(x)");
        cosX.setMinWidth(100);
        RadioButton fibbX = new RadioButton("Fibbonacci(x)");
        fibbX.setMinWidth(100);

        xtimesX.setSelected(true);

        ToggleGroup toggle = new ToggleGroup();
        xtimesX.setToggleGroup(toggle);
        sinX.setToggleGroup(toggle);
        cosX.setToggleGroup(toggle);
        fibbX.setToggleGroup(toggle);

        //In endValues (HBox)
        Label equalsSign = new Label("=");
        equalsSign.setMinWidth(15);
        TextField endValue = new TextField("1");
        endValue.prefWidthProperty().bind(mainPane.widthProperty());

        //Add components
        startingValues.getChildren().add(x);
        startingValues.getChildren().add(startingValue);
        plusMinusOne.getChildren().add(increment);
        plusMinusOne.getChildren().add(decrement);
        functions.getChildren().add(xtimesX);
        functions.getChildren().add(sinX);
        functions.getChildren().add(cosX);
        functions.getChildren().add(fibbX);
        endValues.getChildren().add(equalsSign);
        endValues.getChildren().add(endValue);

        mainPane.add(startingValues, 0 ,2, 1 ,1);
        mainPane.add(plusMinusOne, 1, 1, 1, 3);
        mainPane.add(function, 2, 2, 1, 1);
        mainPane.add(functions, 3, 0, 1, 5);
        mainPane.add(endValues,4, 2, 1, 1);

        //Display
        Scene scene = new Scene(mainPane, 650, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
