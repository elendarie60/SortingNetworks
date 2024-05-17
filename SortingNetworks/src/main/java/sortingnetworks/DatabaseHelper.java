package sortingnetworks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String URL = "jdbc:postgresql://localhost:5432/sorting_networks_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS sorting_networks ("
                + "id SERIAL PRIMARY KEY,"
                + "numbers VARCHAR(255) NOT NULL,"
                + "sorted_numbers VARCHAR(255) NOT NULL"
                + ")";
    }

    public static void insertSorting(String numbers, String sortedNumbers) {
        String sql = "INSERT INTO sorting_networks (numbers, sorted_numbers) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numbers);
            pstmt.setString(2, sortedNumbers);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getHistory() {
        List<String> history = new ArrayList<>();
        String sql = "SELECT numbers, sorted_numbers FROM sorting_networks";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String numbers = rs.getString("numbers");
                String sortedNumbers = rs.getString("sorted_numbers");
                history.add("Numbers: " + numbers + ", Sorted: " + sortedNumbers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}