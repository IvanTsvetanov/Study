package sample;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

/*
Implements the game logic
 */
public class LogicFields {
    private TextField gameField;
    private ArrayList<TextField> textFields = new ArrayList<>();

    public LogicFields() {
    }

    public LogicFields(TextField gameField) {
        this.gameField = gameField;
        textFields.add(gameField);
    }

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
}
