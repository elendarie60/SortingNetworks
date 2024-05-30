package org.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SortingVisualizer extends Application {
    private int[] array;

    public SortingVisualizer(int[] array) {
        this.array = array;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sorting Network Visualization");

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawSortingNetwork(gc, array);

        Pane root = new Pane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    private void drawSortingNetwork(GraphicsContext gc, int[] array) {
        int width = 800;
        int height = 600;
        int n = array.length;

        // Configurăm dimensiunile
        int step = width / (n + 1);
        int verticalStep = height / (n + 1);

        // Desenăm elementele array-ului
        for (int i = 0; i < n; i++) {
            gc.strokeText(String.valueOf(array[i]), (i + 1) * step, 50);
        }

        // Generăm și desenăm comparatoarele
        int[][] comparators = generateSortingNetwork(n);
        for (int[] comparator : comparators) {
            int x1 = (comparator[0] + 1) * step;
            int x2 = (comparator[1] + 1) * step;
            int y = (comparator[0] + 1) * verticalStep;

            gc.strokeLine(x1, y, x2, y);
        }
    }

    private static int[][] generateSortingNetwork(int n) {
        List<int[]> comparatorsList = new ArrayList<>();
        for (int i = n / 2; i > 0; i /= 2) {
            for (int j = i; j < n - i; j += i * 2) {
                for (int k = 0; k < i; k++) {
                    int[] comparator = {j + k, j + k + i};
                    comparatorsList.add(comparator);
                }
            }
        }
        return comparatorsList.toArray(new int[0][]);
    }
}

