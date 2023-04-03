package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController {

  @FXML MFXButton roomButton;
  @FXML MFXButton flowerButton;

  @FXML
  public void initialize() {
    roomButton.setOnAction(event -> Navigation.navigate(Screen.ROOM));
    flowerButton.setOnAction(event -> Navigation.navigate(Screen.FLOWER));
  }
}
