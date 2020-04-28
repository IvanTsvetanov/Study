package Puzzles;

/*
    Loads a puzzle from a text input.
 */

import HelperClasses.Cage;
import HelperClasses.GameLogic;
import HelperClasses.Toolbox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LoadFromText {
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
    private int sizeOfGrid = 0;
    //Used for detecting if an invalid file has been selected.
    private boolean invalidFieldInput = false;
    //Showing mistakes button flag for turning mode on/off.
    private boolean mistakesFlag = false;
    //Stores information about the timer.
    private static int minutes = 0;
    private static int seconds = 0;
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

    //Builds the scene from a text
    public Scene buildSceneFromText(String inputText) {

        //region Create a temp text file, containing the entered code, and then generate the game from it.
        try {
            PrintWriter writer = new PrintWriter("temp-storage.txt", "UTF-8");
            //Check for proper input
            if (inputText.contains("[a-zA-Z]+") == false && inputText.length() > 2) {
                writer.print(inputText);
            } else {
                new Alert(Alert.AlertType.ERROR,
                        "Invalid input. Please try again!")
                        .showAndWait();
            }

            writer.close();
        } catch (Exception z) {
            z.printStackTrace();
        }
        //endregion

        //region Get the Game Ready using the LoadFromFile class.
        String path = "temp-storage.txt";

        LoadFromFile load = new LoadFromFile();
        Scene mainScene = load.buildSceneFromFile(path);

        return mainScene;
    }
}
