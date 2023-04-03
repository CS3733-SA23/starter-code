package edu.wpi.teame.controllers;

import Database.DatabaseController;
import Database.DatabaseGraphController;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.MoveAttribute;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

//public List<String> getLongNamesFromMove(List<MoveAttribute> mv)

public class GroundFloorNavMapController {
  @FXML private MFXButton firstFloorButton;

  @FXML private MFXButton secondFloorButton;

  @FXML private MFXButton thirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton lowerLevelOneButton;

  @FXML private MFXButton lowerLevelTwoButton;
  @FXML MFXComboBox currentLocationList;
  @FXML MFXComboBox destinationList;
  Floor currentFloor = Floor.GROUND;
  DatabaseController db = new DatabaseController();
  DatabaseGraphController graphController = new DatabaseGraphController(db);
  ObservableList<String> floorLocations = FXCollections.observableArrayList(collectLocationNames());

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
  }

  ArrayList collectLocationNames() {
    List<MoveAttribute> tableRow = graphController.getMoveAttributeFromFloor(currentFloor);
    ArrayList<String> toCollect = new ArrayList<>();
    for (int i = 0; tableRow.size() > i; i++) {
      MoveAttribute row = tableRow.get(i);
      toCollect.add(row.longName);
    }
    return toCollect;
  }
}
