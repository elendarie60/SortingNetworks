package sortingnetworks;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class SortingVisualizer {
    private final int[] array;
    private final SortingNetworks sortingNetwork;
    private Pane pane;
    private Label[] labels;

    public SortingVisualizer(int[] array) {
        this.array = array.clone();
        double yOffset = 30;
        double yInterval = 30;
        double xPosition = 200; // Initial x position for comparators
        this.sortingNetwork = new SortingNetworks(yOffset, yInterval, xPosition);
    }

    public void show(Stage stage) {
        pane = new Pane();
        pane.setPadding(new Insets(10));

        labels = new Label[array.length];

        double yOffset = 30;
        double yInterval = 30;
        double width = 300;

        for (int i = 0; i < array.length; i++) {
            Line line = new Line(40, yOffset + (i * yInterval), width, yOffset + (i * yInterval));
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);

            labels[i] = new Label(Integer.toString(array[i]));
            labels[i].setLayoutX(0);
            labels[i].setLayoutY(yOffset + (i * yInterval) - 10);
            labels[i].setPadding(new Insets(0, 10, 0, 10));
            pane.getChildren().add(labels[i]);
        }

        for (Comparator comparator : sortingNetwork.getComparators()) {
            pane.getChildren().add(comparator.getLine());
        }

        Scene scene = new Scene(pane, 400, array.length * yInterval + 40);
        stage.setScene(scene);
        stage.show();

        new Thread(this::runVisualization).start();
    }

    private void runVisualization() {
        try {
            for (Comparator comparator : sortingNetwork.getComparators()) {
                Platform.runLater(() -> {
                    comparator.getLine().setStroke(Color.GREEN);
                });

                Thread.sleep(1000); // Delay for visualization

                comparator.compareAndSwap(array);
                int index1 = comparator.getFrom();
                int index2 = comparator.getTo();

                Platform.runLater(() -> {
                    labels[index1].setText(Integer.toString(array[index1]));
                    labels[index2].setText(Integer.toString(array[index2]));
                });

                Thread.sleep(1000); // Delay for visualization

                Platform.runLater(() -> {
                    comparator.getLine().setStroke(Color.RED);
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

