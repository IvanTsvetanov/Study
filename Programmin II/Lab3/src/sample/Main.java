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
import java.util.*;


public class Main extends Application {

    private static boolean lastDrawn = false;
    private static boolean proceed = true;

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


        //String is for X or O
        //The other map is for integers (coordinates)
        Map<String, HashMap<Integer, Integer>> clickedS = new HashMap<String, HashMap<Integer, Integer>>();

        HashMap<Integer, Integer> coordinates = new HashMap<Integer, Integer>();
        root.setOnMouseClicked(e -> {

            int xCoord = (int) (e.getX() / 100);
            int yCoord = (int) (e.getY() / 100);
            System.out.println(xCoord);
            System.out.println(yCoord);

            for(HashMap<Integer, Integer> coords : clickedS.values()) {
                System.out.println(coords.keySet());
                System.out.println(coords.values());
                if(coords.containsKey(xCoord) && coords.containsValue(yCoord)){
                    proceed = false;
                    System.out.println("Ima break");
                    break;
                }
                if(proceed == false) break;
            }

            if(proceed == true) {
                coordinates.put(xCoord, yCoord);
                System.out.println("Putting coords");


                //Check if it is an X or O
                if (lastDrawn == false) {
                    clickedS.put("X", coordinates);
                    drawAnX(gc, xCoord, yCoord);
                    System.out.println("X");
                } else {
                    clickedS.put("O", coordinates);
                    drawAnO(gc, xCoord, yCoord);
                    System.out.println("Y");
                }
            }
            proceed = true;
        });

        //Set the scene
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        gc.beginPath();
        gc.moveTo(100, 0);
        gc.lineTo(100, 300);
        gc.moveTo(200, 0);
        gc.lineTo(200, 300);
        gc.moveTo(0, 100);
        gc.lineTo(300, 100);
        gc.moveTo(0, 200);
        gc.lineTo(300, 200);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.stroke();
    }

    private void drawAnO(GraphicsContext gc, int x, int y) {
        int padding = 10;
        int width = 100;

        gc.setStroke(Color.RED);
        gc.strokeOval(padding + x * width, padding + y * width, 80, 80);
        lastDrawn = false;
    }

    private void drawAnX(GraphicsContext gc, int x, int y) {
        int padding = 10;
        int width = 100;
        int x1 = padding + x * width;
        int x2 = width * x + width - padding;
        int y1 = padding + y * width;
        int y2 = width * y + width - padding;

        gc.beginPath();
        gc.moveTo(x1, y1);
        gc.lineTo(x2, y2);
        gc.moveTo(x1, y2);
        gc.lineTo(x2, y1);
        gc.setStroke(Color.RED);
        gc.stroke();
        lastDrawn = true;
    }

}
