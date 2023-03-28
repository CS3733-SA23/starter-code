package edu.wpi.teamA.controllers;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class FlowerController {

  @FXML MFXButton backButton;
  @FXML MFXButton clearButton;
  @FXML MFXButton submitButton;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
  }
}
