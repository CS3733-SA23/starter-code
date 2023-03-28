package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class FlowerRequestController {

  @FXML MFXButton cancelButton;

  @FXML MFXButton submitButton;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    submitButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  //  String flowerType;
  //  int numOfFlowers;
  //  String recipientName;
  //  int roomNumber;
  //  String notes;
  //
  //  FlowerRequestController(
  //      String flowerType, int numOfFlowers, String recipientName, int roomNumber, String notes) {
  //    this.flowerType = flowerType;
  //    this.numOfFlowers = numOfFlowers;
  //    this.recipientName = recipientName;
  //    this.roomNumber = roomNumber;
  //    this.notes = notes;
}
