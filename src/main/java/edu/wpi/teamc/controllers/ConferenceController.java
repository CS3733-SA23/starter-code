package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class ConferenceController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MenuItem chocie0;

  @FXML private MenuItem chocie1;

  @FXML private MenuItem chocie2;

  @FXML private MenuItem chocie3;

  @FXML private MenuItem choice4;

  @FXML private MenuButton menuButton;

  public void getGoHome() {
    goHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void getChoice0() {
    menuButton.setText("--Please Conference Room--");
  }

  @FXML
  void getChoice1() {
    menuButton.setText("Conference A1");
  }

  @FXML
  void getChoice2() {
    menuButton.setText("Conference B2");
  }

  @FXML
  void getChoice3() {
    menuButton.setText("Conference C3");
  }

  @FXML
  void getChoice4() {
    menuButton.setText("Ian's Conference Room");
  }

  @FXML
  void getSubmit() {
    submit.setOnMouseClicked(event -> Navigation.navigate(Screen.CONGRATS_PAGE));
  }

  @FXML
  void getClear() {}

  @FXML
  void getMenuButton() {}

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

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  @FXML
  void getEditMap(ActionEvent event) {}

  @FXML
  void getLogOut(ActionEvent event) {}

  @FXML
  void getMapHistory(ActionEvent event) {}

  @FXML
  void getMapPage(ActionEvent event) {}
}
