package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class LowerLevelOneMapNavController {

  @FXML private MFXButton firstFloorButton;

  @FXML private MFXButton secondFloorButton;

  @FXML private MFXButton thirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton groundFloorButton;

  @FXML private MFXButton lowerLevelTwoButton;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    groundFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWERR_LEVEL_TWO));
    firstFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    secondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    thirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
  }
}
