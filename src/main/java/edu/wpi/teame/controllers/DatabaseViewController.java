package edu.wpi.teame.controllers;

import Database.DatabaseController;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pathfinding.MoveAttribute;

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

  @FXML
  public void initialize() {

    DatabaseController databaseController = new DatabaseController("teame", "teame50");

    // load the database into the table
    nodeIDCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("nodeID"));
    nameCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("longName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("date"));

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    deleteButton.setOnMouseClicked(event -> {
      dataTable.getItems().removeAll(dataTable.getSelectionModel().getSelectedItem());
    });

    addButton.setOnMouseClicked(event -> {});

    importButton.setOnMouseClicked(event -> {});

    exportButton.setOnMouseClicked(event -> {});

    ObservableList itemList = FXCollections.observableArrayList(databaseController.getMoveList());

    dataTable.setItems(itemList);

    dataTable.setEditable(true);
  }
}
