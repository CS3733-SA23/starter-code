package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class FloorTwoMapNavController {
  @FXML private MFXButton lowerLevelTwoButton;

  @FXML private MFXButton firstFloorButton;

  @FXML private MFXButton ThirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton groundFloorButton;

  @FXML private MFXButton lowerLevelOneButton;

  public void initialize() {
    lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_ONE));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_TWO));
    groundFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));
    firstFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    ThirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
