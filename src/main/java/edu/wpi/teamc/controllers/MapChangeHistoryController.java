package edu.wpi.teamc.controllers;

import edu.wpi.teamc.MapDisplay.TableRow;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
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
  @FXML private FilteredTableView<edu.wpi.teamc.MapDisplay.TableRow> historyTable;
  @FXML TableView<edu.wpi.teamc.MapDisplay.TableRow> otherTable;
  @FXML TableColumn<edu.wpi.teamc.MapDisplay.TableRow, String> ColumnOne;
  @FXML TableColumn<edu.wpi.teamc.MapDisplay.TableRow, String> ColumnTwo;
  @FXML TableColumn<edu.wpi.teamc.MapDisplay.TableRow, String> ColumnThree;
  ObservableList<edu.wpi.teamc.MapDisplay.TableRow> rows = FXCollections.observableArrayList();

  @FXML private Button goHome;

  public void getGoHome(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  public void dispTable(javafx.event.ActionEvent actionEvent) {
    ColumnOne.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.teamc.MapDisplay.TableRow, String>("nodeID"));
    ColumnTwo.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.teamc.MapDisplay.TableRow, String>("longName"));
    ColumnThree.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.teamc.MapDisplay.TableRow, String>("date"));
    historyTable.getItems().setAll(gettableRows());

    System.out.println("did it");
  }

  public ObservableList<edu.wpi.teamc.MapDisplay.TableRow> gettableRows() {
    rows.add(new edu.wpi.teamc.MapDisplay.TableRow("1234", "Alpha", "4/17/02"));
    rows.add(new TableRow("456", "Beta", "4/5/2023"));
    return rows;
  }
  //  @Override
  //  public void initialize(URL location, ResourceBundle resources) {
  //
  //  }

  //  @FXML
  //  public String getText(ActionEvent event) {
  //    String inputtedText;
  //    inputtedText = textField.getText();
  //    return inputtedText;
  //  }

  public String getText(javafx.event.ActionEvent actionEvent) {
    String inputtedText;
    inputtedText = inputBox.getText();
    return inputtedText;
  }
  //  public void createTable() {
  //    String test = "testing";
  //    ColLocation = ;
  //
  //  }
}
