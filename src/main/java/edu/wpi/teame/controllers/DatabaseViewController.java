package edu.wpi.teame.controllers;


import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import pathfinding.MoveAttribute;
import Database.DatabaseController;

public class DatabaseViewController {

  @FXML MFXButton backButton;
  @FXML MFXButton deleteButton;

  @FXML MFXButton addButton; // three text boxes and a button that says "add" next to it
  @FXML MFXTextField IDField;

  @FXML MFXTextField locationField;

  @FXML MFXTextField dateField;

  @FXML MFXButton importButton;

  @FXML MFXButton exportButton;

  @FXML TableView<MoveAttribute> dataTable;

  @FXML TableColumn<MoveAttribute, String> nodeIDCol;

  @FXML TableColumn<MoveAttribute, String> nameCol;

  @FXML TableColumn<MoveAttribute, String> dateCol;

  DirectoryChooser directoryChooser = new DirectoryChooser();

  @FXML
  public void initialize() {
    /* directoryChooser.setInitialDirectory(new File("src"));

        DatabaseController databaseController = new DatabaseController("teame", "teame50");
    */

    // load the database into the table
    nodeIDCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("nodeID"));
    nameCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("longName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("date"));

    /*ObservableList itemList = FXCollections.observableArrayList(databaseController.getMoveList());

        dataTable.setItems(itemList);
    */
    dataTable.setEditable(true);

    // testing stuff
    dataTable.getItems().add(new MoveAttribute("1111", "testA", "4/2/2023"));
    dataTable.getItems().add(new MoveAttribute("2222", "testB", "4/2/2023"));

    dataTable.setPlaceholder(new Label("No rows to display"));

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    deleteButton.setOnMouseClicked(
        event -> {
          dataTable.getItems().removeAll(dataTable.getSelectionModel().getSelectedItem());
          // delete row in database (call method)
        });

    addButton.setOnMouseClicked(
        event -> {
          String nodeID = IDField.getText();
          String name = locationField.getText();
          String date = dateField.getText();
          MoveAttribute newMoveAttribute = new MoveAttribute(nodeID, name, date);
          dataTable.getItems().add(newMoveAttribute);
          addToTable(newMoveAttribute);
        });

    /* importButton.setOnMouseClicked(
            event -> {
              File selectedDirectory = directoryChooser.showDialog();
            });
    */
    exportButton.setOnMouseClicked(event -> {});
  }
}
