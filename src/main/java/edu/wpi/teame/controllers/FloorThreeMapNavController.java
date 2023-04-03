package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class FloorThreeMapNavController {
  @FXML private MFXButton lowerLevelTwoButton;

  @FXML private MFXButton firstFloorButton;

  @FXML private MFXButton secondFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton groundFloorButton;

  @FXML private MFXButton lowerLevelOneButton;

  @FXML
  public void initialize() {
    lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_ONE));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_TWO));
    groundFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));
    secondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    firstFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
