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

      // Do something with the connection
      //

      Scanner scanner = new Scanner(System.in);
      boolean continueProg = true;

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

      while (continueProg) {
        System.out.println(
            "===========================================\n"
                + "Display node and edge information\n"
                + "Update node coordinates\n"
                + "Update name of location node\n"
                + "Export node table into a CSV file\n"
                + "Import from a CSV file into the node table\n"
                + "Help\n"
                + "Exit\n"
                + "===========================================\n");
        String command = scanner.nextLine();
        switch (command) {
          case "Display node and edge information":
            try {
              System.out.println("Node information: \n");
              while (rsNodes.next()) {
                for (int i = 1; i <= rsNodes.getMetaData().getColumnCount(); i++) {
                  System.out.print(rsNodes.getString(i) + "\t");
                }
                System.out.println("\n");
              }
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
            break;
          case "Update node coordinates":
            //
            break;
          case "Update name of location node":
            //
            break;
          case "Export node table into a CSV file":
            //
            break;
          case "Import from a CSV file into the node table\n":
            //
            break;
          case "Help":
            //
            break;
          case "Exit":
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
}
