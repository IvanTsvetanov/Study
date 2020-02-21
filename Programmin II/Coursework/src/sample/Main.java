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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("MathDoku");

        GridPane mainPane = new GridPane();

        //region Setting up the Canvas
        //Create the canvas and add it to its pane
        GridPane canvasPane = new GridPane();
        canvasPane.prefWidthProperty().bind(mainPane.widthProperty());
        canvasPane.prefHeightProperty().bind(mainPane.heightProperty());
        canvasPane.setAlignment(Pos.CENTER);


        //Set the border of the canvas
        canvasPane.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 5;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: darkblue;");
        //endregion

        //region Creating components to add to the Main Frame.
        GridPane bottomComponents = new GridPane();
        bottomComponents.setPadding(new Insets(10, 10, 10, 5));

        //Add the bottom elements and format them
        Button undo = new Button("Undo");
        undo.setMinSize(100, 30);
        undo.prefWidthProperty().bind(mainPane.widthProperty());
        undo.setMaxSize(250, 60);

        Button redo = new Button("Redo");
        redo.setMinSize(100, 30);
        redo.prefWidthProperty().bind(mainPane.widthProperty());
        redo.setMaxSize(250, 60);

        Button clear = new Button("Clear");
        clear.setMinSize(100, 30);
        clear.prefWidthProperty().bind(mainPane.widthProperty());
        clear.setMaxSize(250, 60);

        Button showMistakes = new Button("Show Mistakes");
        showMistakes.setMinSize(100, 30);
        showMistakes.prefWidthProperty().bind(mainPane.widthProperty());
        showMistakes.setMaxSize(250, 60);

        Button solve = new Button("Solve");
        solve.setMinSize(100, 30);
        solve.prefWidthProperty().bind(mainPane.widthProperty());
        solve.setMaxSize(250, 60);

        Label timerLabel = new Label("00:00");
        timerLabel.setMinSize(100, 30);
        timerLabel.prefWidthProperty().bind(mainPane.widthProperty());
        timerLabel.prefHeightProperty().bind(mainPane.heightProperty());
        timerLabel.setMaxSize(250, 60);
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
        GridPane sideComponents = new GridPane();

        sideComponents.setPadding(new Insets(10, 10, 10, 10));
        sideComponents.setVgap(20);
        sideComponents.setHgap(20);

        int minHeight = (int)(sideComponents.heightProperty().getValue()/50);
        Button loadFile = new Button ("Load Puzzle from File");
        loadFile.setMinSize(150, 50);
        loadFile.prefHeightProperty().bind(mainPane.heightProperty());
        loadFile.setMaxSize(150, 150);

        Button loadText = new Button ("Load Puzzle from Text");
        loadText.setMinSize(150, 50);
        loadText.prefHeightProperty().bind(mainPane.heightProperty());
        loadText.setMaxSize(150, 150);

        Button changeFont = new Button("Change Font");
        changeFont.setMinSize(150, 50);
        changeFont.prefHeightProperty().bind(mainPane.heightProperty());
        changeFont.setMaxSize(150, 150);

        sideComponents.add(loadFile, 0, 0, 1, 1);
        sideComponents.add(loadText, 0, 1, 1, 1);
        sideComponents.add(changeFont, 0, 2, 1 ,1 );

        //Add randomly generate button
        HBox randHbox = new HBox();
        Button randGen = new Button("Randomly Generate Puzzle");
        randGen.setMinSize(130, 80);
        randHbox.getChildren().add(randGen);
        randHbox.setAlignment(Pos.CENTER);
        //endregion

        //region Draw Game Grid
        int size = 3;
        int width = 60;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Rectangle rec = new Rectangle(width, width);
                rec.setStroke(Color.BLACK);
                rec.setFill(Color.WHITE);
                rec.relocate(i * width, j * width);
                canvasPane.add(rec, i, j, 1, 1);
                rec.widthProperty().bind(canvasPane.widthProperty().divide(size + 1));
                rec.heightProperty().bind(canvasPane.heightProperty().divide(size + 1));
            }
        }
        //endregion

        //Adding to the Main Pane
        mainPane.add(canvasPane, 0, 0, 1, 1);
        mainPane.add(bottomComponents, 0, 1, 1, 1);
        mainPane.add(sideComponents, 1, 0, 1, 1);
        mainPane.add(randHbox, 1, 1, 1, 1);

        //Set the scene and show it
        Scene scene = new Scene(mainPane, 670, 600);
        primaryStage.setMinWidth(720);
        primaryStage.setMinHeight(670);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
    }


    public static void main(String[] args) {
        launch(args);
    }
}
