package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class GroundFloorNavMapController {
  @FXML private MFXButton FirstFloorButton;

  @FXML private MFXButton SecondFloorButton;

  @FXML private MFXButton ThirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton lowerLevelOneButton;

  @FXML private MFXButton lowerLevelTwoButton;

  @FXML
  public void initialize() {
    lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_ONE));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_TWO));
    FirstFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    SecondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    ThirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_MENU));
  }
}
