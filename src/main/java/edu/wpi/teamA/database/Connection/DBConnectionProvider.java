package edu.wpi.teamA.database.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {
  private static DBConnectionProvider instance = null;
  private Connection connection;

  public static DBConnectionProvider getInstance() {
    if (instance == null) {
      instance = new DBConnectionProvider();
      instance.createConnection();
    }
    return instance;
  }

  public static Connection createConnection() {
    String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamadb";
    String user = "teama";
    String password = "teama10";

    try {
      return DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
