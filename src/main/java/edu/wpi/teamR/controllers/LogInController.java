package edu.wpi.teamR.controllers;

import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LogInController {

  @FXML MFXButton cancelButton;
  @FXML MFXButton logInButton;
  @FXML MFXTextField usernameField;
  @FXML MFXTextField passwordField;
  @FXML Text messageText;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    logInButton.setOnMouseClicked(event -> checkLogIn());
  }

  @FXML
  public void checkLogIn() {
    System.out.println(usernameField.getText());
    System.out.println(passwordField.getText());

    if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
      Navigation.navigate(Screen.EMPLOYEE);
      messageText.setText("Successfully logged in!");
    } else {
      messageText.setText("Wrong username or password.\nPlease try log in again.");
    }
  }
}
