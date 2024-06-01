package sortingnetworks;
import java.util.List;
import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {
    private TextField inputField;
    private Label outputLabel;
    private TextArea historyArea;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Network App");

        Label inputLabel = new Label("Introduceți numere separate prin virgulă:");
        inputField = new TextField();
        Button sortButton = new Button("Sortează");
        sortButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                sortNumbers();
                openSortingVisualizer();
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
            e.printStackTrace();
            // Handle file writing error message to the user
            showAlert("Error", "Failed to save sorted numbers: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
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
        if (!NumberInputValidator.isValidInput(input)) {
            outputLabel.setText("Introduceți numere valide separate prin virgulă!");
            return;
        }
        String[] numbersStr = input.split(",");
        int[] numbers = new int[numbersStr.length];
        for (int i = 0; i < numbersStr.length; i++) {
            numbers[i] = Integer.parseInt(numbersStr[i].trim());
        }

        // Create an instance of SortingNetworks and sort the numbers
        SortingNetworks sortingNetworks = new SortingNetworks(10, 30, 50); // Example parameters
        // Update output label
        outputLabel.setText("Tabloul sortat: " + Arrays.toString(numbers));
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
            e.printStackTrace();
            // Handle file reading error
            System.err.println("Failed to load history: " + e.getMessage());
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
