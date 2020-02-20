package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("MathDoku");

        GridPane mainPane = new GridPane();

        //region Setting up the Canvas
        //Create the canvas and add it to its pane
        Pane canvasPane = new GridPane();
        Canvas canvas = new Canvas(450, 450);
        canvasPane.getChildren().add(canvas);

        //Set the border of the canvas
        canvasPane.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 5;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: darkblue;");
        //endregion

        //region Adding components to the Main pane
        GridPane bottomComponents = new GridPane();
        bottomComponents.setPadding(new Insets(10, 10, 10, 5));

        //Add the bottom elements and format them
        Button undo = new Button("Undo");
        undo.setMinSize(100, 30);
        Button redo = new Button("Redo");
        redo.setMinSize(100, 30);
        Button clear = new Button("Clear");
        clear.setMinSize(100, 30);
        Button showMistakes = new Button("Show Mistakes");
        showMistakes.setMinSize(100, 30);
        Button solve = new Button("Solve");
        solve.setMinSize(100, 30);
        Label timerLabel = new Label("00:00");
        timerLabel.setMinSize(100, 30);
        timerLabel.setStyle("-fx-border-radius: 3;" +
                "-fx-border-color: brown;");
        timerLabel.setAlignment(Pos.CENTER);

        bottomComponents.add(undo, 0, 0, 1, 1);
        bottomComponents.add(redo, 0, 1, 1, 1);
        bottomComponents.add(clear, 1, 0, 1, 1);
        bottomComponents.add(showMistakes, 1, 1, 1, 1);
        bottomComponents.add(solve, 2, 0, 1, 1);
        bottomComponents.add(timerLabel, 2, 1, 1, 1);
        bottomComponents.setAlignment(Pos.TOP_LEFT);

        bottomComponents.setVgap(20);
        bottomComponents.setHgap(80);

        //Add the side elements and format them

        //endregion

        //Graphics Context & Drawing
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        //Adding to the Main Pane
        mainPane.add(canvasPane, 0, 0, 1, 1);
        mainPane.add(bottomComponents, 0, 1, 1, 1);

        //Set the scene and show it
        Scene scene = new Scene(mainPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
    }


    public static void main(String[] args) {
        launch(args);
    }
}
