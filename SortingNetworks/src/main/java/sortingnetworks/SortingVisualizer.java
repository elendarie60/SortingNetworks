package sortingnetworks;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class SortingVisualizer {
    private final int[] array;
    private final SortingNetworks sortingNetwork;
    private Pane pane;
    private Label[] labels;
    private Line[] horizontalLines;
    private Line[] dottedLines;
    private final Color[] dottedLineColors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PURPLE};

    public SortingVisualizer(int[] array) {
        this.array = array.clone();
        double yOffset = 30;
        double yInterval = 30;
        double xPosition = 200; // pozitia initiala a lui x pt comparatori
        this.sortingNetwork = new SortingNetworks(yOffset, yInterval, xPosition, array.length);
        this.horizontalLines = new Line[array.length];
        this.dottedLines = new Line[array.length];
    }

    public void show(Stage stage) {
        pane = new Pane();
        pane.setPadding(new Insets(10));

        labels = new Label[array.length];
        horizontalLines = new Line[array.length]; // linii orizontale

        double yOffset = 30;
        double yInterval = 30;
        double width = 300;
        double dottedLineYOffset = 5;

        for (int i = 0; i < array.length; i++) {
            Line line = new Line(40, yOffset + (i * yInterval), width, yOffset + (i * yInterval));
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);

            labels[i] = new Label(Integer.toString(array[i]));
            labels[i].setLayoutX(0);
            labels[i].setLayoutY(yOffset + (i * yInterval) - 10);
            labels[i].setPadding(new Insets(0, 10, 0, 10));
            pane.getChildren().add(labels[i]);

            dottedLines[i] = new Line(40, yOffset + (i * yInterval) + dottedLineYOffset, 40, yOffset + (i * yInterval) + dottedLineYOffset);
            dottedLines[i].setStroke(dottedLineColors[i % dottedLineColors.length]); // red change
            dottedLines[i].getStrokeDashArray().addAll(5d, 5d); // linia punctata
            pane.getChildren().add(dottedLines[i]);
        }

        for (SortingNetworks.Comparator comparator : sortingNetwork.getComparators()) {
            pane.getChildren().add(comparator.getLine());
        }

        Scene scene = new Scene(pane, 400, array.length * yInterval + 40);
        stage.setScene(scene);
        stage.show();

        new Thread(this::runVisualization).start();
    }

    private void runVisualization() {
        try {
            for (SortingNetworks.Comparator comparator : sortingNetwork.getComparators()) {
                Platform.runLater(() -> {
                    comparator.getLine().setStroke(Color.GREEN);
                });

                Thread.sleep(1000);

                comparator.compareAndSwap(array);
                int index1 = comparator.getI();
                int index2 = comparator.getJ();

                Platform.runLater(() -> {
                    labels[index1].setText(Integer.toString(array[index1]));
                    labels[index2].setText(Integer.toString(array[index2]));
                    double comparisonX = comparator.getLine().getStartX();
                    dottedLines[index1].setEndX(comparisonX); // linia punctata update
                    dottedLines[index2].setEndX(comparisonX);
                    double tempY = dottedLines[index1].getStartY();
                    dottedLines[index1].setStartY(dottedLines[index2].getStartY());
                    dottedLines[index2].setStartY(tempY);
                });

                Thread.sleep(1000); // Delay

                Platform.runLater(() -> {
                    comparator.getLine().setStroke(Color.RED);
                });
            }

            // Vectorul final sortat
            Platform.runLater(() -> {
                for (int i = 0; i < array.length; i++) {
                    labels[i].setText(Integer.toString(array[i]));
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
