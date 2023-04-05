package edu.wpi.teamc.controllers;

import edu.wpi.teamc.Cdb;
import edu.wpi.teamc.map.Move;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.controlsfx.control.tableview2.FilteredTableView;

public class MapEditingController {

  /** */
  @FXML MFXButton backButton;

  /** Method run when controller is initialized */
  @FXML
  public void goHome() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML private Button testButton;
  @FXML private TextField inputBox;
  @FXML private FilteredTableView<TableRow> historyTable;
  @FXML TableView<TableRow> otherTable;
  @FXML TableColumn<TableRow, String> ColumnOne;
  @FXML TableColumn<TableRow, String> ColumnTwo;
  @FXML TableColumn<TableRow, String> ColumnThree;
  //  @FXML TableColumn<TableRow, String> ColumnOne1;
  //  @FXML TableColumn<TableRow, String> ColumnTwo1;
  //  @FXML TableColumn<TableRow, String> ColumnThree1;
  //  @FXML TableView<TableRow> testTable;
  ObservableList<TableRow> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;
  //  List<Node> databaseNodeList = new ArrayList<Node>();
  //  List<Edge> databaseEdgeList = new ArrayList<Edge>();
  //  List<LocationName> databaseLocationNameList = new ArrayList<LocationName>();
  //  List<Move> databaseMoveList = new ArrayList<Move>();

  /** Method run when controller is initialized */
  public void initialize() {
    // Allows cells to be identifiable
    ColumnOne.setCellValueFactory(new PropertyValueFactory<TableRow, String>("nodeID"));
    ColumnTwo.setCellValueFactory(new PropertyValueFactory<TableRow, String>("longName"));
    ColumnThree.setCellValueFactory(new PropertyValueFactory<TableRow, String>("date"));

    // Allows cells to be editable
    ColumnOne.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnTwo.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());
    ColumnThree.setCellFactory(TextFieldTableCell.<TableRow>forTableColumn());

    historyTable.setEditable(true);
    //    historyTable.getOnMouseClicked();
    //    historyTable.getEditingCell();
    //    testTable.setEditable(true);
    //    ColumnOne.setEditable(true);
    //    ColumnTwo.setEditable(true);
    //    ColumnThree.setEditable(true);

    //    testTable.getItems().setAll(gettableRows(Cdb.databaseMoveList));
    historyTable
        .getItems()
        .setAll(
            gettableRows(
                Cdb.databaseMoveList)); // Need to change from move list to whatever we actually
    // want here

    // here to be used to update databaseMoveList, needs to be implemented still
    List<Move> moveList = Cdb.databaseMoveList;

    // Allows cells to be edited and their edits to be saved and displayed in the table. If we do
    // not want the nodeID to be editable, delete this call for columnOne

    //    ColumnOne.setOnEditCommit(
    //        event -> {
    //          TableRow rowData = event.getRowValue();
    //          rowData.setNodeID(event.getNewValue());
    //        });

    // index is used to find the point in the databaseMoveList the updated node is from. Can therefore
    // use the index to only edit that specific node in the databaseMoveList (instead of checking for
    // nodeID matching because, for this, you would have to look within the nodeID of each and every
    // move object and find the matching one. This way you can just look for the matching index and
    // replace the proper move object at that index)
    ColumnTwo.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          rowData.setLongName(event.getNewValue());
          String updatedNode = rowData.getNodeID();
          String updatedLongName = rowData.getLongName();
          System.out.print(updatedNode + " " + updatedLongName);
          int index = rowData.getIndex();
          System.out.println("/n this is the index: " + index);
        });
    ColumnThree.setOnEditCommit(
        event -> {
          TableRow rowData = event.getRowValue();
          rowData.setDate(event.getNewValue());
          String updatedNode = rowData.getNodeID();
          String updatedDate = rowData.getDate();
          System.out.print(updatedNode + " " + updatedDate);
          int index = rowData.getIndex();
        });
    //    ObservableList<TableColumn<TableRow, ?>> nodeIDList = ColumnOne.getColumns();
    //    TableColumn newColumn = nodeIDList.get(1);
    //    TableRow newTableRow = newColumn.getTableView();
    // use "indexOf()" to get index of nodeID in list from database and compare to saved nodeID from
    // edited cell above?
    System.out.println("before");
    //    historyTable.getItems()
    System.out.println(historyTable.getItems());
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

  public ObservableList<TableRow> gettableRows(List<Move> moveList) {
    String nodeID;
    String longName;
    String date;
    int index = -1;
    for (Move currMove : moveList) {
      nodeID = currMove.getNodeID();
      longName = currMove.getLongName();
      date = currMove.getDate().toString();
      index++;
      rows.add(new TableRow(nodeID, longName, date, index));
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
  void getLogOut(ActionEvent event) {}

  @FXML
  void getMapHistory(ActionEvent event) {}

  @FXML
  void getMapPage(ActionEvent event) {}
}
