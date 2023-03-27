package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseController {
  public static void main(String[] args) {
    DatabaseController DBC1 = new DatabaseController();
    Connection c = DBC1.connectToDatabase("teame", "teame50");
    DBC1.deleteFromTable(c);
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
      String tabletoEdit = s1.nextLine().toLowerCase();

      if (tabletoEdit.equals("nodes")) {
        System.out.println("Please type the Node ID you would like to delete: ");
        String nodetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql =
            "DELETE FROM teame.l1edges WHERE startnode = '"
                + nodetoDelete
                + "' OR endnode = '"
                + nodetoDelete
                + "';";
        sql += "DELETE FROM teame.l1nodes WHERE nodeid = '" + nodetoDelete + "';";
        stmt.execute(sql);
        stmt.close();

      } else {
        System.out.println("Please type the Edge ID you would like to delete: ");
        String edgetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM teame.l1edges WHERE edgeid = '" + edgetoDelete + "'";
        stmt.execute(sql);
        stmt.close();
      }

      System.out.println("Row Deleted successfully from " + tabletoEdit);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void retrieveFromTable(Connection c) {
    try {
      Statement stmt = null;
      ResultSet rs = null;

      Scanner s1 = new Scanner(System.in);
      System.out.println("Which table would you like to retrieve from (Nodes, Edges): ");
      String tabletoEdit = s1.nextLine();

      String nodeID = rs.getString("nodeID");
      int xCoord = rs.getInt("xcoord");
      int yCoord = rs.getInt("ycoord");
      String floor = rs.getString("floor");
      String building = rs.getString("building");
      String nodeType = rs.getString("nodeType");
      String longName = rs.getString("longName");
      String shortName = rs.getString("shortName");


      if (tabletoEdit.equals("Nodes")) {
        System.out.println("Please type the Node ID you would like to delete: ");
        String nodetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "SELECT * FROM" + tabletoEdit;
        stmt.execute(sql);
        stmt.close();

      } else {
        System.out.println("Please type the Edge ID you would like to delete: ");
        String edgetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM teame.l1edges WHERE edgeid = '" + edgetoDelete + "'";
        stmt.execute(sql);
        stmt.close();
      }

      System.out.println("Row Deleted successfully from " + tabletoEdit);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    Scanner s1 = new Scanner(System.in);
    System.out.println("Which table would you like to edit from (Nodes, Edges): ");
    String tabletoEdit = s1.nextLine();

    Statement stmt = null;
  }

  private void updateTable(Connection c) {
    try {
      Statement stmt = null;

      Scanner s1 = new Scanner(System.in);
      System.out.println("Which table would you like to update (Nodes, Edges): ");
      String tabletoUpdate = s1.nextLine().toLowerCase();

      if (tabletoUpdate.equals("nodes")) {
        System.out.println("Please type the Node ID you would like to update: ");
        String nodetoUpdate = s1.nextLine();

        boolean doneUpdating = false;
        while (doneUpdating == false) {
          System.out.println("Which attribute would you like to update (nodeid, xcoord, ycoord, floor, building, nodetype, longname, shortname)");
          stmt = c.createStatement();
        }
        /*
        String sql = "UPDATE FROM teame.L1nodes WHERE nodeID = " +
                "SET age = 30 WHERE id in (100, 101)";  ?????????
        stmt.executeUpdate(sql);
        ResultSet rs = stmt.executeQuery(QUERY); ??????????
        while(rs.next()){
          //Display values
          System.out.print("nodeID: " + rs.getInt("nodeID"));
          System.out.print(", xcoord: " + rs.getInt("xcoord"));
          System.out.print(", ycoord: " + rs.getString("ycoord"));
          System.out.println(", floor: " + rs.getString("floor"));
          System.out.println(", building: " + rs.getString("building"));
          System.out.println(", nodeType: " + rs.getString("nodeType"));
          System.out.println(", longName: " + rs.getString("longName"));
          System.out.println(", shortName: " + rs.getString("shortName"));
        }
        */

        stmt.close();

      } else {
        System.out.println("Please type the Edge ID you would like to update: ");
        String edgetoUpdate = s1.nextLine();
        stmt = c.createStatement();
        // String sql = "DELETE FROM l1edges WHERE edgeid = " + edgetoDelete;
        // stmt.execute(sql);
        stmt.close();
      }

      System.out.println("Row Updated successfully from " + tabletoUpdate);

    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }
}
