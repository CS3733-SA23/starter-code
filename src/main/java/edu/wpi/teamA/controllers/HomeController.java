package edu.wpi.teamA.controllers;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HomeController {

  @FXML MFXButton serviceRequestButton;
  @FXML MFXButton officeMoveButton;
  @FXML MFXButton mapButton;
  @FXML MFXButton signageButton;
  @FXML MFXButton aboutButton;
  @FXML MFXButton helpButton;

  @FXML
  public void initialize() {
    serviceRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    officeMoveButton.setOnMouseClicked(event -> Navigation.navigate(Screen.OFFICE_MOVE));
    mapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    aboutButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ABOUT));
    helpButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HELP));
  }
}
