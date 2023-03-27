package edu.wpi.teamname;

import java.sql.*;
import java.util.Scanner;

public class Cdb {

  public static void main(String[] args) {
    Connection connection = null;
    try {
      // Load the PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");

      // Establish the connection
      String url = "jdbc:postgresql://database.cs.wpi.edu/teamcdb";
      String user = "teamc";
      String password = "teamc30";
      connection = DriverManager.getConnection(url, user, password);

      Scanner scanner = new Scanner(System.in);
      //////////////

      boolean continueProg = true;
      while (continueProg) {
        displayInstructions();
        String command = scanner.nextLine().toLowerCase();
        switch (command) {
          case "display node and edge information":
            // displays all of the node and edge attributes
            displayNodeAndEdgeInfo(connection);
            break;
          case "update node coordinates":
            System.out.println("please type the nodeID of the node you would like to update:");
            String nodeID = scanner.nextLine();
            System.out.println("Enter new x coordinate:");
            int xCoordinate = scanner.nextInt();
            System.out.println("Enter new y coordinate:");
            int yCoordinate = scanner.nextInt();
            // update the coordinates with given nodeID and values
            updateCoordinates(connection, xCoordinate, yCoordinate, nodeID);
            break;
          case "update name of location node":
            //
            break;
          case "export node table into a csv file":
            //
            break;
          case "import from a csv file into the node table\n":
            //
            break;
          case "help":
            System.out.println("");
            break;
          case "exit":
            continueProg = false;
            break;
          default:
            System.out.println("Command not found");
            break;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // Close the connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  static void displayInstructions() {
    System.out.println(
        "===========================================\n"
            + "Options:\n"
            + "Display node and edge information\n"
            + "Update node coordinates\n"
            + "Update name of location node\n"
            + "Export node table into a CSV file\n"
            + "Import from a CSV file into the node table\n"
            + "Help\n"
            + "Exit\n"
            + "===========================================\n");
  }

  static void displayNodeAndEdgeInfo(Connection connection) {
    try {
      Statement stmtNode = connection.createStatement();
      Statement stmtEdge = connection.createStatement();
      // table names
      String node = "\"hospitalNode\".node";
      String edge = "\"hospitalNode\".edge";
      // queries
      String queryDisplayNodes = "SELECT * FROM " + node;
      String queryDisplayEdges = "SELECT * FROM " + edge;

      ResultSet rsNodes = stmtNode.executeQuery(queryDisplayNodes);
      ResultSet rsEdges = stmtEdge.executeQuery(queryDisplayEdges);
      System.out.println("Node information: \n");
      while (rsNodes.next()) {
        for (int i = 1; i <= rsNodes.getMetaData().getColumnCount(); i++) {
          System.out.print(rsNodes.getString(i) + "\t");
        }
        System.out.println("\n");
      }
      System.out.println(
          "----------------------------------------------------------------------------------------------------------------------------");
      System.out.println("Edge information: \n");
      while (rsEdges.next()) {
        for (int i = 1; i <= rsEdges.getMetaData().getColumnCount(); i++) {
          System.out.print(rsEdges.getString(i) + "\t");
        }
        System.out.println("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void updateCoordinates(
      Connection connection, int xCoordinate, int yCoordinate, String nodeID) {
    try {
      PreparedStatement stmtUpdateCoord =
          connection.prepareStatement(
              "UPDATE \"hospitalNode\".node\n"
                  + "SET xcoord = ?, ycoord = ?\n"
                  + "WHERE \"nodeID\" = ?;");
      // set parameters for prepared statement
      stmtUpdateCoord.setInt(1, xCoordinate);
      stmtUpdateCoord.setInt(2, yCoordinate);
      stmtUpdateCoord.setString(3, nodeID);

      int rowsUpdated = stmtUpdateCoord.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("update successful!");
      } else {
        System.out.println("Node not found: Invalid nodeID");
      }
    } catch (SQLException e) {
      System.out.println("SQL exception occurred: " + e.getMessage());
    }
  }
}
