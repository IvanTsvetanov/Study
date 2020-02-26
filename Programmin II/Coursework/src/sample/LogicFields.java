package sample;

import javafx.scene.control.TextField;

import java.util.ArrayList;

/*
Implements the game logic
 */
public class LogicFields {
    //region Fields
    private TextField gameField;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private ArrayList<Cluster> clusters = new ArrayList<>();
    //endregion

    //region Constructors
    public LogicFields() {
    }

    public LogicFields(TextField gameField) {
        this.gameField = gameField;
        textFields.add(gameField);
    }
    //endregion

    //region Getters and Setters
    public TextField getGameField() {
        return gameField;
    }

    public void setGameField(TextField gameField) {
        this.gameField = gameField;
        this.textFields.add(gameField);
    }

    public ArrayList<TextField> getTextFields() {
        return this.textFields;
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }

    public void addClusters(Cluster cluster) {
        this.clusters.add(cluster);
    }
    //endregion

    //region Methods

    //endregion
}
