package sortingnetworks;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {
    private TextField inputField;
    private Label outputLabel;
    private TextArea historyArea;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Network App");

        Label inputLabel = new Label("Introduceți numere separate prin virgulă:");
        inputField = new TextField();
        Button sortButton = new Button("Sortează");
        sortButton.setOnAction(e -> sortNumbers());

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

        DatabaseHelper.createTable();
    }

    private void saveSortedNumbers(int[] numbers) {
        StringBuilder result = new StringBuilder();
        for (int num : numbers) {
            result.append(num).append(" ");
        }
        String sortedNumbersStr = result.toString().trim();

        String sql = "INSERT INTO sorted_numbers (numbers) VALUES (?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:sorting_networks.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sortedNumbersStr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        List<Integer> sortedNumbers = SortingNetworks.sort(numbers);
        String sortedNumbersStr = sortedNumbers.toString();

        outputLabel.setText("Tabloul sortat: " + sortedNumbersStr);
        DatabaseHelper.insertSorting(input, sortedNumbersStr);
    }

    private void loadHistory() {
        List<String> history = DatabaseHelper.getHistory();
        StringBuilder historyBuilder = new StringBuilder();
        for (String entry : history) {
            historyBuilder.append(entry).append("\n");
        }
        String historyStr = historyBuilder.toString();
        historyArea.setText(historyStr);
    }

    public static void main(String[] args) {
        launch(args);
    }
}