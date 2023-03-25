package edu.wpi.teamname;

import java.sql.*;

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
      // ...

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
