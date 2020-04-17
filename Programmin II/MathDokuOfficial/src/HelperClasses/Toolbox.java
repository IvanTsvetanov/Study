package HelperClasses;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.*;
import java.util.stream.IntStream;

/*
    A helper class built to assist in creating the MathDoku Puzzles.
 */

public class Toolbox {
    public int secondsPassed = 0;
    public static Timer timer = new Timer();
    public TextField time = new TextField("0");

    //Creates a dial button. The text of the button is the method argument.
    public Button createDialButton (String btnText) {
        Button button = new Button(btnText);
        button.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 40px; " +
                        "-fx-min-height: 40px; " +
                        "-fx-max-width: 60px; " +
                        "-fx-max-height: 60px;"
        );
        return button;
    }

    //Normalises the given cage. Sets it color, adds it to the list of game fields and sets its target value.
    public Cage normaliseCage (Cage cage, GameLogic gameLogic, String targetValue, int numTargetValue) {
        cage.setCageColor();
        gameLogic.addCage(cage);
        gameLogic.getTargetValues().get(numTargetValue).setText(targetValue);
        cage.setCageTargetValue(gameLogic.getTargetValues().get(numTargetValue).getText());

        return cage;
    }

    //Parses a string to an int.
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

    //Returns a column in a given two-dimensional matrix.
    public int[] getColumn(int[][] matrix, int column) {
        return IntStream.range(0, matrix.length)
                .map(i -> matrix[i][column]).toArray();
    }

    //Returns a row in a given two-dimensional matrix.
    public int[] getRow(int[][] matrix, int row) {
        return IntStream.range(0, matrix.length)
                .map(j -> matrix[row][j]).toArray();
    }

    //Checks for duplicates in an array.
    public boolean duplicates(int[] array) {
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

    //Sleeps thread for one second.
    public void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //Timer settings.
    public void start() {
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void restart() {
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

    //Checking for mistakes in the game fields.
    public void checkForValidCages(GameLogic gameLogic) {
        for (int z = 0; z < gameLogic.getCages().size(); z++) {
            if (gameLogic.getCages().get(z).checkIfSolved() == false) {
                for (int i = 0; i < gameLogic.getCages().get(z).getFields().size(); i++) {
                    //Holds the previous style of the game field, so that we don't lose the BG color.
                    String style = gameLogic.getCages().get(z).getFields().get(i).getStyle();
                    gameLogic.getCages().get(z).getFields().get(i).setStyle("-fx-text-fill: red;" + style);
                }
            } else {
                //Return the normal colors.
                for (int i = 0; i < gameLogic.getCages().get(z).getFields().size(); i++) {
                    gameLogic.getCages().get(z).setCageColorNotRandom(gameLogic.getCages().get(z).getCageColor());
                }
            }
        }
    }

    public void checkForValidCols(int sizeOfGrid, GameLogic gameLogic) {
        int[] col;
        for (int i = 0; i < sizeOfGrid; i++) {
            col = getColumn(gameLogic.getValueHolder(), i);
            //Check if the col contains zeros (i.e. not fully populated)
            if (duplicates(col) == true) {
                for (int j = 0; j < sizeOfGrid; j++) {
                    //Holds the previous style of the game field, so that we don't lose the BG color.
                    String style = gameLogic.getGameField(j, i).getStyle();
                    gameLogic.getGameField(j, i).setStyle("-fx-text-fill: red;" + style);
                }
            }
        }
    }

    public void checkForValidRows(int sizeOfGrid, GameLogic gameLogic) {
        int[] row;
        for (int i = 0; i < sizeOfGrid; i++) {
            row = getRow(gameLogic.getValueHolder(), i);
            if (duplicates(row) == true) {
                for (int j = 0; j < sizeOfGrid; j++) {
                    //Holds the previous style of the game field, so that we don't lose the BG color.
                    String style = gameLogic.getGameField(i, j).getStyle();
                    gameLogic.getGameField(i, j).setStyle("-fx-text-fill: red;" + style);
                }
            }
        }
    }

    //Returns the maximum value of an array.
    public int getMax(int[] inputArray) {
        int maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }

    //Clears the game grid.
    public void clearGameGrid(GameLogic gameLogic) {
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
    }

    //Changes the font of the game grid.
    public void changeFont(GameLogic gameLogic) {
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
    }

    //Displays the game rules.
    public void viewRules() {
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
    }
}
