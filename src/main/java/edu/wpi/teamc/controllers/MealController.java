package edu.wpi.teamc.controllers;

import edu.wpi.teamc.Cdb;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import edu.wpi.teamc.serviceRequest.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class MealController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;

  @FXML private MenuItem chocie0;

  @FXML private MenuItem chocie1;

  @FXML private MenuItem chocie2;

  @FXML private MenuItem chocie3;

  @FXML private MenuItem choice4;
  @FXML private MFXTextField roomID;

  @FXML private MFXTextField nameBox;

  @FXML private MenuButton menuButton;

  @FXML private TextArea textArea;

  public void getGoHome() {
    goHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void getChoice0() {
    menuButton.setText("Halal Shack Bowl with Zucchini Noodles and Chicken Breast");
  }

  @FXML
  void getChoice1() {
    menuButton.setText("Cheese Burger");
  }

  @FXML
  void getChoice2() {
    menuButton.setText("Hotdog");
  }

  @FXML
  void getChoice3() {
    menuButton.setText("Pizza");
  }

  @FXML
  void getChoice4() {
    menuButton.setText("Ian's Ham Sandwich");
  }

  @FXML
  void getSubmit(ActionEvent event) {
    String notes = textArea.getText();
    IServiceRequest.STATUS status = IServiceRequest.STATUS.PENDING;
    String name = nameBox.getText();
    String room = roomID.getText();
    String text = textArea.getText();
    MealRequest req = new MealRequest(new Meal(menuButton.getText(), ""), room, text, status);

    Requester reqr = new Requester(Cdb.latestRequestID("mealRequest") + 1, name);

    Cdb.addMeal(req, reqr);

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  public void getHelpPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.HELP);
  }

  public void getLogOut(ActionEvent actionEvent) {
    Navigation.navigate(Screen.LOGIN);
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
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getExit(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void getEditMap(ActionEvent event) {}

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  @FXML
  void getMapPage(ActionEvent event) {}
}
