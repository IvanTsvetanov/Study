package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Main extends Application {

    static boolean lastDrawn = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing Operations Test");

        //Create the group object
        Group root = new Group();

        //Create the canvas
        Canvas canvas = new Canvas(300, 300);

        //Create the graphics context
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Call the method
        drawShapes(gc);

        //Add the canvas
        root.getChildren().add(canvas);

        //Add mouse listener event
        //Create a tracker that holds all the possible win situations and displays if the player has won
        //Has all the places where X or O has been put

        //Creating an array of booleans to store whether the box has been clicked in or not;
        List<Boolean> hasClicked = new ArrayList<Boolean>(Arrays.asList(new Boolean[10]));
        Collections.fill(hasClicked, Boolean.FALSE);

        root.setOnMouseClicked(e -> {
            //First Box
            if (e.getSceneX() >= 20 && e.getSceneY() >= 20 &&
                    e.getSceneX() <= 90 && e.getSceneY() <= 90) {
                //Check if it has been clicked already
                if (hasClicked.get(0) == Boolean.FALSE) {
                    //Check if it is an X or O
                    if (lastDrawn == false) {
                        drawAnX(gc, 20, 20);
                    } else {
                        drawAnO(gc, 20, 20);
                    }
                    hasClicked.set(0, Boolean.TRUE);
                }
            }

            //Second Box
            else if (e.getSceneX() >= 110 && e.getSceneY() >= 20 &&
                    e.getSceneX() <= 190 && e.getSceneY() <= 190) {
                if (hasClicked.get(1) == Boolean.FALSE) {
                    if (lastDrawn == false) {
                        drawAnX(gc, 115, 20);
                    }
                    else {
                        drawAnO(gc, 115, 20);
                    }
                    hasClicked.set(1, Boolean.TRUE);
                }
            }

        });

        //Set the scene
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        gc.beginPath();
        gc.moveTo(100, 20);
        gc.lineTo(100, 280);
        gc.moveTo(200, 20);
        gc.lineTo(200, 280);
        gc.moveTo(20, 100);
        gc.lineTo(280, 100);
        gc.moveTo(20, 200);
        gc.lineTo(280, 200);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.stroke();
    }

    private void drawAnO(GraphicsContext gc, double x, double y) {
        gc.strokeOval(x, y, 3, 4);
        lastDrawn = false;
    }

    private void drawAnX(GraphicsContext gc, double x, double y) {
        gc.beginPath();
        gc.moveTo(x, y);
        gc.lineTo(x + 70, y + 70);
        gc.moveTo(x + 70, y);
        gc.lineTo(x, y + 70);
        gc.setStroke(Color.RED);
        gc.stroke();
        lastDrawn = true;
    }

}
