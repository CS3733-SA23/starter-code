package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class GroundFloorNavMapController {
  @FXML private MFXButton firstFloorButton;

  @FXML private MFXButton secondFloorButton;

  @FXML private MFXButton thirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton lowerLevelOneButton;

  @FXML private MFXButton lowerLevelTwoButton;
  @FXML MFXComboBox currentLocationList;
  @FXML MFXComboBox destinationList;
  ObservableList<String> floorLocations;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWERR_LEVEL_ONE));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWERR_LEVEL_TWO));
    firstFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    secondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    thirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));

  }
}
