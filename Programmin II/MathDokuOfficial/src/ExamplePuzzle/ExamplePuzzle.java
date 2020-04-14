package ExamplePuzzle;

import HelperClasses.Cage;
import HelperClasses.GameLogic;
import HelperClasses.Toolbox;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ExamplePuzzle  {
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

    public Scene buildExampleScene(){

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

        Button solve = new Button("Solve");
        solve.setMinSize(100, 30);
        solve.prefWidthProperty().bind(mainPane.widthProperty());
        solve.setMaxSize(250, 60);

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
        bottomComponents.add(solve, 2, 0, 1, 1);
        bottomComponents.add(timerLabel, 2, 1, 1, 1);
        bottomComponents.setAlignment(Pos.TOP_LEFT);

        bottomComponents.setVgap(20);
        bottomComponents.setHgap(80);

        //Add the side elements and format them
        GridPane sideComponents = new GridPane();

        sideComponents.setPadding(new Insets(10, 10, 10, 10));
        sideComponents.setVgap(20);
        sideComponents.setHgap(20);

        int minHeight = (int) (sideComponents.heightProperty().getValue() / 50);
        Button loadFile = new Button("Load Puzzle from File");
        loadFile.setMinSize(150, 30);
        loadFile.prefHeightProperty().bind(mainPane.heightProperty());
        loadFile.setMaxSize(150, 50);

        Button loadText = new Button("Load Puzzle from Text");
        loadText.setMinSize(150, 30);
        loadText.prefHeightProperty().bind(mainPane.heightProperty());
        loadText.setMaxSize(150, 50);

        Button changeFont = new Button("Change Font");
        changeFont.setMinSize(150, 30);
        changeFont.prefHeightProperty().bind(mainPane.heightProperty());
        changeFont.setMaxSize(150, 50);

        sideComponents.add(loadFile, 0, 0, 2, 1);
        sideComponents.add(loadText, 0, 1, 2, 1);
        sideComponents.add(changeFont, 0, 2, 2, 1);

        //Add randomly generate button
        HBox randHbox = new HBox();
        Button randGen = new Button("Randomly Generate Puzzle");
        randGen.setMinSize(130, 80);
        randHbox.getChildren().add(randGen);
        randHbox.setAlignment(Pos.CENTER);
        //endregion

        //region Create and add dial buttons
        int x = 0;
        int y = 3;
        for(int i = 1; i < 9; i++) {
            //Create the button
            Button button = toolbox.createDialButton(Integer.toString(i));
            button.prefWidthProperty().bind(sideComponents.widthProperty());
            button.prefHeightProperty().bind(sideComponents.heightProperty());
            //Add the buttons to a list for easy access
            playButtons.add(button);

            //Add buttons to the grid
            if(i % 2 != 0 ) {
                x = 0;
                sideComponents.add(button, x, y, 1, 1);
            }

            else {
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
        mainPane.add(randHbox, 1, 1, 1, 1);
        //endregion

        //region Draw Game Grid
        int width = 60;
        int sizeOfGrid = 4;
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
                text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

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
                            if (button.getText() != null)
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
        //endregion

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

        Scene mainScene = new Scene(mainPane, 670, 600);
        return mainScene;
    }
}

