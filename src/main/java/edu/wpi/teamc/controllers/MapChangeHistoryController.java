package edu.wpi.teamc.controllers;

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
import org.controlsfx.control.tableview2.FilteredTableView;

public class MapChangeHistoryController {

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
  ObservableList<TableRow> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  public void dispTable(List<Move> moveList) {
    ColumnOne.setCellValueFactory(new PropertyValueFactory<TableRow, String>("nodeID"));
    ColumnTwo.setCellValueFactory(new PropertyValueFactory<TableRow, String>("longName"));
    ColumnThree.setCellValueFactory(new PropertyValueFactory<TableRow, String>("date"));
    historyTable.getItems().setAll(gettableRows(moveList));

    System.out.println("did it");
  }

  public ObservableList<TableRow> gettableRows(List<Move> moveList) {
    String nodeID;
    String longName;
    String date;
    for (Move currMove : moveList) {
      nodeID = currMove.getNodeID();
      longName = currMove.getLongName();
      date = currMove.getDate().toString();
      rows.add(new TableRow(nodeID, longName, date));
    }
    return rows;
  }

  public String getText(javafx.event.ActionEvent actionEvent) {
    String inputtedText;
    inputtedText = inputBox.getText();
    inputBox.clear();
    return inputtedText;
  }
}
