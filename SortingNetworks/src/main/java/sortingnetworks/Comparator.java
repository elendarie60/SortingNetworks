package sortingnetworks;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Comparator {

    private final int from;
    private final int to;
    private Line line;


    public Comparator(int from, int to, double yOffset, double yInterval, double xPosition) {
        this.from = from;
        this.to = to;
        this.line = new Line(xPosition, yOffset + (from * yInterval), xPosition, yOffset + (to * yInterval));
        this.line.setStroke(Color.RED);
    }

    public Comparator(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Line getLine() {
        return line;
    }

    public void compareAndSwap(int[] array) {
        if (array[from] > array[to]) {
            int temp = array[from];
            array[from] = array[to];
            array[to] = temp;
        }
    }
}
