package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController {

  @FXML MFXButton backButton;
  @FXML MFXButton mealRequestbutton;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    mealRequestbutton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL));
  }
}
