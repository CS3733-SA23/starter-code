package edu.wpi.teamA.controllers;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ConferenceController {
  @FXML MFXButton backButton;
  @FXML MFXButton submitButton;
  @FXML MFXButton clearButton;
  String name_input;

  @FXML
  public void intitialize() {}

  public void goBack() {
    Navigation.navigate(Screen.HOME);
  }

  public void submitForm() {
    // send form information to database
    System.out.println("Form submitted by: " + name_input);
    Navigation.navigate(Screen.SERVICE_REQUEST);
  }

  public void clearForm() {
    // clear form
  }
}
