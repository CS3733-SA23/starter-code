package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseController {
  public static void main(String[] args) {
    DatabaseController DBC1 = new DatabaseController();
    Connection c = DBC1.connectToDatabase("teame", "teame50");
  }

  private Connection connectToDatabase(String username, String password) {
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
    return c;
  }

  private void deleteFromTable(Connection c) {
    try {
      Statement stmt = null;

      Scanner s1 = new Scanner(System.in);
      System.out.println("Which table would you like to delete from (Nodes, Edges): ");
      String tabletoEdit = s1.nextLine();

      if (tabletoEdit.equals("Nodes")) {
        System.out.println("Please type the Node ID you would like to delete: ");
        String nodetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM L1Nodes WHERE nodeID = " + nodetoDelete;
        stmt.execute(sql);
        stmt.close();

      } else {
        System.out.println("Please type the Edge ID you would like to delete: ");
        String edgetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM L1edges WHERE edgeID = " + edgetoDelete;
        stmt.execute(sql);
        stmt.close();
      }

      System.out.println("Row Deleted successfully from " + tabletoEdit);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void retrieveFromTable() {
    Scanner s1 = new Scanner(System.in);
    System.out.println("Which table would you like to delete from (Nodes, Edges): ");
    String tabletoEdit = s1.nextLine();
  }

  private void updateTable() {
    Scanner s1 = new Scanner(System.in);
    System.out.println("Which table would you like to delete from (Nodes, Edges): ");
    String tabletoEdit = s1.nextLine();
  }
}
