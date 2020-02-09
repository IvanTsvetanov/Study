package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set the title
        primaryStage.setTitle("My Function Calculator");

        //Create panes
        //Main pane
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER_LEFT);
        mainPane.setHgap(20);
        mainPane.setVgap(20);
        mainPane.setPadding(new Insets(20, 20, 20, 20));

        //Other panes
        HBox startingValues = new HBox();
        startingValues.setSpacing(15);
        startingValues.setAlignment(Pos.CENTER);

        VBox plusMinusOne = new VBox();

        HBox endValues = new HBox();
        endValues.setSpacing(15);
        endValues.setAlignment(Pos.CENTER);

        //Remove later
        //mainPane.setGridLinesVisible(true);

        //Create components
        //In startingValues (HBox)
        Label x = new Label("x=");
        x.setMinWidth(15);
        TextField startingValue = new TextField("1");
        startingValue.prefWidthProperty().bind(mainPane.widthProperty());

        //In plusMinusOne (VBox)
        Button increment = new Button("+1");
        increment.setMinSize(32.4, 32.4);
        Button decrement = new Button("-1");
        decrement.setMinSize(32.4, 32.4);

        //Function text
        Label function = new Label("Function: ");
        function.setMinWidth(60);
        function.setAlignment(Pos.CENTER);

        //ComboBox containing functions
        ComboBox functions = new ComboBox();
        functions.getItems().addAll(
                "x*x",
                "sin(x)",
                "cos(x)",
                "Fibonacci(x)"
        );
        functions.setMinWidth(120);
        functions.setValue("x*x");

        //In endValues (HBox)
        Label equalsSign = new Label("=");
        equalsSign.setMinWidth(15);
        TextField endValue = new TextField("1");
        endValue.prefWidthProperty().bind(mainPane.widthProperty());

        //Add slider
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(9);
        slider.setValue(5);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(1);
        slider.setMinWidth(180);


        //Add Handlers and Listeners with lambda expressions
        //Change display values of buttons
        slider.valueProperty().addListener(e -> {
            int value = (int)slider.getValue();
            increment.setText("+" + Integer.toString(value));
            decrement.setText("-" + Integer.toString(value));
        });

        //Increment button
        increment.setOnAction(e -> {
            //Increase the starting value by x (value of button)
            int valueOfStart = Integer.parseInt(startingValue.getText());
            valueOfStart += Integer.parseInt(increment.getText());
            startingValue.setText(Integer.toString(valueOfStart));

            //Calculate with the given function
            switch (functions.getValue().toString()) {
                case "x*x":
                    endValue.setText(Integer.toString(valueOfStart*valueOfStart));
                    break;
                case "sin(x)":
                    endValue.setText(Double.toString(Math.sin(valueOfStart)));
                    break;
                case "cos(x)":
                    endValue.setText(Double.toString(Math.cos(valueOfStart)));
                    break;
                case "Fibonacci(x)":
                    endValue.setText(Integer.toString(fib(valueOfStart)));
                    break;
            }
        });

        //Decrement button
        decrement.setOnAction(e -> {
            //Decrease the starting value by 1
            int valueOfStart = Integer.parseInt(startingValue.getText());
            valueOfStart += Integer.parseInt(decrement.getText());
            startingValue.setText(Integer.toString(valueOfStart));

            //Calculate with the given function
            switch (functions.getValue().toString()) {
                case "x*x":
                    endValue.setText(Integer.toString(valueOfStart*valueOfStart));
                    break;
                case "sin(x)":
                    endValue.setText(Double.toString(Math.sin(valueOfStart)));
                    break;
                case "cos(x)":
                    endValue.setText(Double.toString(Math.cos(valueOfStart)));
                    break;
                case "Fibonacci(x)":
                    endValue.setText(Integer.toString(fib(valueOfStart)));
                    break;
            }
        });

        //Add components to panes
        startingValues.getChildren().add(x);
        startingValues.getChildren().add(startingValue);

        plusMinusOne.getChildren().add(increment);
        plusMinusOne.getChildren().add(decrement);

        endValues.getChildren().add(equalsSign);
        endValues.getChildren().add(endValue);

        mainPane.add(startingValues, 0, 2, 1, 1);
        mainPane.add(plusMinusOne, 1, 1, 1, 3);
        mainPane.add(function, 2, 2, 1, 1);
        mainPane.add(functions, 3, 0, 1, 5);
        mainPane.add(endValues, 4, 2, 1, 1);
        mainPane.add(slider, 5, 2, 1, 1);

        //Display
        Scene scene = new Scene(mainPane, 900, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    static int fib(int n)
    {
        int a = 0, b = 1, c;
        if (n == 0)
            return a;
        for (int i = 2; i <= n; i++)
        {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }
}
