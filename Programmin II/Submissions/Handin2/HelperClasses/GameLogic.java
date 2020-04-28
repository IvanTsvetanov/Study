package HelperClasses;

/*
    A class that manages the game logic and stores the cages.
 */

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameLogic {
    //Holds the size of the game grid.
    private int size;

    //Holds all the textFields of the game grid.
    private ArrayList<TextField> gameFields = new ArrayList<>();

    //Holds all the cages of the game grid.
    private ArrayList<Cage> cages = new ArrayList<>();

    //Holds all the target values of the cages of the game grid.
    private ArrayList<Text> targetValues = new ArrayList<>();

    //Holds all the textFields, but this time, in a two-dimensional array for easier access
    // for certain operations.
    private TextField[][] textFieldHolder;

    //Holds the specific values of the game fields.
    private int[][] valueHolder;

    //Sets the size of the game grid.
    public void setSize(int size) {
        this.size = size;
        textFieldHolder = new TextField[size][size];
        valueHolder  = new int[size][size];
    }

    //Adds a textField to the textFields holder of the game grid.
    public void addGameField(TextField gameField) {
        this.gameFields.add(gameField);
    }

    //Returns all the textFields of the game grid.
    public ArrayList<TextField> getGameFields() {
        return this.gameFields;
    }

    //Adds a cage.
    public void addCage(Cage cage) {
        this.cages.add(cage);
    }

    //Returns all cages.
    public ArrayList<Cage> getCages() {
        return this.cages;
    }

    //Adds a target value.
    public void addTargetValue(Text text) {
        this.targetValues.add(text);
    }

    //Returns all target values.
    public ArrayList<Text> getTargetValues() {
        return this.targetValues;
    }

    //Adds a game field to the array of game fields.
    public void addToGameFieldArray(int i, int j, TextField gameField) {
        textFieldHolder[i][j] = gameField;
    }

    //Returns a specified game field from the array of game fields.
    public TextField getGameField(int i, int j) {
        return textFieldHolder[i][j];
    }

    //Returns a specified value from a game field.
    public int getValue(int i, int j) {
        return valueHolder[i][j];
    }

    //Sets a value to a specified game field.
    public void setValue(int i, int j, int value) {
        valueHolder[i][j] = value;
    }

    //Returns all the values of the game grid as a two-dimensional matrix
    public int[][] getValueHolder() {
        return valueHolder;
    }

    //Returns the index (in the arraylist) of a given game fields.
    public int getIndexOfGivenGameField(TextField textField) {
        for(int i = 0; i < getGameFields().size(); i++) {
            if(getGameFields().get(i).equals(textField)) return i;
        }
        return 0;
    }

}
