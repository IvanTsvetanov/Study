package HelperClasses;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Cage {
    //region Fields
    private ArrayList<TextField> cage = new ArrayList<>();
    private boolean isSolved = false;
    private String targetValue;
    private String cageColor;

    //Generate a random color for each cage.
    public static int cageColorPointer = 0;
    private static List<String> colors = new ArrayList<>() {
        {
            //Starting from 1st, get every 4th element.
            //When you get to the end, start from two,
            //and get every fifth element.
            //When you get to the end, start from three,
            //and get every sixth element.
            add("darkgray");
            add("lightgray");
            add("gray");

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

    //region Methods
    public ArrayList<TextField> getCluster() {
        return cage;
    }

    public void addField(TextField field) {
        this.cage.add(field);
    }

    //text.setStyle("-fx-background-color: ;");
    public void setCageColor() {
        //Check for the base cases
        if (cageColorPointer == 0) {
            String color = colors.get(0);
            for (TextField text : cage)
                text.setStyle("-fx-background-color: " + color + ";");
            setCageColor("-fx-background-color: " + color + ";");
            cageColorPointer += 3;
        } else if (cageColorPointer == 1) {
            String color = colors.get(1);
            for (TextField text : cage)
                text.setStyle("-fx-background-color: " + color + ";");
            setCageColor("-fx-background-color: " + color + ";");
            cageColorPointer += 3;
        } else if (cageColorPointer == 2) {
            String color = colors.get(2);
            for (TextField text : cage)
                text.setStyle("-fx-background-color: " + color + ";");
            setCageColor("-fx-background-color: " + color + ";");
            cageColorPointer += 3;
        } else if (cageColorPointer % 3 == 0) {
            String color = colors.get(cageColorPointer);
            for (TextField text : cage)
                text.setStyle("-fx-background-color: " + color + ";");
            setCageColor("-fx-background-color: " + color + ";");
            if (cageColorPointer == colors.size() - 3) cageColorPointer = 1;
            else {
                cageColorPointer += 3;
            }
        } else if ((cageColorPointer - 1) % 3 == 0) {
            String color = colors.get(cageColorPointer);
            for (TextField text : cage)
                text.setStyle("-fx-background-color: " + color + ";");
            setCageColor("-fx-background-color: " + color + ";");
            if (cageColorPointer == colors.size() - 2) cageColorPointer = 2;
            else {
                cageColorPointer += 3;
            }
        } else if ((cageColorPointer - 2) % 3 == 0) {
            String color = colors.get(cageColorPointer);
            for (TextField text : cage)
                text.setStyle("-fx-background-color: " + color + ";");
            setCageColor("-fx-background-color: " + color + ";");
            if (cageColorPointer == colors.size() - 1) cageColorPointer = 0;
            else {
                cageColorPointer += 3;
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

    public void setCageColorNotRandom(String color) {
        for (TextField text : cage)
            text.setStyle(color);
    }

    public void setCageTargetValue(String value) {
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
        for (TextField text : this.cage) {
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

    public String getCageColor() {
        return cageColor;
    }

    public void setCageColor(String cageColor) {
        this.cageColor = cageColor;
    }
    //endregion
}
