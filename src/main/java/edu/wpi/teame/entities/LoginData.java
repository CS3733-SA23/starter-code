package edu.wpi.teame.entities;

import javafx.scene.control.Alert;

public class LoginData {
  String username;
  String password;

  public LoginData(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public boolean attemptLogin() {
    // Popup message depending on results
    Alert popup = new Alert(Alert.AlertType.NONE);

    // INSERT DATABASE THINGS HERE
    // if username and hashed password are in the database, return true
    // otherwise return false

    // For now just check for admin login
    if (username.equals("admin") && password.equals("password")) {
      popup.setAlertType(Alert.AlertType.CONFIRMATION);
      popup.setContentText("Welcome " + username + "!");
      popup.show();
      return true;
    }
    popup.setAlertType(Alert.AlertType.ERROR);
    popup.setContentText("Invalid Login");
    popup.show();
    return false;
  }
}
