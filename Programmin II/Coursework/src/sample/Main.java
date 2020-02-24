package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main extends Application {

    private ArrayList<Button> playButtons = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MathDoku");

        GridPane mainPane = new GridPane();
        mainPane.setGridLinesVisible(true);

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

        int minHeight = (int) (sideComponents.heightProperty().getValue() / 50);
        Button loadFile = new Button("Load Puzzle from File");
        loadFile.setMinSize(150, 30);
        loadFile.prefHeightProperty().bind(mainPane.heightProperty());
        loadFile.setMaxSize(150, 50);

        Button loadText = new Button("Load Puzzle from Text");
        loadText.setMinSize(150, 30);
        loadText.prefHeightProperty().bind(mainPane.heightProperty());
        loadText.setMaxSize(150, 50);

        Button changeFont = new Button("Change Font");
        changeFont.setMinSize(150, 30);
        changeFont.prefHeightProperty().bind(mainPane.heightProperty());
        changeFont.setMaxSize(150, 50);

        //Adding Dial Buttons

        //region Add Dial Buttons
        Button button1 = new Button("1");
        button1.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
        );
        button1.prefWidthProperty().bind(sideComponents.widthProperty());
        button1.prefHeightProperty().bind(sideComponents.heightProperty());


        Button button2 = new Button("2");
        button2.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
        );
        button2.prefWidthProperty().bind(sideComponents.widthProperty());
        button2.prefHeightProperty().bind(sideComponents.heightProperty());

        Button button3 = new Button("3");
        button3.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
        );
        button3.prefWidthProperty().bind(sideComponents.widthProperty());
        button3.prefHeightProperty().bind(sideComponents.heightProperty());

        Button button4 = new Button("4");
        button4.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
        );
        button4.prefWidthProperty().bind(sideComponents.widthProperty());
        button4.prefHeightProperty().bind(sideComponents.heightProperty());

        Button button5 = new Button("5");
        button5.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
        );
        button5.prefWidthProperty().bind(sideComponents.widthProperty());
        button5.prefHeightProperty().bind(sideComponents.heightProperty());

        Button button6 = new Button("6");
        button6.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
        );
        button6.prefWidthProperty().bind(sideComponents.widthProperty());
        button6.prefHeightProperty().bind(sideComponents.heightProperty());

        Button button7 = new Button("7");
        button7.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
        );
        button7.prefWidthProperty().bind(sideComponents.widthProperty());
        button7.prefHeightProperty().bind(sideComponents.heightProperty());

        Button button8 = new Button("8");
        button8.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
        );
        button8.prefWidthProperty().bind(sideComponents.widthProperty());
        button8.prefHeightProperty().bind(sideComponents.heightProperty());

        playButtons.add(button1);
        playButtons.add(button2);
        playButtons.add(button3);
        playButtons.add(button4);
        playButtons.add(button5);
        playButtons.add(button6);
        playButtons.add(button7);
        playButtons.add(button8);
        //endregion

        sideComponents.add(loadFile, 0, 0, 2, 1);
        sideComponents.add(loadText, 0, 1, 2, 1);
        sideComponents.add(changeFont, 0, 2, 2, 1);

        sideComponents.add(button1, 0, 3, 1, 1);
        sideComponents.add(button2, 1, 3, 1, 1);
        sideComponents.add(button3, 0, 4, 1, 1);
        sideComponents.add(button4, 1, 4, 1, 1);
        sideComponents.add(button5, 0, 5, 1, 1);
        sideComponents.add(button6, 1, 5, 1, 1);
        sideComponents.add(button7, 0, 6, 1, 1);
        sideComponents.add(button8, 1, 6, 1, 1);

        //Add randomly generate button
        HBox randHbox = new HBox();
        Button randGen = new Button("Randomly Generate Puzzle");
        randGen.setMinSize(130, 80);
        randHbox.getChildren().add(randGen);
        randHbox.setAlignment(Pos.CENTER);
        //endregion

        //region Draw Game Grid
        int size = 4;
        int width = 60;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //Add the rectangles
                Rectangle rec = new Rectangle(width, width);
                rec.setStroke(Color.BLACK);
                rec.setFill(Color.WHITE);

                //Add the text areas
                TextField text = new TextField("");
                text.setAlignment(Pos.CENTER);
                text.maxWidthProperty().bind(canvasPane.widthProperty().divide(size + 1));
                text.maxHeightProperty().bind(canvasPane.heightProperty().divide(size + 1));

                //Stacks the textfield on top of the rectangles
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(rec);
                stackPane.getChildren().add(text);

                canvasPane.add(stackPane, i, j, 1, 1);

                text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

                rec.widthProperty().bind(canvasPane.widthProperty().divide(size + 1));
                rec.heightProperty().bind(canvasPane.heightProperty().divide(size + 1));

                //region Handler for Playing Buttons
                text.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                    for(Button button : playButtons) {
                        button.setOnMouseClicked(f -> {
                            text.setText(button.getText());
                        });
                    }
                });
                //endregion
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
        randGen.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//USEFUL
/*
Button openNumPad = new Button("Open Number Pad");
        openNumPad.setMinSize(150, 50);
        openNumPad.prefHeightProperty().bind(mainPane.heightProperty());
        openNumPad.setMaxSize(150, 150);

 //region Add numPad button functionality
        openNumPad.setOnMouseClicked(e -> {
            GridPane keypadPane = new GridPane();
            keypadPane.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
            keypadPane.setHgap(50);
            keypadPane.setVgap(30);
            keypadPane.setPadding(new Insets(50));
            keypadPane.setAlignment(Pos.TOP_CENTER);

            //Add buttons to the window

            //region Creating Buttons
            Button button1 = new Button("1");
            button1.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 60px; " +
                            "-fx-min-height: 60px; " +
                            "-fx-max-width: 60px; " +
                            "-fx-max-height: 60px;"
            );

            Button button2 = new Button("2");
            button2.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 60px; " +
                            "-fx-min-height: 60px; " +
                            "-fx-max-width: 60px; " +
                            "-fx-max-height: 60px;"
            );

            Button button3 = new Button("3");
            button3.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 60px; " +
                            "-fx-min-height: 60px; " +
                            "-fx-max-width: 60px; " +
                            "-fx-max-height: 60px;"
            );

            Button button4 = new Button("4");
            button4.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 60px; " +
                            "-fx-min-height: 60px; " +
                            "-fx-max-width: 60px; " +
                            "-fx-max-height: 60px;"
            );

            Button button5 = new Button("5");
            button5.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 60px; " +
                            "-fx-min-height: 60px; " +
                            "-fx-max-width: 60px; " +
                            "-fx-max-height: 60px;"
            );

            Button button6 = new Button("6");
            button6.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 60px; " +
                            "-fx-min-height: 60px; " +
                            "-fx-max-width: 60px; " +
                            "-fx-max-height: 60px;"
            );

            Button button7 = new Button("7");
            button7.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 60px; " +
                            "-fx-min-height: 60px; " +
                            "-fx-max-width: 60px; " +
                            "-fx-max-height: 60px;"
            );

            Button button8 = new Button("8");
            button8.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 60px; " +
                            "-fx-min-height: 60px; " +
                            "-fx-max-width: 60px; " +
                            "-fx-max-height: 60px;"
            );
            //endregion

            keypadPane.add(button1, 0, 0, 1, 1);
            keypadPane.add(button2, 1, 0, 1, 1);
            keypadPane.add(button3, 0, 1, 1, 1);
            keypadPane.add(button4, 1, 1, 1, 1);
            keypadPane.add(button5, 0, 2, 1, 1);
            keypadPane.add(button6, 1, 2, 1, 1);
            keypadPane.add(button7, 0, 3, 1, 1);
            keypadPane.add(button8, 1, 3, 1, 1);

            buttons.add(button1);
            buttons.add(button2);
            buttons.add(button3);
            buttons.add(button4);
            buttons.add(button5);
            buttons.add(button6);
            buttons.add(button7);
            buttons.add(button8);

            // New window (Stage)
            Scene keypadScene = new Scene(keypadPane, 250, 430);
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.NONE);
            newWindow.setTitle("Keypad");
            newWindow.setScene(keypadScene);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + primaryStage.getWidth());
            newWindow.setY(primaryStage.getY());

            newWindow.show();
        });
        //endregion

 */


/*
for(Button button : buttons) {
                System.out.println("setting text " + button.getText());
                button.setOnMouseClicked(e ->{
                    if(text.isFocused())
                        text.setText(button.getText());

                });
            }
 */


                /*
                //Change the color of the background
                text.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        rec.setFill(Color.RED);
                        text.setStyle("-fx-background-color: red;");
                    }
                });*/