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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main extends Application {

    private ArrayList<Button> playButtons = new ArrayList<>();
    private ArrayList<Text> targetValues = new ArrayList<>();
    private int size = 4;
    private int[][] valueHolder = new int[size][size];
    private TextField[][] textFieldHolder = new TextField[size][size];
    private boolean isComplete = true;

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
                        alert.setHeaderText("!CONGRATULATIONS!");

                        alert.showAndWait();
                    }
                }
            });
        }
        //endregion

        //region Button Show Mistakes
        showMistakes.setOnMousePressed(e -> {
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

        //endregion

        //region Button Load from File
        loadFile.setOnMouseClicked(e -> {
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
                    if(size < max) size = max;
                }
            } catch (Exception f) {
                f.printStackTrace();
            }
            size = (int) Math.sqrt(Double.valueOf(size));

            //Initiate the holders with the new size.
            textFieldHolder = new TextField[size][size];
            valueHolder = new int[size][size];

            //region Draw the game grid anew
            canvasPane.getChildren().clear();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
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

            //region Check for proper keyboard input
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
                    }
                });
            }
            //endregion

            //Get the game info from the file.
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
                    String[] textField = textFields.split(",");
                    for (String text : textField) {
                        TextField field = new TextField(text);
                        cluster.addField(field);
                    }

                    //Set the cluster color.
                    cluster.setClusterColor();

                    //Set the target values.
                    targetValues.get(Integer.valueOf(textField[0]) - 1).setText(targetValue);
                    cluster.setClusterTargetValue(targetValues.get(Integer.valueOf(textField[0])).getText());
                }

            } catch (FileNotFoundException f) {
                f.printStackTrace();
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
    void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    boolean duplicates(int[] array) {
        for (int j = 0; j < array.length; j++) {
            if (array[j] == 0) array = removeTheElement(array, j);
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

    public static int getMax(int[] inputArray){
        int maxValue = inputArray[0];
        for(int i=1;i < inputArray.length;i++){
            if(inputArray[i] > maxValue){
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }
//endregion
}

//USEFUL
/*
        //Run through all the text fields and add them to clusters
        /*for(int i = 0; i < logic.getTextFields().size(); i++) {
            logic.getTextFields().get(i).setText(Integer.toString(i));
        }*/

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