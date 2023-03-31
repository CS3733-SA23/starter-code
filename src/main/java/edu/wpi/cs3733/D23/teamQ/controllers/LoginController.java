package edu.wpi.cs3733.D23.teamQ.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {
  @FXML TextField usernameField;
  @FXML TextField passwordField;
  @FXML Button loginButton;
  @FXML Button FPButton;

  @FXML Button CAButton;

  @FXML
  public void initialize() {}

  @FXML
  public void passwordFieldEntered(KeyEvent e) {
    if (e.getCode().equals(KeyCode.ENTER)) {
      System.out.println("Navigate to a logged in screen or pops up a failed to login screen.");
    }
  }

  /**
   * Whenever the Enter key is pressed inside the username textfield, change the focus to the
   * password textfield.
   *
   * @param e A key pressed event received from the username textfield.
   */
  @FXML
  public void usernameFieldEntered(KeyEvent e) {
    if (e.getCode().equals(KeyCode.ENTER)) {
      passwordField.requestFocus();
    }
  }

  @FXML
  public void loginButtonClicked() {
    System.out.println("Navigate to a logged in screen or pops up a failed to login screen.");
  }

  @FXML
  public void FPButtonClicked() {
    System.out.println("Pops up a screen for resetting password.");
  }

  @FXML
  public void CreatAccountButtonClicked() {
    System.out.println("Create Account");
  }
}
