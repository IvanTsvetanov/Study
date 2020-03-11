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
    private String clusterColor;

    //Generate a random color for each Cluster
    private Random rand = new Random();
    public static int clusterColorPointer = 0;
    private static int bound = 20;
    private static List<String> colors = new ArrayList<>() {
        {
            //Starting from 1st, get every 4th element.
            //When you get to the end, start from two,
            //and get every fifth element.
            //When you get to the end, start from three,
            //and get every sixth element.
            add("darkgray");
            add("grey");
            add("lightgray");

            add("darkblue");
            add("blue");
            add("lightblue");

            add("darkgreen");
            add("green");
            add("lightgreen");

            add("orangered");
            add("orange");
            add("darkorange");

            add("DARKGOLDENROD");
            add("yellow");
            add("lightyellow");

            add("mediumpurple");
            add("DARKSLATEBLUE");
            add("purple");

            add("deeppink");
            add("pink");
            add("lightpink");
            /*add("lightblue");
            add("orange");
            add("beige");
            add("blueviolet");
            add("brown");
            add("cyan");
            add("khaki");
            add("yellowgreen");
            add("slateblue");
            add("salmon");
            add("gold");*/
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
        //Check for the base cases
        if (clusterColorPointer == 0) {
            String color = colors.get(0);
            for (TextField text : cluster)
                text.setStyle("-fx-background-color: " + color + ";");
            setClusterColor("-fx-background-color: " + color + ";");
            clusterColorPointer += 3;
        } else if (clusterColorPointer == 1) {
            String color = colors.get(1);
            for (TextField text : cluster)
                text.setStyle("-fx-background-color: " + color + ";");
            setClusterColor("-fx-background-color: " + color + ";");
            clusterColorPointer += 3;
        } else if (clusterColorPointer == 2) {
            String color = colors.get(2);
            for (TextField text : cluster)
                text.setStyle("-fx-background-color: " + color + ";");
            setClusterColor("-fx-background-color: " + color + ";");
            clusterColorPointer += 3;
        } else if (clusterColorPointer % 3 == 0) {
            String color = colors.get(clusterColorPointer);
            for (TextField text : cluster)
                text.setStyle("-fx-background-color: " + color + ";");
            setClusterColor("-fx-background-color: " + color + ";");
            if (clusterColorPointer == colors.size() - 3) clusterColorPointer = 1;
            else {
                clusterColorPointer += 3;
            }
        } else if ((clusterColorPointer - 1) % 3 == 0) {
            String color = colors.get(clusterColorPointer);
            for (TextField text : cluster)
                text.setStyle("-fx-background-color: " + color + ";");
            setClusterColor("-fx-background-color: " + color + ";");
            if (clusterColorPointer == colors.size() - 2) clusterColorPointer = 2;
            else {
                clusterColorPointer += 3;
            }
        } else if ((clusterColorPointer - 2) % 3 == 0) {
            String color = colors.get(clusterColorPointer);
            for (TextField text : cluster)
                text.setStyle("-fx-background-color: " + color + ";");
            setClusterColor("-fx-background-color: " + color + ";");
            if (clusterColorPointer == colors.size() - 1) clusterColorPointer = 0;
            else {
                clusterColorPointer += 3;
            }
        }

        /*
        int num = rand.nextInt(bound);

        String color = colors.get(num);
        for (TextField text : cluster)
            text.setStyle("-fx-background-color: " + color + ";");

        setClusterColor("-fx-background-color: " + color + ";");

        colors.remove(num);
        bound--;*/
    }

    public void setClusterColorNotRandom(String color) {
        for (TextField text : cluster)
            text.setStyle(color);
    }

    public void setClusterTargetValue(String value) {
        this.targetValue = value;
    }

    public boolean checkIfSolved() {
        int targetNumber;
        String targetSign = null;

        //Split up the target value
        if(this.targetValue.length() > 1) {
            targetSign = this.targetValue.substring(this.targetValue.length() - 1);
            targetNumber = Integer.valueOf(this.targetValue.substring(0, this.targetValue.length() - 1));
        }
        else {
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
                case "x":
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
                case "÷":
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

    public String getClusterColor() {
        return clusterColor;
    }

    public void setClusterColor(String clusterColor) {
        this.clusterColor = clusterColor;
    }
    //endregion
}
