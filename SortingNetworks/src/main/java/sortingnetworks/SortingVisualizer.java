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
    private  int[] array;
        private int k = 0;

    private SortingNetworks sortingNetwork;
    private Pane pane;
    private Label[] labels;
    private Line[] horizontalLines;
        private List<Line> dottedLine;

    private final Color[] dottedLineColors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PURPLE,Color.YELLOW,Color.PINK,Color.AQUA,
            Color.BROWN,Color.CORAL,Color.DARKGREY,Color.FUCHSIA,Color.GOLD,Color.HONEYDEW,Color.IVORY,Color.KHAKI,Color.LIGHTBLUE,Color.MAGENTA};

    public SortingVisualizer(int[] array) {
        this.array = array.clone();
        double yOffset = 30;
        double yInterval = 30;
        double xPosition = 200; // pozitia initiala a lui x pt comparatori
        this.sortingNetwork = new SortingNetworks(yOffset, yInterval, xPosition, array.length);
        this.horizontalLines = new Line[array.length];
        this.dottedLines = new ArrayList<>();
    }

    public void show(Stage stage) {
        pane = new Pane();
        pane.setPadding(new Insets(10));

        labels = new Label[array.length];
        horizontalLines = new Line[array.length]; // linii orizontale
 int numElements = array.length;
        double newWidth;
        double yOffset = 30;
        double yInterval = 30;
        double width = 150;
        //double dottedLineYOffset = 5;

        for (int i = 0; i < array.length; i++) {
             if (numElements > 4) {
                newWidth = (width * (numElements / 2));
            } else {
                newWidth = 300;
            }
            Line line = new Line(40, yOffset + (i * yInterval), neWidth, yOffset + (i * yInterval));
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);

            labels[i] = new Label(Integer.toString(array[i]));
            labels[i].setLayoutX(15);
            labels[i].setLayoutY(yOffset + (i * yInterval) - 10);
            labels[i].setPadding(new Insets(0, 10, 0, 10));
            pane.getChildren().add(labels[i]);
//afisare din dreapta
            labels[i] = new Label(Integer.toString(array[i]));
            labels[i].setLayoutX(newWidth + 5);
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
                         Line[] lastHorizontalLine = {null};
             for(Comparator comparator : sortingNetworks.getComparators()){
               // comparator.compareAndSwap(array);
                int index1 = comparator.getFrom();
                int index2 = comparator.getTo();
                  double comparatorX = comparator.getLine().getStartX();
                double initialY1 = 30 + (index1 * 30) + 5;
                double initialY2 = 30 + (index2 * 30) + 5;

                Platform.runLater(() -> {
                                        comparator.getLine().setStroke(Color.GREEN);
                });
                                 k = comparator.compareAndSwap(array);

               // Thread.sleep(1000); // Delay

                Platform.runLater(() -> {
                     labels[index1].setText(Integer.toString(array[index1]));
                    labels[index2].setText(Integer.toString(array[index2]));
double finalX = comparator.getLine().getEndX() + 20;
                    double finalY1 = 30 + (index1 * 30) + 5;
                    double finalY2 = 30 + (index2 * 30) + 5;
                     if (k == 1) {
                        // if (array[index1] != comparator.getFrom()) {
                        Line obliqueLine1 = new Line(comparatorX, initialY1, finalX, finalY2);
                        obliqueLine1.setStroke(dottedLineColors[dottedLine.size() % dottedLineColors.length]);
                        obliqueLine1.getStrokeDashArray().addAll(5d, 5d);
                        dottedLine.add(obliqueLine1);
                        pane.getChildren().add(obliqueLine1);
                        //}

                        //if (array[index2] != comparator.getTo()) {
                        Line obliqueLine2 = new Line(comparatorX, initialY2, finalX, finalY1);
                        obliqueLine2.setStroke(dottedLineColors[dottedLine.size() % dottedLineColors.length]);
                        obliqueLine2.getStrokeDashArray().addAll(5d, 5d);
                        dottedLine.add(obliqueLine2);
                        pane.getChildren().add(obliqueLine2);
                     }
                     if (lastHorizontalLine[0] != null) { // Verifica daca exista o linie orizontala anterioara
                            lastHorizontalLine[0].setEndX(finalX); // Ajusteaza finalul liniei
                        }
                          addHorizontalLines(index1,index2);
                });
                          Thread.sleep(2000); // Delay for visualization

                Platform.runLater(() -> {
                    comparator.getLine().setStroke(Color.RED);
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     private void addHorizontalLines(int index1, int index2) {
        double yOffset = 30;
        double yInterval = 30;
        double startX = 40;
        double endX = 200; // End X position for the horizontal dotted lines
        double offset = 10;
        Line horizontalLine1 = new Line(startX, yOffset + (index1 * yInterval) + offset, 140, yOffset + (index1 * yInterval) + offset);
        horizontalLine1.setStroke(dottedLineColors[index1 % dottedLineColors.length]);
        horizontalLine1.getStrokeDashArray().addAll(5d, 5d);
        pane.getChildren().add(horizontalLine1);

      /*  Line horizontalLine1 = new Line(startX, yOffset + (index1 * yInterval) + offset, endX, yOffset + (index1 * yInterval) + offset);
        horizontalLine1.setStroke(dottedLineColors[index1 % dottedLineColors.length]);
        horizontalLine1.getStrokeDashArray().addAll(5d, 5d);
        pane.getChildren().add(horizontalLine1);*/
        for (Comparator comparator : sortingNetwork.getComparators()) {
            if (comparator.getFrom() == index1 || comparator.getTo() == index1) {
                double comparatorX = comparator.getLine().getStartX();
                Line horizontalLine2 = new Line(startX, yOffset + (index2 * yInterval) + offset, comparatorX, yOffset + (index2 * yInterval) + offset);
                horizontalLine2.setStroke(dottedLineColors[index1 % dottedLineColors.length]);
                horizontalLine2.getStrokeDashArray().addAll(5d, 5d);
                pane.getChildren().add(horizontalLine2);
                break;
            }
        }
    }


