package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class FurnitureController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;

  @FXML private MenuItem chocie0;

  @FXML private MenuItem chocie1;

  @FXML private MenuItem chocie2;

  @FXML private MenuItem chocie3;

  @FXML private MenuItem choice4;

  @FXML private MenuButton menuButton;

  @FXML
  public void getGoHome() {
    goHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void getChoice0() {
    menuButton.setText("--Please Select Furniture Option--");
  }

  @FXML
  void getChoice1() {
    menuButton.setText("Large Table");
  }

  @FXML
  void getChoice2() {
    menuButton.setText("Desk");
  }

  @FXML
  void getChoice3() {
    menuButton.setText("Chair");
  }

  @FXML
  void getChoice4() {
    menuButton.setText("Ian's Desk");
  }

  @FXML
  void getSubmit() {
    submit.setOnMouseClicked(event -> Navigation.navigate(Screen.CONGRATS_PAGE));
  }

  @FXML
  void getClear() {}

  @FXML
  void getFlowerDeliveryPage(javafx.event.ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(javafx.event.ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(javafx.event.ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(javafx.event.ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(javafx.event.ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(javafx.event.ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(javafx.event.ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  @FXML
  void getEditMap(javafx.event.ActionEvent event) {}

  @FXML
  void getLogOut(javafx.event.ActionEvent event) {}

  @FXML
  void getMapHistory(javafx.event.ActionEvent event) {}

  @FXML
  void getMapPage(ActionEvent event) {}
}
