package edu.wpi.teamc;

import java.sql.*;

public class DBConnection {
  private static Connection connection;

  static {
    try {
      // Load the PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");
      // Establish the connection
      String url = "jdbc:postgresql://database.cs.wpi.edu/teamcdb";
      String user = "teamc";
      String password = "teamc30";
      connection = DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    return connection;
  }
}
