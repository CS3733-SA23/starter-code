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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

// public List<String> getLongNamesFromMove(List<MoveAttribute> mv)

public class GroundFloorNavMapController {
  @FXML private MFXButton firstFloorButton;

  @FXML private MFXButton secondFloorButton;

  @FXML private MFXButton thirdFloorButton;

  @FXML private MFXButton backButton;

  @FXML private MFXButton lowerLevelOneButton;

  @FXML private MFXButton lowerLevelTwoButton;
  @FXML MFXComboBox<String> currentLocationList;
  @FXML MFXComboBox<String> destinationList;
  Floor currentFloor = Floor.ONE;
  String curLocFromComboBox;
  String destFromComboBox;

  DatabaseController db = new DatabaseController("teame", "teame50");
  DatabaseGraphController graphController = new DatabaseGraphController(db);
  ObservableList<String> floorLocations =
      FXCollections.observableArrayList(
          graphController.getLongNamesFromMove(
              graphController.getMoveAttributeFromFloor(currentFloor)));

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));
    lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));
    firstFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_ONE));
    secondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
    thirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
    currentLocationList.setItems(floorLocations);
    destinationList.setItems(floorLocations);

    currentLocationList.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            curLocFromComboBox = currentLocationList.getValue();
            if (!(destFromComboBox == null)) {
              displayPath(curLocFromComboBox, destFromComboBox);
            }
          }
        });

    destinationList.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            destFromComboBox = destinationList.getValue();
            if (!(curLocFromComboBox == null)) {
              displayPath(curLocFromComboBox, destFromComboBox);
            }
          }
        });
  }

  void displayPath(String from, String to) {
    System.out.println(from + " to " + to); //used for testing
  }
}
