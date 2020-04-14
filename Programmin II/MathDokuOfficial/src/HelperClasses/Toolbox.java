package HelperClasses;

import javafx.scene.control.Button;

/*
    A helper class built to assist in creating the MathDoku Puzzles.
 */

public class Toolbox {
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

    public Cage normaliseCage (Cage cage, GameLogic gameLogic, String targetValue, int numTargetValue) {
        cage.setCageColor();
        gameLogic.addCage(cage);
        gameLogic.getTargetValues().get(numTargetValue).setText(targetValue);
        cage.setCageTargetValue(gameLogic.getTargetValues().get(numTargetValue).getText());

        return cage;
    }
}
