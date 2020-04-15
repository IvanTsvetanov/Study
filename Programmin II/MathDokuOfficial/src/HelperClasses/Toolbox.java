package HelperClasses;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
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
                        "-fx-min-width: 50px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"
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
}
