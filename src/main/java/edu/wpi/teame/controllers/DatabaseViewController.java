package edu.wpi.teame.controllers;

import Database.DatabaseController;
import edu.wpi.teame.App;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
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

  @FXML TableView<MoveAttribute> moveTable;

  @FXML TableColumn<MoveAttribute, String> nodeIDCol;

  @FXML TableColumn<MoveAttribute, String> nameCol;

  @FXML TableColumn<MoveAttribute, String> dateCol;

  FileChooser saveChooser = new FileChooser();
  FileChooser selectChooser = new FileChooser();

  @FXML
  public void initialize() {
    Popup windowPop = new Popup();
    Label popupLabel = new Label("Error: improper formatting");
    popupLabel.setStyle("-fx-background-color: red;");
    windowPop.getContent().add(popupLabel);
    windowPop.setAutoHide(true);

    // directoryChooser.setInitialDirectory(new File("src"));
    saveChooser.setTitle("Select where to save your file");
    saveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));
    selectChooser.setTitle("Select file to import");

    DatabaseController dC = new DatabaseController("teame", "teame50");

    // load the database into the table
    nodeIDCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("nodeID"));
    nameCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("longName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("date"));

    ObservableList itemList = FXCollections.observableArrayList(dC.getMoveList());
    moveTable.setItems(itemList);
    moveTable.setEditable(true);

    // testing stuff
    // dataTable.getItems().add(new MoveAttribute("1111", "testA", "4/2/2023"));
    // dataTable.getItems().add(new MoveAttribute("2222", "testB", "4/2/2023"));

    moveTable.setPlaceholder(new Label("No rows to display"));

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    deleteButton.setOnMouseClicked(
        event -> {
          MoveAttribute selectedItem = moveTable.getSelectionModel().getSelectedItem();
          if (selectedItem != null) {
            moveTable.getItems().remove(selectedItem);
            dC.deleteFromTable(selectedItem);
          }
        });

    App.getPrimaryStage()
        .addEventHandler(
            KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>() {
              @Override
              public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                  MoveAttribute selectedItem = moveTable.getSelectionModel().getSelectedItem();
                  if (selectedItem != null) {
                    moveTable.getItems().remove(selectedItem);
                    dC.deleteFromTable(selectedItem);
                  }
                }
              }
            });

    addButton.setOnMouseClicked(
        event -> {
          String nodeID = IDField.getText();
          String name = locationField.getText();
          String date = dateField.getText();
          MoveAttribute newMoveAttribute;
          try {
            newMoveAttribute = new MoveAttribute(nodeID, name, date);
            dC.addToTable(newMoveAttribute);
            moveTable.getItems().add(newMoveAttribute);
            IDField.clear();
            locationField.clear();
            dateField.clear();
          } catch (RuntimeException e) {
            // have an error pop up
            System.out.println("error caught");
            windowPop.show(App.getPrimaryStage());
          }
        });

    importButton.setOnMouseClicked(
        event -> {
          File selectedFile = selectChooser.showOpenDialog(App.getPrimaryStage());
          if (selectedFile == null) {
            // cancel
          } else {
            // add the file
            try {
              dC.importFromCSV(selectedFile.getAbsolutePath(), "Move");
            } catch (IOException e) {
              System.out.println("You messed up big time!!!!!!");
              System.out.println(e);
            }

            // System.out.println(selectedFile.getAbsolutePath());
          }
        });

    exportButton.setOnMouseClicked(
        event -> {
          // File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
          File selectedFile = saveChooser.showSaveDialog(App.getPrimaryStage());
          if (selectedFile == null) {
            // cancel
          } else {
            // export to the given path
            // System.out.println(selectedFile.getAbsolutePath());
            try {
              dC.exportToCSV(
                  "Move", selectedFile.getParentFile().getAbsolutePath(), selectedFile.getName());
            } catch (SQLException | IOException e) {
              System.out.println("You messed up big time!!!!!!");
              // System.out.println(e);
            }
          }
        });
  }
}
