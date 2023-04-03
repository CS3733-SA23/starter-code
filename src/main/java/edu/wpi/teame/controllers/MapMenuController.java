package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class MapMenuController {
  @FXML private MFXButton lowerLevelOne;
  @FXML private MFXButton lowerLevelTwo;
  @FXML private MFXButton floorOne;

  @FXML private MFXButton floorTwo;

  @FXML private MFXButton floorThree;

  @FXML private MFXButton returnButton;

  @FXML private MFXButton groundFloor;

  @FXML
  public void initialize() {
    lowerLevelOne.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_ONE));
    lowerLevelTwo.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_TWO));
    groundFloor.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));
    floorOne.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    floorTwo.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    floorThree.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
    returnButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
