package edu.wpi.teamc.controllers;

import edu.wpi.teamc.Cdb;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import edu.wpi.teamc.serviceRequest.ConferenceRoomRequest;
import edu.wpi.teamc.serviceRequest.IServiceRequest;
import edu.wpi.teamc.serviceRequest.Requester;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class ConferenceController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MFXButton clear;

  @FXML private MenuItem choice0;

  @FXML private MenuItem choice1;

  @FXML private MenuItem choice2;

  @FXML private MenuItem choice3;

  @FXML private MenuItem choice4;

  @FXML private MenuButton menuButton;
  @FXML private MFXTextField nameBox;
  @FXML private TextArea specialRequest;
  @FXML private DatePicker startTime;
  @FXML private DatePicker endTime;
  private int currentReqID = 0;

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
  void getSubmit(ActionEvent event) {
    LocalDate start = startTime.getValue();
    LocalDate end = endTime.getValue();

    String notes = specialRequest.getText();
    String roomName = menuButton.getText();
    IServiceRequest.STATUS status = IServiceRequest.STATUS.COMPLETE;
    ConferenceRoomRequest req =
        new ConferenceRoomRequest(start.toString(), end.toString(), notes, roomName, status);

    String name = nameBox.getText();

    Requester reqr = new Requester(Cdb.latestRequestID("conferenceRoom") + 1, name);

    Cdb.addConferenceRoomRequest(req, reqr);

    Navigation.navigate(Screen.CONGRATS_PAGE);
  }

  @FXML
  void getClear() {
    clear.setOnMouseClicked(event -> Navigation.navigate(Screen.CONFERENCE));
  }

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

  @FXML
  void getConferenceHistory(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE_HISTORY);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  @FXML
  void getEditMap(ActionEvent event) {}

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN);
  }

  @FXML
  void getExit(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void getMapHistory(ActionEvent event) {}

  @FXML
  void getMapPage(ActionEvent event) {}
}
