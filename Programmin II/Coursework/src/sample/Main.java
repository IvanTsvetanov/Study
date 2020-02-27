package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
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

import java.util.*;

public class Main extends Application {

    private ArrayList<Button> playButtons = new ArrayList<>();
    private ArrayList<Text> targetValues = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MathDoku");

        GridPane mainPane = new GridPane();

        //region Setting up the Canvas
        //Create the canvas and add it to its pane
        GridPane canvasPane = new GridPane();
        canvasPane.prefWidthProperty().bind(mainPane.widthProperty());
        canvasPane.prefHeightProperty().bind(mainPane.heightProperty());
        canvasPane.setAlignment(Pos.CENTER);

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
        //endregion

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
        LogicFields logic = new LogicFields();
        int size = 4;
        int width = 60;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //Add the rectangles
                Rectangle rec = new Rectangle(width, width);
                rec.setStroke(Color.BLACK);
                rec.setFill(Color.WHITE);
                rec.setStrokeWidth(3);
                rec.setStrokeType(StrokeType.CENTERED);

                //Add the text areas
                TextField text = new TextField("");
                text.setAlignment(Pos.CENTER);
                text.maxWidthProperty().bind(canvasPane.widthProperty().divide(size + 1));
                text.maxHeightProperty().bind(canvasPane.heightProperty().divide(size + 1));
                logic.setGameField(text);

                //Adding the target values
                StackPane targetPane = new StackPane();
                Text target = new Text();
                target.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                targetPane.getChildren().add(target);
                targetPane.setMaxSize(10, 10);
                targetValues.add(target);

                //Stacks the textfield on top of the rectangles
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(rec);
                stackPane.setAlignment(Pos.TOP_LEFT);
                stackPane.getChildren().add(text);
                stackPane.getChildren().add(targetPane);

                canvasPane.add(stackPane, i, j, 1, 1);
                text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

                rec.widthProperty().bind(canvasPane.widthProperty().divide(size + 1));
                rec.heightProperty().bind(canvasPane.heightProperty().divide(size + 1));

                //region Handler for Playing Buttons
                text.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    for (Button button : playButtons) {
                        button.setOnMouseClicked(f -> {
                            text.setText(button.getText());
                        });
                    }
                });
                //endregion
            }
        }
        //endregion

        //region Set Styles
        canvasPane.setStyle("-fx-background-color: transparent, black;" +
                "-fx-background-insets:0, 5;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 0;" +
                "-fx-border-width: 35;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: brown;");
        //endregion

        //Adding to the Main Pane
        mainPane.add(canvasPane, 0, 0, 1, 1);
        mainPane.add(bottomComponents, 0, 1, 1, 1);
        mainPane.add(sideComponents, 1, 0, 1, 1);
        mainPane.add(randHbox, 1, 1, 1, 1);

        //region Game logic implementation
        //region Create the clusters // NEEDS REFACTORING FOR SURE
        //Add the fields to the clusters
        Cluster cluster1 = new Cluster();
        cluster1.addField(logic.getTextFields().get(0));
        cluster1.addField(logic.getTextFields().get(1));
        cluster1.addField(logic.getTextFields().get(2));

        Cluster cluster2 = new Cluster();
        cluster2.addField(logic.getTextFields().get(3));

        Cluster cluster3 = new Cluster();
        cluster3.addField(logic.getTextFields().get(4));
        cluster3.addField(logic.getTextFields().get(8));

        Cluster cluster4 = new Cluster();
        cluster4.addField(logic.getTextFields().get(5));
        cluster4.addField(logic.getTextFields().get(9));

        Cluster cluster5 = new Cluster();
        cluster5.addField(logic.getTextFields().get(6));

        Cluster cluster6 = new Cluster();
        cluster6.addField(logic.getTextFields().get(7));
        cluster6.addField(logic.getTextFields().get(11));

        Cluster cluster7 = new Cluster();
        cluster7.addField(logic.getTextFields().get(12));
        cluster7.addField(logic.getTextFields().get(13));

        Cluster cluster8 = new Cluster();
        cluster8.addField(logic.getTextFields().get(10));
        cluster8.addField(logic.getTextFields().get(14));
        cluster8.addField(logic.getTextFields().get(15));

        cluster1.setClusterColor();
        cluster2.setClusterColor();
        cluster3.setClusterColor();
        cluster4.setClusterColor();
        cluster5.setClusterColor();
        cluster6.setClusterColor();
        cluster7.setClusterColor();
        cluster8.setClusterColor();

        logic.addClusters(cluster1);
        logic.addClusters(cluster2);
        logic.addClusters(cluster3);
        logic.addClusters(cluster4);
        logic.addClusters(cluster5);
        logic.addClusters(cluster6);
        logic.addClusters(cluster7);
        logic.addClusters(cluster8);

        // TO GET THE TEXT BOX (TARGET VALUE) YOU JUST NEED THE INDEX OF ONE OF THE TEXTFIELDS
        //Target values
        targetValues.get(0).setText("8*");
        cluster1.setClusterTargetValue(targetValues.get(0).getText());

        targetValues.get(3).setText("3");
        cluster2.setClusterTargetValue(targetValues.get(3).getText());

        targetValues.get(4).setText("1-");
        cluster3.setClusterTargetValue(targetValues.get(4).getText());

        targetValues.get(5).setText("5+");
        cluster4.setClusterTargetValue(targetValues.get(5).getText());

        targetValues.get(6).setText("2");
        cluster5.setClusterTargetValue(targetValues.get(6).getText());

        targetValues.get(7).setText("2/");
        cluster6.setClusterTargetValue(targetValues.get(7).getText());

        targetValues.get(12).setText("5+");
        cluster7.setClusterTargetValue(targetValues.get(12).getText());

        targetValues.get(10).setText("8+");
        cluster8.setClusterTargetValue(targetValues.get(10).getText());
        //endregion

        //Run through all the text fields and add them to clusters
        /*for(int i = 0; i < logic.getTextFields().size(); i++) {
            logic.getTextFields().get(i).setText(Integer.toString(i));
        }*/

        //Update when a new value has been entered into a textfield
        for (TextField text : logic.getTextFields()) {
            text.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ArrayList<Boolean> isFinished = new ArrayList<>();
                    for (Cluster cluster : logic.getClusters()) {
                        isFinished.add(cluster.checkIfSolved());
                    }

                    for(boolean a : isFinished) {
                        System.out.print(a);
                    }
                    System.out.println();

                    if(!isFinished.contains(false)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "It took you *ADD TIME HERE*");

                        alert.setTitle("You have won!");
                        alert.setHeaderText("!CONGRATULATIONS!");

                        alert.showAndWait();

                    }

                }
            });

        }
        //endregion

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
        //Set the border of the canvas
        /*canvasPane.setStyle("-fx-padding: 0;" +
                "-fx-border-width: 10;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 50;" +
                "-fx-border-color: black;" +
                "-fx-background-color: darkblue");

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