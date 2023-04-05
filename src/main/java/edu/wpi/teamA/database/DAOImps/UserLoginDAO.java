package edu.wpi.teamA.database.DAOImps;

import edu.wpi.teamA.database.Connection.DBConnectionProvider;
import edu.wpi.teamA.database.ORMclasses.UserLogIn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class UserLoginDAO {
  ArrayList<UserLogIn> UserArray;

  static DBConnectionProvider UserLoginProvider = new DBConnectionProvider();

  public UserLoginDAO(ArrayList<UserLogIn> UserArray) {
    this.UserArray = UserArray;
  }

  // Create database table for User
  public void createUserTable() {
    try {
      Statement stmtUser = UserLoginProvider.createConnection().createStatement();
      String sqlCreateUser =
          "CREATE TABLE IF NOT EXISTS users ("
              + "userName   VARCHAR(255) PRIMARY KEY,"
              + "password   VARCHAR(255),"
              + "firstName  VARCHAR(255),"
              + "lastName   VARCHAR(255))";
      stmtUser.execute(sqlCreateUser);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // Check if the user exists. If exists, pull the password from the database and check if it fits
  // If not exist, call addUser
  public void checkUser(String userName, String password) {
    try {
      PreparedStatement ps =
          UserLoginProvider.createConnection()
              .prepareStatement("SELECT * FROM users WHERE userName = ?");
      ps.setString(1, userName);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        String storedPassword = rs.getString("password");
        if (password.equals(storedPassword)) {
          System.out.println("User login successful.");
        } else {
          System.out.println("Incorrect password.");
        }
      } else {
        System.out.println("User not found, adding new user.");
        addUser(userName, password);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // Add the new user into the database
  // Also store the user into the array
  public void addUser(String userName, String password) {
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter first name and last name:");
      String firstName = input.next();
      String lastName = input.next();

      PreparedStatement ps =
          UserLoginProvider.createConnection()
              .prepareStatement(
                  "INSERT INTO users (userName, password, firstName, lastName) VALUES (?, ?, ?, ?)");
      ps.setString(1, userName);
      ps.setString(2, password);
      ps.setString(3, firstName);
      ps.setString(4, lastName);
      ps.executeUpdate();

      UserArray.add(new UserLogIn(userName, password, firstName, lastName));
      System.out.println("New user added successfully.");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
