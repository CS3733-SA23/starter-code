package edu.wpi.teamname;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Qdb {
  private final String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamqdb";
  private final String user = "teamq";
  private final String password = "teamq140";

  public Connection connect() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return conn;
  }

  public static void main(String[] args) {
    Qdb app1 = new Qdb();
    app1.connect();
  }
}
