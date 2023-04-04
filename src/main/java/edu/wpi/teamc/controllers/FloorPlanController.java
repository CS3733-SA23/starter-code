package edu.wpi.teamc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class FloorPlanController {
  @FXML private MenuItem choice1;

  @FXML private MenuItem choice2;

  @FXML private MenuItem choice3;

  @FXML private MenuItem choice4;

  @FXML private MenuButton menuButton;

  @FXML
  void getChoice1(ActionEvent event) {
    menuButton.setText("Floor 1");
  }

  @FXML
  void getChoice2(ActionEvent event) {
    menuButton.setText("Floor 2");
  }

  @FXML
  void getChoice3(ActionEvent event) {
    menuButton.setText("Floor 3");
  }

  @FXML
  void getChoice4(ActionEvent event) {
    menuButton.setText("Floor 4");
  }

  @FXML
  void getMenuButton(ActionEvent event) {}
}
