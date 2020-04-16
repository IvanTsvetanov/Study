package Puzzles;

import HelperClasses.Cage;
import HelperClasses.GameLogic;
import HelperClasses.Toolbox;
import MainMenu.Launch;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;

import static HelperClasses.Toolbox.timer;

/*
    Creates a pre-build puzzle as an example.
    Has all functionalities as a normal puzzle.
 */

public class ExamplePuzzle {
    //The pane that holds all the components.
    private GridPane mainPane = new GridPane();
    //The dial buttons with which you can play.
    private ArrayList<Button> playButtons = new ArrayList<>();
    //The pane that holds the game grid.
    private GridPane canvasPane = new GridPane();
    //Toolbox for useful methods.
    private Toolbox toolbox = new Toolbox();
    //Stores information about the game.
    private GameLogic gameLogic = new GameLogic();
    //Stores the size of the grid (NxN).
    private int sizeOfGrid = 4;
    //Stores information about the timer.
    private static int minutes = 0;
    private static int seconds = 0;
    //Showing mistakes button flag for turning mode on/off.
    private boolean mistakesFlag = false;
    //Listener enabling the user to see his mistakes as he enters numbers.
    ChangeListener textFieldChangeL = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            //Check if the cages are valid.
            toolbox.checkForValidCages(gameLogic);

            //Check if all the cols are valid.
            toolbox.checkForValidCols(sizeOfGrid, gameLogic);

            //Check if all the rows are valid.
            toolbox.checkForValidRows(sizeOfGrid, gameLogic);
        }
    };

    public Scene buildExampleScene() {

        //region Setting up the scene
        //Set up the look of the game grid, and its movements regarding the Main Pane.
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

        Button restartTimer = new Button("Restart Timer");
        restartTimer.setMinSize(100, 30);
        restartTimer.prefWidthProperty().bind(mainPane.widthProperty());
        restartTimer.setMaxSize(250, 60);

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
        bottomComponents.add(restartTimer, 2, 0, 1, 1);
        bottomComponents.add(timerLabel, 2, 1, 1, 1);
        bottomComponents.setAlignment(Pos.TOP_LEFT);

        bottomComponents.setVgap(20);
        bottomComponents.setHgap(80);

        //Add the side elements and format them
        GridPane sideComponents = new GridPane();

        sideComponents.setPadding(new Insets(10, 20, 10, 20));
        sideComponents.setVgap(20);
        sideComponents.setHgap(30);
        sideComponents.setAlignment(Pos.BOTTOM_CENTER);

        Button viewRules = new Button("View Game Rules");
        viewRules.setMinSize(150, 30);
        viewRules.prefHeightProperty().bind(mainPane.heightProperty());
        viewRules.setMaxSize(150, 50);

        Button changeColors = new Button("Change the Colors");
        changeColors.setMinSize(150, 30);
        changeColors.prefHeightProperty().bind(mainPane.heightProperty());
        changeColors.setMaxSize(150, 50);

        Button changeFont = new Button("Change Font");
        changeFont.setMinSize(150, 30);
        changeFont.prefHeightProperty().bind(mainPane.heightProperty());
        changeFont.setMaxSize(150, 50);

        sideComponents.add(viewRules, 0, 0, 2, 1);
        sideComponents.add(changeColors, 0, 1, 2, 1);
        sideComponents.add(changeFont, 0, 2, 2, 1);

        //Add return to main menu button
        HBox returnToMenu = new HBox();
        Button returnMenu = new Button("Return to Main Menu");
        returnMenu.setMinSize(150, 83);
        returnToMenu.getChildren().add(returnMenu);
        returnToMenu.setAlignment(Pos.CENTER);
        //endregion

        //region Create and add dial buttons
        int x = 0;
        int y = 3;
        for (int i = 1; i < 9; i++) {
            //Create the button.
            Button button = toolbox.createDialButton(Integer.toString(i));
            button.prefWidthProperty().bind(sideComponents.widthProperty());
            button.prefHeightProperty().bind(sideComponents.heightProperty());
            //Add the buttons to a list for easy access.
            playButtons.add(button);

            //Add buttons to the grid
            if (i % 2 != 0) {
                x = 0;
                sideComponents.add(button, x, y, 1, 1);
            } else {
                x = 1;
                sideComponents.add(button, x, y, 1, 1);
                y++;
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

        //Adding components to the Main Pane
        mainPane.add(canvasPane, 0, 0, 1, 1);
        mainPane.add(bottomComponents, 0, 1, 1, 1);
        mainPane.add(sideComponents, 1, 0, 1, 1);
        mainPane.add(returnToMenu, 1, 1, 1, 1);
        //endregion

        //region Draw Game Grid
        int width = 60;
        gameLogic.setSize(sizeOfGrid);

        for (int i = 0; i < sizeOfGrid; i++) {
            for (int j = 0; j < sizeOfGrid; j++) {
                //Add the rectangles
                Rectangle rec = new Rectangle(width, width);
                rec.setStroke(Color.BLACK);
                rec.setFill(Color.WHITE);
                rec.setStrokeWidth(3);
                rec.setStrokeType(StrokeType.CENTERED);
                rec.widthProperty().bind(canvasPane.widthProperty().divide(sizeOfGrid + 1));
                rec.heightProperty().bind(canvasPane.heightProperty().divide(sizeOfGrid + 1));

                //Add the textFields (game fields)
                TextField text = new TextField();
                text.setAlignment(Pos.CENTER);
                text.maxWidthProperty().bind(canvasPane.widthProperty().divide(sizeOfGrid + 1));
                text.maxHeightProperty().bind(canvasPane.heightProperty().divide(sizeOfGrid + 1));
                gameLogic.addGameField(text);

                //Adding the target values
                StackPane targetPane = new StackPane();
                Text target = new Text();
                target.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                targetPane.getChildren().add(target);
                targetPane.setMaxSize(10, 10);
                gameLogic.addTargetValue(target);

                //Stacks the textfield (game field) on top of the rectangles,
                // so the text from the game field can be properly seen.
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(rec);
                stackPane.setAlignment(Pos.TOP_LEFT);
                stackPane.getChildren().add(text);
                stackPane.getChildren().add(targetPane);

                canvasPane.add(stackPane, i, j, 1, 1);
                text.setFont(Font.font("Verdana", FontWeight.BOLD, 25));

                //region Get easier access to rows and cols
                gameLogic.addToGameFieldArray(j, i, text);

                //Sets the values of the game fields.
                final int row = i;
                final int col = j;
                text.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        boolean numeric = true;
                        numeric = text.getText().matches("-?\\d+(\\.\\d+)?");

                        if (text.getText() == null || text.getText().isEmpty() || text.getText() == "" || numeric == false)
                            gameLogic.setValue(col, row, 0);

                        else {
                            gameLogic.setValue(col, row, Integer.valueOf(text.getText()));
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
                            if (!text.getText().equals(""))
                                if (Integer.valueOf(text.getText()) > sizeOfGrid) {
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
        //endregion`

        //region Adding the cages
        //Add the fields to the cages
        Cage cage1 = new Cage();
        cage1.addField(gameLogic.getGameFields().get(0));
        cage1.addField(gameLogic.getGameFields().get(1));
        cage1.addField(gameLogic.getGameFields().get(2));
        cage1 = toolbox.normaliseCage(cage1, gameLogic, "8*", 0);

        Cage cage2 = new Cage();
        cage2.addField(gameLogic.getGameFields().get(3));
        cage2 = toolbox.normaliseCage(cage2, gameLogic, "3", 3);

        Cage cage3 = new Cage();
        cage3.addField(gameLogic.getGameFields().get(4));
        cage3.addField(gameLogic.getGameFields().get(8));
        cage3 = toolbox.normaliseCage(cage3, gameLogic, "1-", 4);

        Cage cage4 = new Cage();
        cage4.addField(gameLogic.getGameFields().get(5));
        cage4.addField(gameLogic.getGameFields().get(9));
        cage4 = toolbox.normaliseCage(cage4, gameLogic, "5+", 5);

        Cage cage5 = new Cage();
        cage5.addField(gameLogic.getGameFields().get(6));
        cage5 = toolbox.normaliseCage(cage5, gameLogic, "2", 6);

        Cage cage6 = new Cage();
        cage6.addField(gameLogic.getGameFields().get(7));
        cage6.addField(gameLogic.getGameFields().get(11));
        cage6 = toolbox.normaliseCage(cage6, gameLogic, "2/", 7);

        Cage cage7 = new Cage();
        cage7.addField(gameLogic.getGameFields().get(12));
        cage7.addField(gameLogic.getGameFields().get(13));
        cage7 = toolbox.normaliseCage(cage7, gameLogic, "5+", 12);

        Cage cage8 = new Cage();
        cage8.addField(gameLogic.getGameFields().get(10));
        cage8.addField(gameLogic.getGameFields().get(14));
        cage8.addField(gameLogic.getGameFields().get(15));
        cage8 = toolbox.normaliseCage(cage8, gameLogic, "8+", 10);
        //endregion

        //region Win Condition & Proper Keyboard Input
        //Update when a new value has been entered into a game field.
        for (TextField text : gameLogic.getGameFields()) {
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
                        enteredNumber = toolbox.tryParse(text.getText());
                        if (enteredNumber > 0 && enteredNumber <= sizeOfGrid) {
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
                    //Check if all the cages are valid.
                    ArrayList<Boolean> isFinished = new ArrayList<>();
                    for (Cage cage : gameLogic.getCages()) {
                        isFinished.add(cage.checkIfSolved());
                    }

                    //Check if all the columns are valid.
                    boolean isComplete = true;
                    int[] col;
                    for (int i = 0; i < sizeOfGrid; i++) {
                        col = toolbox.getColumn(gameLogic.getValueHolder(), i);
                        //Check if the col contains zeros (i.e. not fully populated)
                        if (toolbox.duplicates(col) == true) {
                            isComplete = false;
                            break;
                        }
                    }

                    //Check if all the rows are valid
                    int[] row;
                    for (int i = 0; i < sizeOfGrid; i++) {
                        row = toolbox.getRow(gameLogic.getValueHolder(), i);
                        if (toolbox.duplicates(row) == true) {
                            isComplete = false;
                            break;
                        }
                    }

                    //Display the alert if the user has completed the puzzle.
                    if (!isFinished.contains(false) && isComplete == true) {
                        //Stop the timer after winning.
                        timer.cancel();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "It took you " + timerLabel.getText() + " to solve the puzzle!");

                        alert.setTitle("You have won!");
                        alert.setHeaderText("CONGRATULATIONS!");
                        alert.showAndWait();

                        //Win animation
                        Text finishText = new Text("GREAT JOB!");
                        finishText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
                        finishText.setStyle(" -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);" +
                                "    -fx-stroke: black;" +
                                "    -fx-stroke-width: 1;");

                        //Holds the text.
                        FlowPane textPane = new FlowPane();
                        textPane.setAlignment(Pos.CENTER);
                        textPane.getChildren().add(finishText);

                        //Creates the animation for the text.
                        ScaleTransition st = new ScaleTransition(Duration.millis(2000));
                        st.setByX(1.5f);
                        st.setByY(1.5f);
                        st.setCycleCount(Integer.MAX_VALUE);
                        st.setAutoReverse(true);

                        //Combines the two elements above and plays the animation.
                        ParallelTransition pt1 = new ParallelTransition(finishText, st);
                        pt1.play();
                        mainPane.add(textPane, 0, 0, 1, 1);
                        //Remove the win animation.
                        mainPane.setOnMouseClicked(l -> {
                            mainPane.getChildren().remove(textPane);
                        });
                    }
                }
            });
        }
        //endregion

        //region Button Show Mistakes
        //Change the flag variable (so as to enter/exit "show mistakes" mode).
        showMistakes.setOnMouseClicked(a -> {
            //Flag for entering/exiting mistakes mode.
            if (!mistakesFlag) {
                mistakesFlag = true;
                //Check the validity of the entries the user has made so far.
                //Check if the cages are valid.
                toolbox.checkForValidCages(gameLogic);

                //Check if all the cols are valid.
                toolbox.checkForValidCols(sizeOfGrid, gameLogic);

                //Check if all the rows are valid.
                toolbox.checkForValidRows(sizeOfGrid, gameLogic);
            } else mistakesFlag = false;

            //Enter "show mistakes" mode.
            if (mistakesFlag) {
                //Change the name of the button to indicate the mode the user is in.
                showMistakes.setText("Hide Mistakes");

                //Change the color of the numbers in the game fields as the user enters them.
                for (TextField text : gameLogic.getGameFields()) {
                    text.textProperty().addListener(textFieldChangeL);
                }
            } else {
                //Change the name of the button to indicate that we are exiting the mode.
                showMistakes.setText("Show Mistakes");

                //Remove the listener so the mistakes the user makes no longer show up.
                for (TextField text : gameLogic.getGameFields()) {
                    text.textProperty().removeListener(textFieldChangeL);
                }

                //Return to the normal color of the game fields.
                for (int z = 0; z < gameLogic.getCages().size(); z++) {
                    for (int i = 0; i < gameLogic.getCages().get(z).getFields().size(); i++) {
                        gameLogic.getCages().get(z).setCageColorNotRandom(gameLogic.getCages().get(z).getCageColor());
                    }
                }
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
                for (int i = 0; i < gameLogic.getGameFields().size(); i++) {
                    gameLogic.getGameFields().get(i).setText("");
                }
            }
        });
        //endregion

        //region Button Undo
        //Arraylists to hold values and game fields.
        ArrayList<TextField> textFieldArrayList = new ArrayList<>();
        ArrayList<String> textFieldArrayListValues = new ArrayList<>();
        ArrayList<TextField> textFieldsRedo = new ArrayList<>();
        ArrayList<String> textFieldsValuesRedo = new ArrayList<>();

        undo.setDisable(true);
        redo.setDisable(true);

        //Listener to update the game fields.
        for (TextField text : gameLogic.getGameFields()) {
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
            //Create the components for the scene.
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
                for (TextField text : gameLogic.getGameFields()) {
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                }
                for (Text target : gameLogic.getTargetValues()) {
                    target.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                }
                Window window = ((Node) (a.getSource())).getScene().getWindow();
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            });

            mediumB.setOnMouseClicked(a -> {
                for (TextField text : gameLogic.getGameFields()) {
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
                }
                for (Text target : gameLogic.getTargetValues()) {
                    target.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
                }
                Window window = ((Node) (a.getSource())).getScene().getWindow();
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            });

            largeB.setOnMouseClicked(a -> {
                for (TextField text : gameLogic.getGameFields()) {
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 33));
                }
                for (Text target : gameLogic.getTargetValues()) {
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
            newWindow.setTitle("Select");
            newWindow.setScene(inputScene);
            newWindow.setResizable(false);

            newWindow.show();
        });
        //endregion

        //region Timer
        toolbox.start();
        toolbox.time.textProperty().
                addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        seconds = Integer.valueOf(toolbox.time.getText());

                        //Change the minutes when 60 seconds have passed.
                        if (Integer.valueOf(toolbox.time.getText()) == 60) {
                            minutes++;
                            seconds = 0;
                            toolbox.secondsPassed = 0;
                        }

                        //Display time taken in a normal way.
                        boolean isOneDigitSecs = (seconds >= 0 && seconds <= 9);
                        boolean isOneDigitMins = (minutes >= 0 && minutes <= 9);

                        if (isOneDigitMins && isOneDigitSecs)
                            timerLabel.setText("0" + minutes + ":" + "0" + seconds);
                        else if (isOneDigitMins) timerLabel.setText("0" + minutes + ":" + seconds);
                        else if (isOneDigitSecs) timerLabel.setText(minutes + ":" + "0" + seconds);
                        else timerLabel.setText(minutes + ":" + seconds);
                    }
                });
        //endregion

        //region Button Return to Main Menu
        returnMenu.setOnMouseClicked(e -> {
            //Confirming the user wants to go to the Main Menu.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to return to the Main Menu?");

            alert.setTitle("Clear confirmation");
            alert.setHeaderText("All progress will be lost!");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Close the current window
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.close();

                //Launch the Main Menu again
                Launch launch = new Launch();
                try {
                    stage.setWidth(300);
                    stage.setHeight(450);
                    launch.start(stage);
                } catch (Exception ex) {
                    System.out.println("A problem with returning has occurred! Please restart the application!");
                }
            }
        });
        //endregion

        //region Button Restart the timer
        restartTimer.setOnMouseClicked(e -> {
            toolbox.restart();
            minutes = 0;
            seconds = 0;
            timerLabel.setText("00:00");
        });
        //endregion

        //region Button View Rules
        viewRules.setOnMouseClicked(e -> {
            //Create the components for the scene.
            GridPane rulesMainPane = new GridPane();
            rulesMainPane.setAlignment(Pos.TOP_CENTER);
            rulesMainPane.setPadding(new Insets(5));
            rulesMainPane.setVgap(20);

            TextArea gameRules = new TextArea();
            gameRules.setFont(Font.font(18));
            gameRules.setEditable(false);
            gameRules.setText(
                    "Game Rules:\n" +
                            "1. Digits may appear only once in each row and column.\n" +
                            "2. The grids are divided in cages with targets and operators.\n" +
                            "3. Find a mathematical solution using the given operator \nto match the target.\n" +
                            "4. Each puzzle has only 1 solution.\n" +
                            "5. Please contact your psychiatrist in case of accute \nMathDoku addiction.\n\n" +
                            "BONUS:\n" +
                            "MathDoku puzzles are highly addictive.\n" +
                            "Solving MathDoku puzzles will boost your brain power.\n" +
                            "Solving MathDoku puzzles instead of math class is \nNOT recommended.");

            rulesMainPane.add(gameRules, 0, 0, 2, 2);

            //Create the scene and show it.
            Scene inputScene = new Scene(rulesMainPane, 520, 250);
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.NONE);
            newWindow.setTitle("Rules");
            newWindow.setScene(inputScene);
            newWindow.setResizable(false);

            newWindow.show();
        });
        //endregion

        //region Button Change Colors
        changeColors.setOnMouseClicked(e -> {
            for(Cage cage : gameLogic.getCages()) {
                cage.setCageColor();
            }
        });
        //endregion

        Scene mainScene = new Scene(mainPane, 690, 600);
        returnMenu.requestFocus();
        return mainScene;
    }
}


