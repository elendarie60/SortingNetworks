package sortingnetworks;

import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private TextField inputField;
    private Label outputLabel;
    private TextArea historyArea;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Networks App");

        Label inputLabel = new Label("Introduceți numere separate prin virgulă:");
        inputField = new TextField();
        Button sortButton = new Button("Sortează");
        sortButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    sortNumbers();
                    openSortingVisualizer();
                } catch (Exception e) {
                    showAlert("Eroare", "A apărut o eroare: " + e.getMessage());
                }
            }
        });

        outputLabel = new Label();
        historyArea = new TextArea();
        historyArea.setEditable(false);
        Button loadButton = new Button("Încarcă Istoricul");
        loadButton.setOnAction(e -> loadHistory());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(inputLabel, inputField, sortButton, outputLabel, loadButton, historyArea);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveSortedNumbers(int[] numbers) {
        StringBuilder result = new StringBuilder();
        for (int num : numbers) {
            result.append(num).append(" ");
        }
        String sortedNumbersStr = result.toString().trim();

        try (FileWriter writer = new FileWriter("sorted_numbers.txt")) {
            writer.write(sortedNumbersStr);
        } catch (IOException e) {
            showAlert("Eroare", "Nu s-a putut salva rezultatul sortării: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateOutputLabel(int[] numbers) {
        StringBuilder result = new StringBuilder();
        for (int num : numbers) {
            result.append(num).append(" ");
        }
        String sortedNumbersStr = result.toString().trim();
        javafx.application.Platform.runLater(() -> outputLabel.setText("Tabloul sortat: " + sortedNumbersStr));
    }

    private void sortNumbers() {
        String input = inputField.getText();
        if (!input.matches("^-?\\d+(,-?\\d+)*$")) {
            outputLabel.setText("Introduceți numere valide separate prin virgulă!");
            return;
        }
        String[] numbersStr = input.split(",");
        int[] numbers = new int[numbersStr.length];
        for (int i = 0; i < numbersStr.length; i++) {
        
                numbers[i] = Integer.parseInt(numbersStr[i].trim());
        }

        double yOffset = 10;
        double yInterval = 30;
        double xPosition = 200;
        int numElements = numbers.length;
        SortingNetworks sortingNetworks = new SortingNetworks(yOffset, yInterval, xPosition, numElements);
        outputLabel.setText("Tabloul sortat: " + Arrays.toString(numbers));

       // sortingNetworks.sort(numbers);
       // updateOutputLabel(numbers);
        //saveSortedNumbers(numbers);
    }

    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("history.txt"))) {
            StringBuilder historyBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                historyBuilder.append(line).append("\n");
            }
            String historyStr = historyBuilder.toString();
            historyArea.setText(historyStr);
        } catch (IOException e) {
            showAlert("Eroare", "Nu s-a putut încărca istoricul: " + e.getMessage());
        }
    }

    private void openSortingVisualizer() {
        String input = inputField.getText();
        if (!input.matches("^-?\\d+(,-?\\d+)*$")) {
            outputLabel.setText("Introduceți numere valide separate prin virgulă!");
            return;
        }
        String[] numbersStr = input.split(",");
        int[] numbers = new int[numbersStr.length];
        for (int i = 0; i < numbersStr.length; i++) {
                numbers[i] = Integer.parseInt(numbersStr[i].trim());
        }

        SortingVisualizer visualizer = new SortingVisualizer(numbers);
        visualizer.show(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
