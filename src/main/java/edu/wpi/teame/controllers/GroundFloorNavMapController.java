package edu.wpi.teame.controllers;

import Database.DatabaseController;
import Database.DatabaseGraphController;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

// public List<String> getLongNamesFromMove(List<MoveAttribute> mv)

public class GroundFloorNavMapController {
  @FXML private MFXButton firstFloorButton;

  @FXML private MFXButton secondFloorButton;

  @FXML private MFXButton thirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton lowerLevelOneButton;

  @FXML private MFXButton lowerLevelTwoButton;
  @FXML MFXComboBox currentLocationList;
  @FXML MFXComboBox destinationList;
  Floor currentFloor = Floor.ONE;

  DatabaseController db = DatabaseController.INSTANCE;
  DatabaseGraphController graphController = new DatabaseGraphController(db);
  ObservableList<String> floorLocations =
      FXCollections.observableArrayList(
          graphController.getLongNamesFromMove(
              graphController.getMoveAttributeFromFloor(currentFloor)));

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWERR_LEVEL_ONE));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWERR_LEVEL_TWO));
    firstFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    secondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    thirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
    currentLocationList.setItems(floorLocations);
    destinationList.setItems(floorLocations);
    System.out.println(floorLocations);
  }

  ArrayList<String> test() {
    ArrayList<String> test = new ArrayList<>();
    for (int i = 0; 10 > i; i++) {
      test.add("i");
    }
    return test;
  }
}
