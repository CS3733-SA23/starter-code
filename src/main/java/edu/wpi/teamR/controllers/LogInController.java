package edu.wpi.teamR.controllers;

import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class LogInController {

  @FXML MFXButton cancelButton;
  @FXML MFXButton logInButton;
  @FXML MFXTextField usernameField;
  @FXML MFXTextField passwordField;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    logInButton.setOnMouseClicked(event -> checkLogIn());
  }

  @FXML
  public void checkLogIn() {
    System.out.println(usernameField.getText());
    System.out.println(passwordField.getText());

    if (usernameField.getText().equals("username") && passwordField.getText().equals("TeamR")) {
      Navigation.navigate(Screen.SortOrders);
      System.out.println("Successfully logged in!");
    } else {
      System.out.println("Wrong username or password.\nPlease try log in again.");
    }
  }
}
