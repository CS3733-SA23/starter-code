package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import pathfinding.HospitalNode;


public class DatabaseController {
    public static void main(String[] args) {
        DatabaseController DBC1 = new DatabaseController();
        DBC1.connectToDatabase("teame", "teame50");
    }

    private void connectToDatabase(String username, String password) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c =
                    DriverManager.getConnection(
                            "jdbc:postgresql://database.cs.wpi.edu:5432/teamedb", username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
