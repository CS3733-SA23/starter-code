package edu.wpi.teamc.controllers;

import edu.wpi.teamc.Cdb;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import edu.wpi.teamc.serviceRequest.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.tableview2.FilteredTableView;

public class ConferenceHistoryController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<ConferenceRoomRequest> historyTable;
  @FXML TableView<ConferenceRoomRequest> otherTable;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnOne;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnTwo;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnThree;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnFour;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnFive;
  @FXML TableColumn<ConferenceRoomRequest, String> ColumnSix;

  ObservableList<ConferenceRoomRequest> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  /** Method run when controller is initialized */
  public void initialize() {
    ColumnOne.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("requestID"));
    ColumnTwo.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("Requester"));
    ColumnTwo.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("status"));
    ColumnThree.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("startTime"));
    ColumnFour.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("endTime"));
    ColumnFive.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("additionalInfo"));
    ColumnSix.setCellValueFactory(
        new PropertyValueFactory<ConferenceRoomRequest, String>("roomName"));
    ColumnOne.setText("ID");
    ColumnTwo.setText("Status");
    ColumnThree.setText("Start");
    ColumnFour.setText("End");
    ColumnFive.setText("Info");
    ColumnSix.setText("Room");
    //    ColumnOne.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    ColumnTwo.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    //    ColumnThree.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());

    // get conference room table

    historyTable
        .getItems()
        .setAll(this.convertToObservableList(Cdb.getTable("ServiceRequests", "conferenceRoom")));

    System.out.println("did it");
  }

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  //  public void dispTable(List<Move> moveList) {
  //    ColumnOne.setCellValueFactory(new PropertyValueFactory<TableRow, String>("nodeID"));
  //    ColumnTwo.setCellValueFactory(new PropertyValueFactory<TableRow, String>("longName"));
  //    ColumnThree.setCellValueFactory(new PropertyValueFactory<TableRow, String>("date"));
  //    //    testTable.getItems().setAll(gettableRows(moveList));
  //    historyTable.getItems().setAll(gettableRows(moveList));
  //    //    ColumnOne.setEditable(true);
  //    //    ColumnTwo.setEditable(true);
  //    //    ColumnThree.setEditable(true);
  //
  //    System.out.println("did it");
  //  }

  ObservableList<ConferenceRoomRequest> convertToObservableList(List<List<String>> rowList) {
    ObservableList<ConferenceRoomRequest> rows = FXCollections.observableArrayList();
    int requestID;
    String Requester;
    String status;
    String startTime;
    String endTime;
    String additionalInfo;
    String roomName;
    for (List<String> rl : rowList) {
      for (String s : rl) {
        System.out.println(s);
      }

      requestID = Integer.valueOf(rl.get(0));
      Requester = rl.get(1);
      status = rl.get(2);
      startTime = rl.get(3);
      endTime = rl.get(4);
      additionalInfo = rl.get(5);
      roomName = rl.get(6);
      rows.add(
          new ConferenceRoomRequest(
              new Requester(requestID, Requester),
              new ConferenceRoom(roomName, roomName, false),
              startTime,
              endTime,
              additionalInfo,
              IServiceRequest.STATUS.valueOf(status)));
    }
    return rows;
  }

  public String getText(javafx.event.ActionEvent actionEvent) {
    String inputtedText;
    inputtedText = inputBox.getText();
    inputBox.clear();
    return inputtedText;
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