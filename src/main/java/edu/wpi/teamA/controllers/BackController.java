package edu.wpi.teamA.controllers;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class BackController {
  @FXML MFXButton backButton;

  @FXML
  public void initialize() {
    backButton.setOnAction(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
  }
}
