package edu.wpi.teamA.controllers;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController {

  @FXML MFXButton backButton;
  @FXML MFXButton confRoomButton;
  @FXML MFXButton flowerButton;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    flowerButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));
  }

  public void openConferenceForm() {
    Navigation.navigate(Screen.CONFERENCE_REQUEST);
  }
}
