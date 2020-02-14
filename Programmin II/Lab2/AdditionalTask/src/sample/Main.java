package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Random;

/**
 * Creates a scene with a ball inside.
 *
 * @author jan
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Circle ball;
    private Scene scene;
    private boolean goesNorth = false;
    private boolean goesSouth = false;
    private boolean goesWest = false;
    private boolean goesEast = false;
    private boolean runPosThread = true;

    @Override
    public void start(Stage stage) {
        ball = new Circle();
        ball.setRadius(25.0f);
        ball.setFill(Color.BURLYWOOD);
        ball.setStrokeWidth(20);

        //Change the color of the ball whenever it is clicked
        //Create the random color also
        ball.setOnMouseClicked(e -> {
            Random random = new Random();

            final float hue = random.nextFloat();
            // Saturation between 0.1 and 0.3
            final float saturation = (random.nextInt(2000) + 1000) / 10000f;
            final float luminance = 0.9f;
            final Color color = Color.color(hue, saturation, luminance);
            ball.setFill(color);
        });

        Group root = new Group(ball);
        scene = new Scene(root, 600, 300);
        scene.setFill(Color.BEIGE);
        stage.setTitle("Ball Area");
        stage.setScene(scene);
        startPositionThread();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                runPosThread = false;
            }
        });
        stage.show();
        ball.setCenterX(scene.getWidth() / 2);
        ball.setCenterY(scene.getHeight() / 2);

        //Isn't FINISHED!
        ball.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            System.out.println("event");
                if (e.getCode() == KeyCode.A) {
                ball.setCenterX(ball.getCenterX() - 1);
                System.out.println("left");
            }
        });
    }

    private void startPositionThread() {
        Runnable posChanger = new Runnable() {

            @Override
            public void run() {
                while (runPosThread) {
                    if (goesNorth && !goesSouth) {
                        // This statement ensures that the ball is updated on
                        // the JavaFX application thread.
                        Platform.runLater(
                                () -> ball.setCenterY(ball.getCenterY() - 2.5));
                    }
                    if (goesSouth && !goesNorth) {
                        Platform.runLater(
                                () -> ball.setCenterY(ball.getCenterY() + 2.5));
                    }
                    if (goesWest && !goesEast) {
                        Platform.runLater(
                                () -> ball.setCenterX(ball.getCenterX() - 2.5));
                    }
                    if (goesEast && !goesWest) {
                        Platform.runLater(
                                () -> ball.setCenterX(ball.getCenterX() + 2.5));
                    }
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread posThread = new Thread(posChanger);
        posThread.start();
    }

    /**
     * Sets a new colour for the ball.
     *
     * @param colour
     */
    public void setBallColour(Color colour) {
        ball.setFill(colour);
    }

    /**
     * @return the goesNorth
     */
    public boolean isGoesNorth() {
        return goesNorth;
    }

    /**
     * Switches if the ball moves north or not.
     *
     * @param goesNorth true for moving north.
     */
    public void setGoesNorth(boolean goesNorth) {
        this.goesNorth = goesNorth;
    }

    /**
     * @return the goesSouth
     */
    public boolean isGoesSouth() {
        return goesSouth;
    }

    /**
     * Switches if the ball moves south or not.
     *
     * @param goesSouth true for moving south.
     */
    public void setGoesSouth(boolean goesSouth) {
        this.goesSouth = goesSouth;
    }

    /**
     * @return the goesWest
     */
    public boolean isGoesWest() {
        return goesWest;
    }

    /**
     * Switches if the ball moves west or not.
     *
     * @param goesWest true for moving west.
     */
    public void setGoesWest(boolean goesWest) {
        this.goesWest = goesWest;
    }

    /**
     * @return the goesEast
     */
    public boolean isGoesEast() {
        return goesEast;
    }

    /**
     * Switches if the ball moves east or not.
     *
     * @param goesEast true for moving east.
     */
    public void setGoesEast(boolean goesEast) {
        this.goesEast = goesEast;
    }

    /**
     * Registers an event filter to the ball. The filter is called when the ball
     * receives an Event of the specified type during the capturing phase of
     * event delivery.
     *
     * @param eventType   the type of the events to receive by the filter
     * @param eventFilter the filter to register
     */
    public void addBallEventFilter(EventType<MouseEvent> eventType,
                                   EventHandler<MouseEvent> eventFilter) {
        ball.addEventFilter(eventType, eventFilter);
    }

    /**
     * Sets the value of the property onKeyPressed.
     * <p>
     * Defines a function to be called when some Node of this Scene has input
     * focus and a key has been pressed. The function is called only if the
     * event hasn't been already consumed during its capturing or bubbling
     * phase.
     *
     * @param keyEvent
     */
    public void setOnKeyPressed(EventHandler<KeyEvent> keyEvent) {
        scene.setOnKeyPressed(keyEvent);
    }

    /**
     * Sets the value of the property onKeyPressed.
     * <p>
     * Defines a function to be called when some Node of this Scene has input
     * focus and a key has been pressed. The function is called only if the
     * event hasn't been already consumed during its capturing or bubbling
     * phase.
     *
     * @param keyEvent
     */
    public void setOnKeyReleased(EventHandler<KeyEvent> keyEvent) {
        scene.setOnKeyReleased(keyEvent);
    }
}
