package sample;

import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Cluster {
    //region Fields
    private ArrayList<TextField> cluster = new ArrayList<>();
    private boolean isSolved = false;

    //Generate a random color for each Cluster
    private Random rand = new Random();
    private static int bound = 16;
    private static List<String> colors = new ArrayList<>() {
        {
            add("lightblue");
            add("orange");
            add("yellow");
            add("aliceblue");
            add("beige");
            add("blueviolet");
            add("brown");
            add("cyan");
            add("darkgreen");
            add("grey");
            add("khaki");
            add("yellowgreen");
            add("wheat");
            add("slateblue");
            add("salmon");
            add("gold");
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
        int num = rand.nextInt(bound);

        String color = colors.get(num);
        for(TextField text : cluster)
        text.setStyle("-fx-background-color: " + color + ";");

        colors.remove(num);
        bound--;
    }

    public void setClusterTargetValue(String value) {
        TextField targetValue = new TextField(value);
    }
    //endregion
}
