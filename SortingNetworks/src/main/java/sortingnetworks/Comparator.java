package sortingnetworks;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Comparator {
    private  int i;
    private  int j;
     private  int from;
    private  int to;
    private  double yOffset;
    private  double yInterval;
    private  double xPosition;
    private  int dir;
        private int direction;

    private Line line;

    public Comparator(int from, int to, double yOffset, double yInterval, double xPosition, int direction) {
        this.i = i; this.j = j;
        this.yOffset = yOffset;
         this.from = from;
        this.to = to;
        this.yInterval = yInterval;
        this.xPosition = xPosition;
        this.dir = dir;
         double startX = xPosition;
        double startY = yOffset+from * yInterval;
        double endX = xPosition;
        double endY = yOffset+to*yInterval;
        this.direction = direction;

        // Initialize the visual representation of the comparator (if using a GUI)
        this.line = new Line(startX,startY,endX,endY);
       // this.line = new Line(xPosition, yOffset + (this.i * yInterval), xPosition, yOffset + (this.j * yInterval));
        this.line.setStroke(Color.GREEN);
    }

    public int  compareAndSwap(int[] array) {
        if (i >= 0 && i < array.length && j >= 0 && j < array.length) {
            if ((array[i] > array[j]) == (dir == 1)) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                return 1;
            }
        } 
        return 0;
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
}
