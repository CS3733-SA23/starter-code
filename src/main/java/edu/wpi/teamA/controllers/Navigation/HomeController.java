package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HomeController {
  @FXML private MFXButton serviceRequestButton;
  @FXML private MFXButton mapButton;
  @FXML private MFXButton signageButton;
  @FXML private MFXButton pathfindingButton;

  @FXML
  public void initialize() {
    serviceRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    mapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    pathfindingButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING));
  }
}
