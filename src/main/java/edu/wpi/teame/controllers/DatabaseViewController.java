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
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import pathfinding.*;

public class DatabaseViewController {

  // common buttons:
  @FXML MFXButton importButton;
  @FXML MFXButton exportButton;
  @FXML MFXButton backButton;
  @FXML MFXButton deleteButton;
  @FXML MFXButton addButton; // three text boxes and a button that says "add" next to it

  @FXML ComboBox<String> databaseChoice;

  // fields for Moves
  @FXML HBox movesAddZone;
  @FXML MFXTextField IDField;
  @FXML MFXTextField locationField;
  @FXML MFXTextField dateField;

  // table data for Moves
  @FXML TableView<MoveAttribute> moveTable;

  @FXML TableColumn<MoveAttribute, String> nodeIDCol;

  @FXML TableColumn<MoveAttribute, String> nameCol;

  @FXML TableColumn<MoveAttribute, String> dateCol;

  // fields for Nodes
  @FXML HBox nodeAddZone;
  @FXML MFXTextField IDFieldLoc;
  @FXML MFXTextField xField;
  @FXML MFXTextField yField;
  @FXML MFXTextField floorField;
  @FXML MFXTextField buildingField;

  // table data for Nodes
  @FXML TableView<HospitalNode> nodeTable;
  @FXML TableColumn<HospitalNode, String> nodeIDCoordCol;
  @FXML TableColumn<HospitalNode, Integer> nodeXCol;
  @FXML TableColumn<HospitalNode, Integer> nodeYCol;
  @FXML TableColumn<HospitalNode, Floor> floorCol;
  @FXML TableColumn<HospitalNode, String> buildingCol;

  // fields for Location Names
  @FXML HBox locationAddZone;
  @FXML MFXTextField longNameField;
  @FXML MFXTextField shortNameField;
  @FXML MFXTextField locationTypeField;

  // table data for Location Names
  @FXML TableView<LocationName> locationTable;
  @FXML TableColumn<LocationName, String> longNameCol;
  @FXML TableColumn<LocationName, String> shortNameCol;
  @FXML TableColumn<LocationName, String> nodeTypeCol;

  // fields for Edges
  @FXML HBox edgeAddZone;
  @FXML MFXTextField edge1Field;
  @FXML MFXTextField edge2Field;

  // table data for Edges
  @FXML TableView<HospitalEdge> edgeTable;
  @FXML TableColumn<HospitalEdge, String> edge1Col;
  @FXML TableColumn<HospitalEdge, String> edge2Col;

  FileChooser saveChooser = new FileChooser();
  FileChooser selectChooser = new FileChooser();

  TableView activeTable;

  @FXML
  public void initialize() {
    ArrayList<String> choices = new ArrayList<String>();
    choices.add("move");
    choices.add("location");
    choices.add("node");
    choices.add("edge");
    databaseChoice.setItems(FXCollections.observableArrayList(choices));

    databaseChoice.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            switchActiveTable(databaseChoice.getValue());
            // System.out.println(databaseChoice.getValue());
          }
        });

    Popup windowPop = new Popup();
    Label popupLabel = new Label("Error: improper formatting");
    popupLabel.setStyle("-fx-background-color: red;");
    windowPop.getContent().add(popupLabel);
    windowPop.setAutoHide(true);
    saveChooser.setTitle("Select where to save your file");
    saveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));
    selectChooser.setTitle("Select file to import");

    DatabaseController dC = DatabaseController.INSTANCE;

    // load the database into the table
    nodeIDCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("nodeID"));
    nameCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("longName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("date"));

    ObservableList moveList = FXCollections.observableArrayList(dC.getMoveList());
    moveTable.setItems(moveList);
    moveTable.setEditable(true);

    longNameCol.setCellValueFactory(new PropertyValueFactory<LocationName, String>("longName"));
    shortNameCol.setCellValueFactory(new PropertyValueFactory<LocationName, String>("shortName"));
    nodeTypeCol.setCellValueFactory(new PropertyValueFactory<LocationName, String>("nodeType"));

    ObservableList locationList = FXCollections.observableArrayList(dC.getLocationName());
    //ArrayList<LocationName> locationArrList = new ArrayList<>();
    //locationArrList.add(new LocationName("test long", "testshort", LocationName.NodeType.INFO));
    //locationArrList.add(new LocationName("test looong", "tstshrt", LocationName.NodeType.EXIT));
    //ObservableList locationList = FXCollections.observableArrayList(locationArrList);
    locationTable.setItems(locationList);
    locationTable.setEditable(true);

    nodeIDCoordCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, String>("nodeID"));
    nodeXCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Integer>("xCoord"));
    nodeYCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Integer>("yCoord"));
    floorCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Floor>("floor"));
    buildingCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, String>("building"));
    ObservableList nodeList = FXCollections.observableArrayList(dC.getNodes());
    //ArrayList<HospitalNode> nodeArrList = new ArrayList<>();
    //nodeArrList.add(new HospitalNode("22222"));
    //nodeArrList.add(new HospitalNode("120", 4, 5, Floor.LOWER_ONE, "Testing"));
    //nodeArrList.add(new HospitalNode());
    //ObservableList nodeList = FXCollections.observableArrayList(nodeArrList);
    nodeTable.setItems(nodeList);
    nodeTable.setEditable(true);

    edge1Col.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeOneID"));
    edge2Col.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeTwoID"));

    ObservableList edgeList = FXCollections.observableArrayList(dC.getEdges());

    edgeTable.setItems(edgeList);
    edgeTable.setEditable(true);

    // testing stuff
    // dataTable.getItems().add(new MoveAttribute("1111", "testA", "4/2/2023"));
    // dataTable.getItems().add(new MoveAttribute("2222", "testB", "4/2/2023"));

    moveTable.setPlaceholder(new Label("No rows to display"));

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    deleteButton.setOnMouseClicked(
        event -> {
          removeItem(dC);
          /*
          Object selectedItem = activeTable.getSelectionModel().getSelectedItem();
          if (selectedItem != null) {
            moveTable.getItems().remove(selectedItem);
            //dC.deleteFromTable( ,selectedItem);
          }

           */
        });

    App.getPrimaryStage()
        .addEventHandler(
            KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>() {
              @Override
              public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                  removeItem(dC);
                  /*
                  MoveAttribute selectedItem = moveTable.getSelectionModel().getSelectedItem();
                  if (selectedItem != null) {
                    moveTable.getItems().remove(selectedItem);
                    dC.deleteFromTable(selectedItem);
                  }

                   */
                }
              }
            });

    addButton.setOnMouseClicked(
        event -> {
          addRow(dC, windowPop);
          /*
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

           */
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

  private void switchActiveTable(String db) {
    switch (db) {
      case "move":
        activeTable = moveTable;

        moveTable.setVisible(true);
        locationTable.setVisible(false);
        nodeTable.setVisible(false);
        edgeTable.setVisible(false);

        movesAddZone.setVisible(true);
        locationAddZone.setVisible(false);
        nodeAddZone.setVisible(false);
        edgeAddZone.setVisible(false);
        break;
      case "location":
        activeTable = locationTable;

        moveTable.setVisible(false);
        locationTable.setVisible(true);
        nodeTable.setVisible(false);
        edgeTable.setVisible(false);

        movesAddZone.setVisible(false);
        locationAddZone.setVisible(true);
        nodeAddZone.setVisible(false);
        edgeAddZone.setVisible(false);
        break;
      case "node":
        activeTable = nodeTable;

        moveTable.setVisible(false);
        locationTable.setVisible(false);
        nodeTable.setVisible(true);
        edgeTable.setVisible(false);

        movesAddZone.setVisible(false);
        locationAddZone.setVisible(false);
        nodeAddZone.setVisible(true);
        edgeAddZone.setVisible(false);
        break;
      case "edge":
        activeTable = edgeTable;

        moveTable.setVisible(false);
        locationTable.setVisible(false);
        nodeTable.setVisible(false);
        edgeTable.setVisible(true);

        movesAddZone.setVisible(false);
        locationAddZone.setVisible(false);
        nodeAddZone.setVisible(false);
        edgeAddZone.setVisible(true);
        break;
    }
    if (!addButton.isVisible()) {
      addButton.setVisible(true);
    }
  }

  private void addRow(DatabaseController dC, Popup windowPop) {
    Object toAdd;
    switch (databaseChoice.getValue()) {
      case "move":
        String nodeID = IDField.getText();
        String name = locationField.getText();
        String date = dateField.getText();
        // MoveAttribute newMoveAttribute;
        try {
          toAdd = new MoveAttribute(nodeID, name, date);
          dC.addToTable(DatabaseController.Table.MOVE, toAdd);
          moveTable.getItems().add((MoveAttribute) toAdd);
          IDField.clear();
          locationField.clear();
          dateField.clear();
        } catch (RuntimeException e) {
          // have an error pop up
          System.out.println("error caught");
          windowPop.show(App.getPrimaryStage());
        }
        break;
      case "location":
        String longName = longNameField.getText();
        String shortName = shortNameField.getText();
        LocationName.NodeType type =
            LocationName.NodeType.stringToNodeType(locationTypeField.getText());
        try {
          toAdd = new LocationName(longName, shortName, type);
          dC.addToTable(DatabaseController.Table.LOCATION_NAME, toAdd);
          locationTable.getItems().add((LocationName) toAdd);
          longNameField.clear();
          shortNameField.clear();
          locationTypeField.clear();
        } catch (RuntimeException e) {
          // have an error pop up
          System.out.println("error caught");
          windowPop.show(App.getPrimaryStage());
        }
        break;
      case "node":
        String nodeI = IDFieldLoc.getText();
        int nodeX = Integer.parseInt(xField.getText());
        int nodeY = Integer.parseInt(yField.getText());
        Floor flr = Floor.stringToFloor(floorField.getText());
        String building = buildingField.getText();
        try {
          toAdd = new HospitalNode(nodeI, nodeX, nodeY, flr, building);
          dC.addToTable(DatabaseController.Table.NODE, toAdd);
          nodeTable.getItems().add((HospitalNode) toAdd);
          IDFieldLoc.clear();
          xField.clear();
          yField.clear();
          floorField.clear();
          buildingField.clear();
        } catch (RuntimeException e) {
          // have an error pop up
          System.out.println("error caught");
          windowPop.show(App.getPrimaryStage());
        }
        break;
      case "edge":
        String edge1 = edge1Field.getText();
        String edge2 = edge2Field.getText();
        try {
          toAdd = new HospitalEdge(edge1, edge2);
          dC.addToTable(DatabaseController.Table.EDGE, toAdd);
          edgeTable.getItems().add((HospitalEdge) toAdd);
          edge1Field.clear();
          edge2Field.clear();
        } catch (RuntimeException e) {
          // have an error pop up
          System.out.println("error caught");
          windowPop.show(App.getPrimaryStage());
        }
        break;
    }
  }

  private void removeItem(DatabaseController dC) {
    Object selectedItem = activeTable.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
      activeTable.getItems().remove(selectedItem);
      switch (databaseChoice.getValue()) {
        case "move":
          dC.deleteFromTable(DatabaseController.Table.MOVE, selectedItem);
          break;
        case "location":
          dC.deleteFromTable(DatabaseController.Table.LOCATION_NAME, selectedItem);
          break;
        case "node":
          dC.deleteFromTable(DatabaseController.Table.NODE, selectedItem);
          break;
        case "edge":
          dC.deleteFromTable(DatabaseController.Table.EDGE, selectedItem);
          break;
      }
      // dC.deleteFromTable( ,selectedItem);
    }
  }
}
