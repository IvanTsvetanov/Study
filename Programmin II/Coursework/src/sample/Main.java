package sample;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.IntStream;

public class Main extends Application {

    //region Variables
    private ArrayList<Button> playButtons = new ArrayList<>();
    private ArrayList<Text> targetValues = new ArrayList<>();
    private int size = 4;
    private int[][] valueHolder = new int[size][size];
    private TextField[][] textFieldHolder = new TextField[size][size];
    private boolean isComplete = true;
    private ArrayList<Integer> fieldNumbers = new ArrayList<>();
    private boolean invalidFieldInput = false;
    int secondsPassed = 0;
    Timer timer = new Timer();
    TextField time = new TextField("0");
    boolean flagMistakes = false;
    static int whereAreWe = 0;
    //endregion

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
        //endregion

        //Add randomly generate button
        HBox randHbox = new HBox();
        Button randGen = new Button("Randomly Generate Puzzle");
        randGen.setMinSize(130, 80);
        randHbox.getChildren().add(randGen);
        randHbox.setAlignment(Pos.CENTER);
        //endregion

        //region Draw Game Grid
        LogicFields logic = new LogicFields();
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
                TextField text = new TextField();
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

                //region Get easier access to rows and cols
                textFieldHolder[j][i] = text;
                final int row = i;
                final int col = j;
                text.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        boolean numeric = true;
                        numeric = text.getText().matches("-?\\d+(\\.\\d+)?");

                        if (text.getText() == null || text.getText().isEmpty() || text.getText() == "" || numeric == false)
                            valueHolder[col][row] = 0;

                        else {
                            valueHolder[col][row] = Integer.valueOf(text.getText());
                        }
                    }
                });
                //endregion

                //region Handler for Playing Buttons
                text.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    //Clear the text box when it has been clicked
                    text.setText("");
                    for (Button button : playButtons) {
                        button.setOnMouseClicked(f -> {
                            text.setText(button.getText());

                            //Check for valid input. The input depends on the size of the board.
                            if (button.getText() != null)
                                if (Integer.valueOf(text.getText()) > size) {
                                    text.setText("");
                                    new Alert(Alert.AlertType.ERROR,
                                            "You cant enter that big of a number!")
                                            .showAndWait();
                                }
                        });
                    }
                });
                //endregion
            }
        }
        //endregion

        //region Set Style of Game Grid
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

        //region Win Condition & Proper Keyboard Input.
        //Update when a new value has been entered into a textfield
        for (TextField text : logic.getTextFields()) {
            text.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    //Check for valid entry.
                    Integer enteredNumber = 0;

                    //Check if it is a numeric entry
                    boolean numeric = true;
                    numeric = text.getText().matches("-?\\d+(\\.\\d+)?");

                    //If it is a number, and it is in the range in the size,
                    //we keep the change to the text field.
                    if (numeric) {
                        enteredNumber = tryParse(text.getText());
                        if (enteredNumber > 0 && enteredNumber <= size) {
                            text.setText(enteredNumber.toString());
                        } else {
                            text.setText("");
                            new Alert(Alert.AlertType.ERROR,
                                    "Invalid input! You cannot enter that big of a number!")
                                    .showAndWait();
                        }
                    } else {
                        text.setText("");
                    }

                    //Check for win condition
                    ArrayList<Boolean> isFinished = new ArrayList<>();
                    for (Cluster cluster : logic.getClusters()) {
                        isFinished.add(cluster.checkIfSolved());
                    }

                    //Check the cols
                    isComplete = true;
                    int[] col;
                    for (int i = 0; i < size; i++) {
                        col = getColumn(valueHolder, i);
                        //Check if the col contains zeros (i.e. not fully populated)
                        if (duplicates(col) == true) {
                            isComplete = false;
                            break;
                        }
                    }

                    //Check the rows
                    int[] row;
                    for (int i = 0; i < size; i++) {
                        row = getRow(valueHolder, i);
                        if (duplicates(row) == true) {
                            isComplete = false;
                            break;
                        }
                    }

                    if (!isFinished.contains(false) && isComplete == true) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "It took you *ADD TIME HERE*");

                        alert.setTitle("You have won!");
                        alert.setHeaderText("CONGRATULATIONS!");

                        //Win animation
                        alert.showAndWait();
                        Text finishText = new Text("GREAT JOB!");
                        finishText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                        finishText.setStyle(" -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);" +
                                "    -fx-stroke: black;" +
                                "    -fx-stroke-width: 1;");
                        FlowPane textPane = new FlowPane();
                        textPane.setAlignment(Pos.CENTER);
                        textPane.getChildren().add(finishText);
                        ScaleTransition st = new ScaleTransition(Duration.millis(2000));
                        st.setByX(1.5f);
                        st.setByY(1.5f);
                        st.setCycleCount(Integer.MAX_VALUE);
                        st.setAutoReverse(true);
                        ParallelTransition pt1 = new ParallelTransition(finishText, st);
                        pt1.play();
                        mainPane.add(textPane, 0, 0, 1, 1);
                        mainPane.setOnMouseClicked(l -> {
                            mainPane.getChildren().remove(textPane);
                        });
                    }
                }
            });
        }
        //endregion

        //region Button Show Mistakes
        showMistakes.setOnMousePressed(e -> {
            //If its off - turn it on, flash mistakes, and change color of target value
            if (flagMistakes == false) {
                //Check the cols
                int[] col;
                for (int i = 0; i < size; i++) {
                    col = getColumn(valueHolder, i);
                    //Check if the col contains zeros (i.e. not fully populated)
                    if (duplicates(col) == true) {
                        for (int j = 0; j < size; j++) {
                            textFieldHolder[j][i].setStyle("-fx-background-color: red;");
                        }
                    }
                }

                //Check the rows
                int[] row;
                for (int i = 0; i < size; i++) {
                    row = getRow(valueHolder, i);
                    if (duplicates(row) == true) {
                        for (int j = 0; j < size; j++) {
                            textFieldHolder[i][j].setStyle("-fx-background-color: red;");
                        }
                    }
                }

                //Check the clusters
                for (int z = 0; z < logic.getClusters().size(); z++) {
                    if (logic.getClusters().get(z).checkIfSolved() == false) {
                        for (int i = 0; i < logic.getClusters().get(z).getCluster().size(); i++) {
                            logic.getClusters().get(z).getCluster().get(i).setStyle("-fx-background-color: red;");
                        }
                    }
                }
            }
        });
        //Return to the normal colors
        showMistakes.setOnMouseReleased(e -> {
            sleepForOneSecond();
            for (int z = 0; z < logic.getClusters().size(); z++) {
                logic.getClusters().get(z).setClusterColorNotRandom(logic.getClusters().get(z).getClusterColor());
            }
        });

        //endregion

        //region Button Clear Game Grid
        clear.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to clear the board?");

            alert.setTitle("Clear confirmation");
            alert.setHeaderText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (int i = 0; i < logic.getTextFields().size(); i++) {
                    logic.getTextFields().get(i).setText("");
                }
            }
        });
        //endreion
        //endregion

        //region Button Undo
        //Arraylists to hold values and textfields.
        ArrayList<TextField> textFieldArrayList = new ArrayList<>();
        ArrayList<String> textFieldArrayListValues = new ArrayList<>();
        ArrayList<TextField> textFieldsRedo = new ArrayList<>();
        ArrayList<String> textFieldsValuesRedo = new ArrayList<>();

        undo.setDisable(true);
        redo.setDisable(true);

        //Listener to update the textfields.
        for (TextField text : logic.getTextFields()) {
            text.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!text.getText().equals("") && !text.equals(null)) {
                        if (textFieldArrayListValues.size() < 2) {
                            textFieldArrayList.add(text);
                            textFieldArrayListValues.add(text.getText());
                        }
                        //Takes care of adding values to the same textfield
                        else if (textFieldArrayListValues.size() >= 2) {
                            if (!textFieldArrayListValues.get((textFieldArrayListValues.size() - 1)).equals(text.getText())) {
                                textFieldArrayList.add(text);
                                textFieldArrayListValues.add(text.getText());
                            }
                        }
                    }
                    //Disable button when there is nothing to undo.
                    if (textFieldArrayListValues.size() == 0)
                        undo.setDisable(true);
                    else undo.setDisable(false);

                    if (textFieldsValuesRedo.size() == 0)
                        redo.setDisable(true);
                    else redo.setDisable(false);
                }
            });
        }

        undo.setOnMouseClicked(e -> {
            //Remove the text from the last element.
            textFieldArrayList.get((textFieldArrayList.size() - 1)).setText("");

            //Remove the last element. (Add redo values)
            textFieldsRedo.add(textFieldArrayList.get(textFieldArrayList.size() - 1));
            textFieldArrayList.remove(textFieldArrayList.size() - 1);
            textFieldsValuesRedo.add(textFieldArrayListValues.get(textFieldArrayListValues.size() - 1));
            textFieldArrayListValues.remove(textFieldArrayListValues.size() - 1);

            if (textFieldsValuesRedo.size() == 0)
                redo.setDisable(true);
            else redo.setDisable(false);

            if (textFieldArrayListValues.size() == 0)
                undo.setDisable(true);
            else undo.setDisable(false);

            //Set the new text.
            if (textFieldArrayList.size() > 0)
                textFieldArrayList.get(textFieldArrayList.size() - 1).setText(textFieldArrayListValues.get((textFieldArrayListValues.size() - 1)));
        });
        //endregion

        //region Button Redo
        redo.setOnMouseClicked(e -> {
            textFieldsRedo.get(textFieldsRedo.size() - 1).setText(textFieldsValuesRedo.get((textFieldsValuesRedo.size() - 1)));
            textFieldsRedo.remove(textFieldsRedo.size() - 1);
            textFieldsValuesRedo.remove(textFieldsValuesRedo.size() - 1);
            if (textFieldsValuesRedo.size() == 0)
                redo.setDisable(true);
            else redo.setDisable(false);
        });
        //endregion

        //region Button Change Font
        //Open a new window to select font sizes of the game
        changeFont.setOnMouseClicked(e ->

        {
            //Create the components of the scene.
            VBox fontBox = new VBox();
            fontBox.setAlignment(Pos.CENTER);
            fontBox.setSpacing(30);
            Text selectFontSize = new Text("Select font size");
            selectFontSize.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            fontBox.getChildren().add(selectFontSize);
            Button smallB = new Button("Small");
            smallB.setMinSize(140, 40);
            Button mediumB = new Button("Medium");
            mediumB.setMinSize(140, 40);
            Button largeB = new Button("Large");
            largeB.setMinSize(140, 40);
            fontBox.getChildren().addAll(smallB, mediumB, largeB);

            //Handlers for changing fonts.
            smallB.setOnMouseClicked(a -> {
                for (TextField text : logic.getTextFields()) {
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                }
                for (Text target : targetValues) {
                    target.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                }
                Window window = ((Node) (a.getSource())).getScene().getWindow();
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            });

            mediumB.setOnMouseClicked(a -> {
                for (TextField text : logic.getTextFields()) {
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
                }
                for (Text target : targetValues) {
                    target.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                }
                Window window = ((Node) (a.getSource())).getScene().getWindow();
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            });

            largeB.setOnMouseClicked(a -> {
                for (TextField text : logic.getTextFields()) {
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 33));
                }
                for (Text target : targetValues) {
                    target.setFont(Font.font("Verdana", FontWeight.BOLD, 23));
                }
                Window window = ((Node) (a.getSource())).getScene().getWindow();
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            });

            //Create the scene and show it.
            Scene inputScene = new Scene(fontBox, 225, 250);
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.NONE);
            newWindow.setTitle("Font");
            newWindow.setScene(inputScene);
            newWindow.setResizable(false);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + primaryStage.getWidth());
            newWindow.setY(primaryStage.getY());

            newWindow.show();
        });
        //endregion

        //region Button Load from File
        loadFile.setOnMouseClicked(e ->

        {
            //region Get the Game Ready
            //Open the text file.
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File workFile = fileChooser.showOpenDialog(primaryStage);
            String path = workFile.getAbsolutePath();

            //Clear the game logic from the loaded by default example.
            logic.getClusters().clear();
            logic.getTextFields().clear();
            for (Text text : targetValues) {
                text.setText("");
            }
            Cluster.clusterColorPointer = 0;
            update();

            //See how big is the grid (NxN). Find the max number of a textfield.
            try (Scanner scanner = new Scanner(new File(path))) {
                //Work through every line in the given text file to create the game grid. (DUPLICATE CODE BELOW).
                while (scanner.hasNext()) {
                    //Split the input line into target value and textfields.
                    String line = scanner.nextLine();
                    String[] valueAndFields = line.split(" ", 2);
                    String targetValue = valueAndFields[0];
                    String textFields = valueAndFields[1];
                    //Get the max value.
                    String[] textField = textFields.split(",");
                    int[] array = Arrays.asList(textField).stream().mapToInt(Integer::parseInt).toArray();
                    int max = getMax(array);
                    if (size < max) size = max;
                }
            } catch (Exception f) {
                f.printStackTrace();
            }
            size = (int) Math.sqrt(Double.valueOf(size));

            //Initiate the holders with the new size.
            textFieldHolder = new TextField[size][size];
            valueHolder = new int[size][size];
            targetValues.clear();

            //endregion

            //region Draw the game grid anew
            canvasPane.getChildren().clear();
            for (int j = 0; j < size; j++) {
                for (int i = 0; i < size; i++) {
                    //Add the rectangles
                    Rectangle rec = new Rectangle(60, 60);
                    rec.setStroke(Color.BLACK);
                    rec.setFill(Color.WHITE);
                    rec.setStrokeWidth(3);
                    rec.setStrokeType(StrokeType.CENTERED);
                    rec.widthProperty().bind(canvasPane.widthProperty().divide(size + 2));
                    rec.heightProperty().bind(canvasPane.heightProperty().divide(size + 2));

                    //Add the text areas
                    TextField text = new TextField();
                    text.setAlignment(Pos.CENTER);
                    text.maxWidthProperty().bind(canvasPane.widthProperty().divide(size + 2));
                    text.maxHeightProperty().bind(canvasPane.heightProperty().divide(size + 2));
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

                    //region Get easier access to rows and cols
                    textFieldHolder[j][i] = text;
                    final int row = i;
                    final int col = j;
                    text.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            boolean numeric = true;
                            numeric = text.getText().matches("-?\\d+(\\.\\d+)?");

                            if (text.getText() == null || text.getText().isEmpty() || text.getText() == "" || numeric == false)
                                valueHolder[col][row] = 0;

                            else {
                                valueHolder[col][row] = Integer.valueOf(text.getText());
                            }
                        }
                    });
                    //endregion

                    //region Handler for Playing Buttons
                    text.addEventHandler(MouseEvent.MOUSE_CLICKED, a -> {
                        //Clear the text box when it has been clicked
                        text.setText("");
                        for (Button button : playButtons) {
                            button.setOnMouseClicked(f -> {
                                text.setText(button.getText());

                                //Check for valid input. The input depends on the size of the board.
                                if (button.getText() != null)
                                    if (Integer.valueOf(text.getText()) > size) {
                                        text.setText("");
                                        new Alert(Alert.AlertType.ERROR,
                                                "You cant enter that big of a number!")
                                                .showAndWait();
                                    }
                            });
                        }
                    });
                    //endregion
                }
            }
            //endregion

            //region Check for proper keyboard input & Win condition
            for (TextField text : logic.getTextFields()) {
                text.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        //Check for valid entry.
                        Integer enteredNumber = 0;

                        //Check if it is a numeric entry
                        boolean numeric = true;
                        numeric = text.getText().matches("-?\\d+(\\.\\d+)?");

                        //If it is a number, and it is in the range in the size,
                        //we keep the change to the text field.
                        if (numeric) {
                            enteredNumber = tryParse(text.getText());
                            if (enteredNumber > 0 && enteredNumber <= size) {
                                text.setText(enteredNumber.toString());
                            } else {
                                text.setText("");
                                new Alert(Alert.AlertType.ERROR,
                                        "Invalid input! You cannot enter that big of a number!")
                                        .showAndWait();
                            }
                        } else {
                            text.setText("");
                        }

                        //Win condition checking.
                        ArrayList<Boolean> isFinished = new ArrayList<>();
                        for (Cluster cluster : logic.getClusters()) {
                            isFinished.add(cluster.checkIfSolved());
                        }

                        //Check the cols
                        isComplete = true;
                        int[] col;
                        for (int i = 0; i < size; i++) {
                            col = getColumn(valueHolder, i);
                            //Check if the col contains zeros (i.e. not fully populated)
                            if (duplicates(col) == true) {
                                isComplete = false;
                                break;
                            }
                        }

                        //Check the rows
                        int[] row;
                        for (int i = 0; i < size; i++) {
                            row = getRow(valueHolder, i);
                            if (duplicates(row) == true) {
                                isComplete = false;
                                break;
                            }
                        }

                        if (!isFinished.contains(false) && isComplete == true) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                    "It took you *ADD TIME HERE*");

                            alert.setTitle("You have won!");
                            alert.setHeaderText("!CONGRATULATIONS!");

                            alert.showAndWait();

                            //Win animation
                            alert.showAndWait();
                            Text finishText = new Text("GREAT JOB!");
                            finishText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                            finishText.setStyle(" -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);" +
                                    "    -fx-stroke: black;" +
                                    "    -fx-stroke-width: 1;");
                            FlowPane textPane = new FlowPane();
                            textPane.setAlignment(Pos.CENTER);
                            textPane.getChildren().add(finishText);
                            ScaleTransition st = new ScaleTransition(Duration.millis(2000));
                            st.setByX(1.5f);
                            st.setByY(1.5f);
                            st.setCycleCount(Integer.MAX_VALUE);
                            st.setAutoReverse(true);
                            ParallelTransition pt1 = new ParallelTransition(finishText, st);
                            pt1.play();
                            mainPane.add(textPane, 0, 0, 1, 1);
                            mainPane.setOnMouseClicked(l -> {
                                mainPane.getChildren().remove(textPane);
                            });
                        }
                    }
                });
            }
            //endregion

            //region Get the game info from the file.
            fieldNumbers.clear();
            invalidFieldInput = false;
            try (Scanner scanner = new Scanner(new File(path))) {
                //Work through every line in the given text file to create the game grid.
                while (scanner.hasNext()) {
                    //For every line we create a cluster.
                    Cluster cluster = new Cluster();
                    logic.getClusters().add(cluster);

                    String line = scanner.nextLine();
                    String[] valueAndFields = line.split(" ", 2);
                    String targetValue = valueAndFields[0];
                    String textFields = valueAndFields[1];

                    //Create the text fields and add them to their cluster.
                    ArrayList<Integer> fieldChecker = new ArrayList<>();
                    String[] textField = textFields.split(",");
                    for (String text : textField) {
                        cluster.addField(logic.getTextFields().get(Integer.valueOf(text) - 1));
                        //Add the numbers to check for valid input
                        fieldNumbers.add(Integer.valueOf(text));
                        fieldChecker.add(Integer.valueOf(text));
                    }
                    //Check for adjacent cells in a cluster.
                    Collections.sort(fieldChecker);
                    for (int i = 0; i < fieldChecker.size(); i++) {
                        if (fieldChecker.size() < 3)
                            if (i < fieldChecker.size() - 1) {
                                if (fieldChecker.get(i) + 1 != fieldChecker.get(i + 1) &&
                                        fieldChecker.get(i) + size != fieldChecker.get(i + 1)) {
                                    invalidFieldInput = true;
                                }
                            } else {
                                if (i < fieldChecker.size() - 1 && i > 0) {
                                    if (fieldChecker.get(i) + 1 != fieldChecker.get(i + 1) &&
                                            fieldChecker.get(i) + size != fieldChecker.get(i + 1) &&
                                            fieldChecker.get(i - 1) + 6 != fieldChecker.get(i + 1)) {
                                        invalidFieldInput = true;
                                    }
                                }
                            }
                    }

                    //Set the target values.
                    targetValues.get(Integer.valueOf(textField[0]) - 1).setText(targetValue);
                    cluster.setClusterTargetValue(targetValues.get(Integer.valueOf(textField[0]) - 1).getText());

                    //Set the cluster color.
                    cluster.setClusterColor();
                    for (TextField text : cluster.getCluster()) {
                        text.setStyle(cluster.getClusterColor());
                    }
                }
                //Display message for adjacent cells & for duplicate cells in a cluster.
                Set<Integer> set = new HashSet<Integer>(fieldNumbers);

                if (set.size() < fieldNumbers.size()) {
                    new Alert(Alert.AlertType.ERROR,
                            "Invalid file selected. Please select a new file!")
                            .showAndWait();
                } else if (invalidFieldInput) {
                    new Alert(Alert.AlertType.ERROR,
                            "Invalid file selected. Please select a new file!")
                            .showAndWait();
                }
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            }
            //endregion
        });
        //endregion

        //region Load from Text
        loadText.setOnMouseClicked(e ->

        {
            //Create the input text pane.
            Button inputDone = new Button("Done");
            inputDone.setMinSize(60, 30);
            TextArea inputText = new TextArea();
            inputText.setMinHeight(385);
            GridPane inputPane = new GridPane();
            inputPane.setPadding(new Insets(5));
            inputPane.setVgap(5);
            inputPane.add(inputText, 0, 0, 2, 1);
            inputPane.add(inputDone, 1, 1, 1, 1);
            update();

            //region Create the new game.
            inputDone.setOnMouseClicked(f -> {
                //Create a temp text file, containing the entered code, and then generate the game from it.
                try {
                    PrintWriter writer = new PrintWriter("temp-storage.txt", "UTF-8");
                    //Check for proper input
                    if (inputText.getText().contains("[a-zA-Z]+") == false && inputText.getText().length() > 2) {
                        writer.print(inputText.getText());
                    } else {
                        new Alert(Alert.AlertType.ERROR,
                                "Invalid input. Please try again!")
                                .showAndWait();
                    }

                    writer.close();
                } catch (Exception z) {
                    z.printStackTrace();
                }

                //region Get the Game Ready
                String path = "temp-storage.txt";

                //Clear the game logic from the loaded by default example.
                logic.getClusters().clear();
                logic.getTextFields().clear();
                for (Text text : targetValues) {
                    text.setText("");
                }
                Cluster.clusterColorPointer = 0;

                //See how big is the grid (NxN). Find the max number of a textfield.
                try (Scanner scanner = new Scanner(new File(path))) {
                    //Work through every line in the given text file to create the game grid. (DUPLICATE CODE BELOW).
                    while (scanner.hasNext()) {
                        //Split the input line into target value and textfields.
                        String line = scanner.nextLine();
                        String[] valueAndFields = line.split(" ", 2);
                        String targetValue = valueAndFields[0];
                        String textFields = valueAndFields[1];
                        //Get the max value.
                        String[] textField = textFields.split(",");
                        int[] array = Arrays.asList(textField).stream().mapToInt(Integer::parseInt).toArray();
                        int max = getMax(array);
                        if (size < max) size = max;
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
                size = (int) Math.sqrt(Double.valueOf(size));

                //Initiate the holders with the new size.
                textFieldHolder = new TextField[size][size];
                valueHolder = new int[size][size];
                targetValues.clear();

                //endregion

                //region Draw the game grid anew
                canvasPane.getChildren().clear();
                for (int j = 0; j < size; j++) {
                    for (int i = 0; i < size; i++) {
                        //Add the rectangles
                        Rectangle rec = new Rectangle(60, 60);
                        rec.setStroke(Color.BLACK);
                        rec.setFill(Color.WHITE);
                        rec.setStrokeWidth(3);
                        rec.setStrokeType(StrokeType.CENTERED);
                        rec.widthProperty().bind(canvasPane.widthProperty().divide(size + 2));
                        rec.heightProperty().bind(canvasPane.heightProperty().divide(size + 2));

                        //Add the text areas
                        TextField text = new TextField();
                        text.setAlignment(Pos.CENTER);
                        text.maxWidthProperty().bind(canvasPane.widthProperty().divide(size + 2));
                        text.maxHeightProperty().bind(canvasPane.heightProperty().divide(size + 2));
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

                        //region Get easier access to rows and cols
                        textFieldHolder[j][i] = text;
                        final int row = i;
                        final int col = j;
                        text.textProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                boolean numeric = true;
                                numeric = text.getText().matches("-?\\d+(\\.\\d+)?");

                                if (text.getText() == null || text.getText().isEmpty() || text.getText() == "" || numeric == false)
                                    valueHolder[col][row] = 0;

                                else {
                                    valueHolder[col][row] = Integer.valueOf(text.getText());
                                }
                            }
                        });
                        //endregion

                        //region Handler for Playing Buttons
                        text.addEventHandler(MouseEvent.MOUSE_CLICKED, a -> {
                            //Clear the text box when it has been clicked
                            text.setText("");
                            for (Button button : playButtons) {
                                button.setOnMouseClicked(p -> {
                                    text.setText(button.getText());

                                    //Check for valid input. The input depends on the size of the board.
                                    if (button.getText() != null)
                                        if (Integer.valueOf(text.getText()) > size) {
                                            text.setText("");
                                            new Alert(Alert.AlertType.ERROR,
                                                    "You cant enter that big of a number!")
                                                    .showAndWait();
                                        }
                                });
                            }
                        });
                        //endregion
                    }
                }
                //endregion

                //region Check for proper keyboard input & Win condition
                for (TextField text : logic.getTextFields()) {
                    text.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            //Check for valid entry.
                            Integer enteredNumber = 0;

                            //Check if it is a numeric entry
                            boolean numeric = true;
                            numeric = text.getText().matches("-?\\d+(\\.\\d+)?");

                            //If it is a number, and it is in the range in the size,
                            //we keep the change to the text field.
                            if (numeric) {
                                enteredNumber = tryParse(text.getText());
                                if (enteredNumber > 0 && enteredNumber <= size) {
                                    text.setText(enteredNumber.toString());
                                } else {
                                    text.setText("");
                                    new Alert(Alert.AlertType.ERROR,
                                            "Invalid input! You cannot enter that big of a number!")
                                            .showAndWait();
                                }
                            } else {
                                text.setText("");
                            }

                            //Win condition checking.
                            ArrayList<Boolean> isFinished = new ArrayList<>();
                            for (Cluster cluster : logic.getClusters()) {
                                isFinished.add(cluster.checkIfSolved());
                            }

                            //Check the cols
                            isComplete = true;
                            int[] col;
                            for (int i = 0; i < size; i++) {
                                col = getColumn(valueHolder, i);
                                //Check if the col contains zeros (i.e. not fully populated)
                                if (duplicates(col) == true) {
                                    isComplete = false;
                                    break;
                                }
                            }

                            //Check the rows
                            int[] row;
                            for (int i = 0; i < size; i++) {
                                row = getRow(valueHolder, i);
                                if (duplicates(row) == true) {
                                    isComplete = false;
                                    break;
                                }
                            }

                            if (!isFinished.contains(false) && isComplete == true) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                        "It took you *ADD TIME HERE*");

                                alert.setTitle("You have won!");
                                alert.setHeaderText("!CONGRATULATIONS!");

                                alert.showAndWait();

                                //Win animation
                                alert.showAndWait();
                                Text finishText = new Text("GREAT JOB!");
                                finishText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                                finishText.setStyle(" -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);" +
                                        "    -fx-stroke: black;" +
                                        "    -fx-stroke-width: 1;");
                                FlowPane textPane = new FlowPane();
                                textPane.setAlignment(Pos.CENTER);
                                textPane.getChildren().add(finishText);
                                ScaleTransition st = new ScaleTransition(Duration.millis(2000));
                                st.setByX(1.5f);
                                st.setByY(1.5f);
                                st.setCycleCount(Integer.MAX_VALUE);
                                st.setAutoReverse(true);
                                ParallelTransition pt1 = new ParallelTransition(finishText, st);
                                pt1.play();
                                mainPane.add(textPane, 0, 0, 1, 1);
                                mainPane.setOnMouseClicked(l -> {
                                    mainPane.getChildren().remove(textPane);
                                });
                            }
                        }
                    });
                }
                //endregion

                //region Get the game info from the file.
                fieldNumbers.clear();
                invalidFieldInput = false;
                try (Scanner scanner = new Scanner(new File(path))) {
                    //Work through every line in the given text file to create the game grid.
                    while (scanner.hasNext()) {
                        //For every line we create a cluster.
                        Cluster cluster = new Cluster();
                        logic.getClusters().add(cluster);

                        String line = scanner.nextLine();
                        String[] valueAndFields = line.split(" ", 2);
                        String targetValue = valueAndFields[0];
                        String textFields = valueAndFields[1];

                        //Create the text fields and add them to their cluster.
                        ArrayList<Integer> fieldChecker = new ArrayList<>();
                        String[] textField = textFields.split(",");
                        for (String text : textField) {
                            cluster.addField(logic.getTextFields().get(Integer.valueOf(text) - 1));
                            //Add the numbers to check for valid input
                            fieldNumbers.add(Integer.valueOf(text));
                            fieldChecker.add(Integer.valueOf(text));
                        }
                        //Check for adjacent cells in a cluster.
                        Collections.sort(fieldChecker);
                        for (int i = 0; i < fieldChecker.size(); i++) {
                            if (fieldChecker.size() < 3)
                                if (i < fieldChecker.size() - 1) {
                                    if (fieldChecker.get(i) + 1 != fieldChecker.get(i + 1) &&
                                            fieldChecker.get(i) + size != fieldChecker.get(i + 1)) {
                                        invalidFieldInput = true;
                                    }
                                } else {
                                    if (i < fieldChecker.size() - 1 && i > 0) {
                                        if (fieldChecker.get(i) + 1 != fieldChecker.get(i + 1) &&
                                                fieldChecker.get(i) + size != fieldChecker.get(i + 1) &&
                                                fieldChecker.get(i - 1) + 6 != fieldChecker.get(i + 1)) {
                                            invalidFieldInput = true;
                                        }
                                    }
                                }
                        }

                        //Set the target values.
                        targetValues.get(Integer.valueOf(textField[0]) - 1).setText(targetValue);
                        cluster.setClusterTargetValue(targetValues.get(Integer.valueOf(textField[0]) - 1).getText());

                        //Set the cluster color.
                        cluster.setClusterColor();
                        for (TextField text : cluster.getCluster()) {
                            text.setStyle(cluster.getClusterColor());
                        }
                    }
                    //Display message for adjacent cells & for duplicate cells in a cluster.
                    Set<Integer> set = new HashSet<Integer>(fieldNumbers);

                    if (set.size() < fieldNumbers.size()) {
                        new Alert(Alert.AlertType.ERROR,
                                "Invalid file selected. Please select a new file!")
                                .showAndWait();
                    } else if (invalidFieldInput) {
                        new Alert(Alert.AlertType.ERROR,
                                "Invalid file selected. Please select a new file!")
                                .showAndWait();
                    }
                } catch (FileNotFoundException q) {
                    q.printStackTrace();
                }
                //endregion

                //Close window after "Done".
                Window window = ((Node) (f.getSource())).getScene().getWindow();
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }

                //Delete the temp file.
                File tempFile = new File("temp-storage.txt");
                tempFile.delete();
            });
            //endregion

            //Show the new scene containing the text area.
            Scene inputScene = new Scene(inputPane, 250, 430);
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.NONE);
            newWindow.setTitle("Enter puzzle");
            newWindow.setScene(inputScene);
            newWindow.setResizable(false);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + primaryStage.getWidth());
            newWindow.setY(primaryStage.getY());

            newWindow.show();
        });
        //endregion

        //region Timer
        start();
        time.textProperty().
                addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        timerLabel.setText(time.getText());
                    }
                });
        //endregion
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

    //region Helper Methods
    public void update() {
        secondsPassed = 0;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    secondsPassed++;
                    time.setText(String.valueOf(secondsPassed));
                });
            }
        };
        timer.cancel();
        timer = new Timer();
        timer.schedule(timerTask, 1000, 1000);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> {
                secondsPassed++;
                time.setText(String.valueOf(secondsPassed));
            });
        }
    };

    public void start() {
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    boolean duplicates(int[] array) {
        for (int j = 0; j < array.length; j++) {
            //if (array[j] == 0) array = removeTheElement(array, j);
            if (array[j] == 0) array[j] = Integer.MAX_VALUE - j;
        }
        Set<Integer> lump = new HashSet<Integer>();
        for (int i : array) {
            if (lump.contains(i)) return true;
            lump.add(i);
        }
        return false;
    }

    int[] getColumn(int[][] matrix, int column) {
        return IntStream.range(0, matrix.length)
                .map(i -> matrix[i][column]).toArray();
    }

    int[] getRow(int[][] matrix, int row) {
        return IntStream.range(0, matrix.length)
                .map(j -> matrix[row][j]).toArray();
    }

    public static int[] removeTheElement(int[] arr, int index) {

        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null
                || index < 0
                || index >= arr.length) {

            return arr;
        }

        // Create another array of size one less
        int[] anotherArray = new int[arr.length - 1];

        // Copy the elements except the index
        // from original array to the other array
        for (int i = 0, k = 0; i < arr.length; i++) {

            // if the index is
            // the removal element index
            if (i == index) {
                continue;
            }

            // if the index is not
            // the removal element index
            anotherArray[k++] = arr[i];
        }

        // return the resultant array
        return anotherArray;
    }

    public int tryParse(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public int tryParse(String value) {
        return tryParse(value, 0);
    }

    public static int getMax(int[] inputArray) {
        int maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }
//endregion
}