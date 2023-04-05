package edu.wpi.teame.controllers;

import Database.DatabaseController;
import edu.wpi.teame.App;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.map.*;
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
import org.json.JSONObject;

public class DatabaseViewController {

  // common buttons:
  @FXML MFXButton importButton;
  @FXML MFXButton exportButton;
  @FXML MFXButton backButton;
  @FXML MFXButton deleteButton;
  @FXML MFXButton addButton; // three text boxes and a button that says "add" next to it

  @FXML ComboBox<DatabaseController.Table> databaseChoice;

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

  // table data for service requests
  @FXML TableView<ServiceRequestData> requestTable;
  @FXML TableColumn<ServiceRequestData, JSONObject> dataCol;
  @FXML TableColumn<ServiceRequestData, ServiceRequestData.RequestType> typeCol;
  @FXML TableColumn<ServiceRequestData, ServiceRequestData.Status> statusCol;
  @FXML TableColumn<ServiceRequestData, String> staffCol;

  FileChooser saveChooser = new FileChooser();
  FileChooser selectChooser = new FileChooser();

  TableView activeTable;

  @FXML
  public void initialize() {
    ArrayList<DatabaseController.Table> choices = new ArrayList<>();
    choices.add(DatabaseController.Table.MOVE);
    choices.add(DatabaseController.Table.LOCATION_NAME);
    choices.add(DatabaseController.Table.NODE);
    choices.add(DatabaseController.Table.EDGE);
    choices.add(DatabaseController.Table.SERVICE_REQUESTS);
    databaseChoice.setItems(FXCollections.observableArrayList(choices));

    databaseChoice.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            switchActiveTable(databaseChoice.getValue());
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
    locationTable.setItems(locationList);
    locationTable.setEditable(true);

    nodeIDCoordCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, String>("nodeID"));
    nodeXCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Integer>("xCoord"));
    nodeYCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Integer>("yCoord"));
    floorCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Floor>("floor"));
    buildingCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, String>("building"));
    ObservableList nodeList = FXCollections.observableArrayList(dC.getNodes());
    nodeTable.setItems(nodeList);
    nodeTable.setEditable(true);

    edge1Col.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeOneID"));
    edge2Col.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeTwoID"));

    ObservableList edgeList = FXCollections.observableArrayList(dC.getEdges());

    edgeTable.setItems(edgeList);
    edgeTable.setEditable(true);

    dataCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequestData, JSONObject>("requestData"));
    typeCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequestData, ServiceRequestData.RequestType>(
            "requestType"));
    statusCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequestData, ServiceRequestData.Status>("requestStatus"));
    staffCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequestData, String>("assignedStaff"));

    // ObservableList requestList = FXCollections.observableArrayList(dC.get)
    requestTable.setEditable(true);

    moveTable.setPlaceholder(new Label("No rows to display"));

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    deleteButton.setOnMouseClicked(
        event -> {
          removeItem();
        });

    App.getPrimaryStage()
        .addEventHandler(
            KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>() {
              @Override
              public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                  removeItem();
                }
              }
            });

    addButton.setOnMouseClicked(
        event -> {
          addRow(windowPop);
        });

    importButton.setOnMouseClicked(
        event -> {
          File selectedFile = selectChooser.showOpenDialog(App.getPrimaryStage());
          if (selectedFile == null) {
            // cancel
          } else {
            // add the file
            try {
              dC.importFromCSV(
                  selectedFile.getAbsolutePath(),
                  DatabaseController.Table.tableToString(databaseChoice.getValue()));
            } catch (IOException e) {
              System.out.println("You messed up big time!!!!!!");
              System.out.println(e);
            }
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
            try {
              dC.exportToCSV(
                  DatabaseController.Table.tableToString(databaseChoice.getValue()),
                  selectedFile.getParentFile().getAbsolutePath(),
                  selectedFile.getName());
            } catch (SQLException | IOException e) {
              System.out.println("You messed up big time!!!!!!");
            }
          }
        });
  }

  private void switchActiveTable(DatabaseController.Table db) {
    switch (db) {
      case MOVE:
        activeTable = moveTable;

        moveTable.setVisible(true);
        locationTable.setVisible(false);
        nodeTable.setVisible(false);
        edgeTable.setVisible(false);
        requestTable.setVisible(false);

        movesAddZone.setVisible(true);
        locationAddZone.setVisible(false);
        nodeAddZone.setVisible(false);
        edgeAddZone.setVisible(false);

        addButton.setDisable(false);
        deleteButton.setDisable(false);
        break;
      case LOCATION_NAME:
        activeTable = locationTable;

        moveTable.setVisible(false);
        locationTable.setVisible(true);
        nodeTable.setVisible(false);
        edgeTable.setVisible(false);
        requestTable.setVisible(false);

        movesAddZone.setVisible(false);
        locationAddZone.setVisible(true);
        nodeAddZone.setVisible(false);
        edgeAddZone.setVisible(false);

        addButton.setDisable(false);
        deleteButton.setDisable(false);
        break;
      case NODE:
        activeTable = nodeTable;

        moveTable.setVisible(false);
        locationTable.setVisible(false);
        nodeTable.setVisible(true);
        edgeTable.setVisible(false);
        requestTable.setVisible(false);

        movesAddZone.setVisible(false);
        locationAddZone.setVisible(false);
        nodeAddZone.setVisible(true);
        edgeAddZone.setVisible(false);

        addButton.setDisable(false);
        deleteButton.setDisable(false);
        break;
      case EDGE:
        activeTable = edgeTable;

        moveTable.setVisible(false);
        locationTable.setVisible(false);
        nodeTable.setVisible(false);
        edgeTable.setVisible(true);
        requestTable.setVisible(false);

        movesAddZone.setVisible(false);
        locationAddZone.setVisible(false);
        nodeAddZone.setVisible(false);
        edgeAddZone.setVisible(true);

        addButton.setDisable(false);
        deleteButton.setDisable(false);
        break;
      case SERVICE_REQUESTS:
        activeTable = requestTable;
        moveTable.setVisible(false);
        locationTable.setVisible(false);
        nodeTable.setVisible(false);
        edgeTable.setVisible(false);
        requestTable.setVisible(true);

        movesAddZone.setVisible(false);
        locationAddZone.setVisible(false);
        nodeAddZone.setVisible(false);
        edgeAddZone.setVisible(false);

        addButton.setDisable(true);
        deleteButton.setDisable(true);
        break;
    }
    if (!addButton.isVisible()) {
      addButton.setVisible(true);
    }
  }

  private void addRow(Popup windowPop) {
    Object toAdd;
    switch (databaseChoice.getValue()) {
      case MOVE:
        String nodeID = IDField.getText();
        String name = locationField.getText();
        String date = dateField.getText();
        // MoveAttribute newMoveAttribute;
        try {
          toAdd = new MoveAttribute(nodeID, name, date);
          DatabaseController.INSTANCE.addToTable(DatabaseController.Table.MOVE, toAdd);
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
      case LOCATION_NAME:
        String longName = longNameField.getText();
        String shortName = shortNameField.getText();
        LocationName.NodeType type =
            LocationName.NodeType.stringToNodeType(locationTypeField.getText());
        try {
          toAdd = new LocationName(longName, shortName, type);
          DatabaseController.INSTANCE.addToTable(DatabaseController.Table.LOCATION_NAME, toAdd);
          locationTable.getItems().add((LocationName) toAdd);
          longNameField.clear();
          shortNameField.clear();
          locationTypeField.clear();
        } catch (RuntimeException e) {
          // have an error pop up
          System.out.println("error caught");
          windowPop.show(App.getPrimaryStage());
          System.out.println(e);
        }
        break;
      case NODE:
        String nodeI = (IDFieldLoc.getText());
        int nodeX = Integer.parseInt(xField.getText());
        int nodeY = Integer.parseInt(yField.getText());
        String flr = floorField.getText();
        String building = buildingField.getText();
        try {
          toAdd = new HospitalNode(new NodeInitializer(nodeI, nodeX, nodeY, flr, building));
          DatabaseController.INSTANCE.addToTable(DatabaseController.Table.NODE, toAdd);
          nodeTable.getItems().add((HospitalNode) toAdd);
          IDFieldLoc.clear();
          xField.clear();
          yField.clear();
          floorField.clear();
          buildingField.clear();
        } catch (RuntimeException e) {
          // have an error pop up
          System.out.println(e.getMessage());
          windowPop.show(App.getPrimaryStage());
        }
        break;
      case EDGE:
        String edge1 = edge1Field.getText();
        String edge2 = edge2Field.getText();
        try {
          toAdd = new HospitalEdge(edge1, edge2);
          DatabaseController.INSTANCE.addToTable(DatabaseController.Table.EDGE, toAdd);
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

  private void removeItem() {
    Object selectedItem = activeTable.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
      activeTable.getItems().remove(selectedItem);
      DatabaseController.INSTANCE.deleteFromTable(databaseChoice.getValue(), selectedItem);
    }
  }
}
