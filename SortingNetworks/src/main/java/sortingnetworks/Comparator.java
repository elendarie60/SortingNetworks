package sortingnetworks;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Comparator {
    private final int i;
    private final int j;
    private final double yOffset;
    private final double yInterval;
    private final double xPosition;
    private final int dir;
    private Line line;

    public Comparator(int i, int j, double yOffset, double yInterval, double xPosition, int dir, int arrayLength) {
        this.i = i; this.j = j;
        this.yOffset = yOffset;
        this.yInterval = yInterval;
        this.xPosition = xPosition;
        this.dir = dir;
        this.line = new Line(xPosition, yOffset + (this.i * yInterval), xPosition, yOffset + (this.j * yInterval));
        this.line.setStroke(Color.RED);
    }

    public void compareAndSwap(int[] array) {
        if (i >= 0 && i < array.length && j >= 0 && j < array.length) {
            if ((array[i] > array[j]) == (dir == 1)) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        } else {
            System.err.println("index: i=" + i + ", j=" + j);
        }
    }
}
