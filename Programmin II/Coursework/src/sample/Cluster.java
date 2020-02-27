package sample;

import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;

public class Cluster {
    //region Fields
    private ArrayList<TextField> cluster = new ArrayList<>();
    private boolean isSolved = false;
    private String targetValue;

    //Generate a random color for each Cluster
    private Random rand = new Random();
    private static int bound = 12;
    private static List<String> colors = new ArrayList<>() {
        {
            add("lightblue");
            add("orange");
            add("beige");
            add("blueviolet");
            add("brown");
            add("cyan");
            add("grey");
            add("khaki");
            add("yellowgreen");
            add("slateblue");
            add("salmon");
            add("gold");
        }
    };
    //endregion

    //region Constructors
    public Cluster() {
    }

    public Cluster(TextField textField) {
        this.cluster.add(textField);
    }
    //endregion

    //region Getters and Setters
    public ArrayList<TextField> getCluster() {
        return cluster;
    }

    //endregion

    //region Methods
    public void addField(TextField field) {
        this.cluster.add(field);
    }

    //text.setStyle("-fx-background-color: ;");
    public void setClusterColor() {
        int num = rand.nextInt(bound);

        String color = colors.get(num);
        for (TextField text : cluster)
            text.setStyle("-fx-background-color: " + color + ";");

        colors.remove(num);
        bound--;
    }

    public void setClusterTargetValue(String value) {
        this.targetValue = value;
    }

    public boolean checkIfSolved() {
        int targetNumber;
        String targetSign = null;

        //Split up the target value
        if (this.targetValue.length() == 2) {
            targetNumber = Character.getNumericValue(this.targetValue.charAt(0));
            targetSign = String.valueOf(this.targetValue.charAt(1));
        } else {
            targetNumber = Character.getNumericValue(this.targetValue.charAt(0));
        }

        //Hold the values of the cells
        ArrayList<Integer> values = new ArrayList<>();
        for (TextField text : this.cluster) {
            if (text.getText() == null || text.getText().isEmpty()) {
                values.add(Integer.parseInt("0"));
            } else {
                values.add(Integer.parseInt(text.getText()));
            }
        }

        //Calculate if the target value has been reached
        int tempValueHolder = 0;
        if (targetSign != null) {
            switch (targetSign) {
                case "*":
                    tempValueHolder = 1;
                    for (Integer value : values) {
                        tempValueHolder *= value;
                    }
                    break;
                case "+":
                    for (Integer value : values) {
                        tempValueHolder += value;
                    }
                    break;
                case "-":
                    Collections.sort(values, Collections.reverseOrder());
                    tempValueHolder = values.get(0);
                    for (int i = 1; i < values.size(); i++) {
                        tempValueHolder -= values.get(i);
                    }
                    break;
                case "/":
                    Collections.sort(values, Collections.reverseOrder());
                    tempValueHolder = values.get(0);
                    for (int i = 1; i < values.size(); i++) {
                        if (values.get(i) == 0)
                            tempValueHolder /= 1;
                        else {
                            tempValueHolder /= values.get(i);
                        }
                    }
                    break;
            }
        } else {
            tempValueHolder = values.get(0);
        }

        //Check if the target values have been reached
        if (tempValueHolder == targetNumber) return true;
        else return false;
    }

    public String getTargetValue() {
        return targetValue;
    }
    //endregion
}
