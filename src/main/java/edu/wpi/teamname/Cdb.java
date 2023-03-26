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
      while (continueProg) {
        System.out.println(
            "Display node and edge information\n"
                + "Update node coordinates\n"
                + "Update name of location node\n"
                + "Export node table into a CSV file\n"
                + "Import from a CSV file into the node table\n"
                + "Display Help on how to use your database program\n"
                + "Exit the program\n");
        String command = scanner.nextLine();
        switch (command) {
          case "Display node and edge information\n":
            //
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
          case "Display Help on how to use your database program":
            //
            break;
          case "Exit the program":
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
