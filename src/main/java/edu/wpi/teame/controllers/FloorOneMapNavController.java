package edu.wpi.teame.controllers;

import Database.DatabaseController;
import Database.DatabaseGraphController;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class FloorOneMapNavController {
  @FXML private MFXButton lowerLevelTwoButton;

  @FXML private MFXButton secondFloorButton;

  @FXML private MFXButton thirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton groundFloorButton;

  @FXML private MFXButton lowerLevelOneButton;
  @FXML MFXComboBox<String> currentLocationList;
  @FXML MFXComboBox<String> destinationList;
  Floor currentFloor = Floor.ONE;

  DatabaseController db = new DatabaseController();
  DatabaseGraphController graphController = new DatabaseGraphController(db);
  ObservableList<String> floorLocations =
      FXCollections.observableArrayList(
          graphController.getLongNamesFromMove(
              graphController.getMoveAttributeFromFloor(currentFloor)));

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    groundFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWERR_LEVEL_TWO));
    lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWERR_LEVEL_ONE));
    secondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    thirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
  }
}
