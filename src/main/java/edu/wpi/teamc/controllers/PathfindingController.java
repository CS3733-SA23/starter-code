package edu.wpi.teamc.controllers;

import edu.wpi.teamc.map.Graph;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.*;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PathfindingController {
  Graph graph = new Graph();
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;
  @FXML private MFXButton clear;
  @FXML private MFXTextField startNode;
  @FXML private MFXTextField endNode;

  @FXML private Label directionsbox;

  @FXML
  void getGoHome() {
    goHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void getClear(javafx.event.ActionEvent event) {
    startNode.clear();
    endNode.clear();
    directionsbox.setText("");
  }

  @FXML
  void getSubmit(javafx.event.ActionEvent event) {
    String startNodeString = startNode.getText();
    String endNodeString = endNode.getText();
    List<String> directions = graph.stringDirectionsAStar(startNodeString, endNodeString);
    String directionsString = "";

    directionsString += directions.get(0);
    for (String s : directions.subList(1, directions.size() - 1)) {
      directionsString += "->" + s;
    }

    directionsString += "\nWeight: " + directions.get(directions.size() - 1);
    directionsbox.setWrapText(true);
    directionsbox.setMaxWidth(330);
    directionsbox.setText(directionsString);
  }
  /** Method run when controller is initializes */
  public void initialize() {
    graph.syncWithDB();
  }

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getEditMap(ActionEvent event) {}

  @FXML
  void getLogOut(ActionEvent event) {}

  @FXML
  void getMapHistory(ActionEvent event) {}

  @FXML
  void getMapPage(ActionEvent event) {}
}
