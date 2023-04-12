package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.App;
import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.map.*;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import org.json.JSONObject;

public class DatabaseTableViewController {

  // common buttons:
  @FXML MFXButton importButton;
  @FXML MFXButton exportButton;
  @FXML MFXButton backButton;
  @FXML MFXButton deleteButton;
  @FXML MFXButton addNodeButton;
  @FXML MFXButton addMoveButton;
  @FXML MFXButton addLocationButton;
  @FXML MFXButton addEdgeButton;

  // Tabs
  @FXML TabPane tableTabs;
  @FXML Tab moveTab;
  @FXML Tab edgeTab;
  @FXML Tab nameTab;
  @FXML Tab requestTab;
  @FXML Tab nodeTab;

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
  SQLRepo.Table activeTableEnum;

  @FXML
  public void initialize() {
    Popup windowPop = new Popup();
    Label popupLabel = new Label("Error: improper formatting");
    popupLabel.setStyle("-fx-background-color: red;");
    windowPop.getContent().add(popupLabel);
    windowPop.setAutoHide(true);

    Popup confirmPop = new Popup();
    Label confirmLabel = new Label("Row added successfully");
    confirmLabel.setStyle("-fx-background-color: green;");
    confirmPop.getContent().add(confirmLabel);
    confirmPop.setAutoHide(true);

    saveChooser.setTitle("Select where to save your file");
    saveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", ".csv"));
    selectChooser.setTitle("Select file to import");

    SQLRepo dC = SQLRepo.INSTANCE;
    // dC.connectToDatabase("teame", "teame50");

    // load the database into the table
    nodeIDCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("nodeID"));
    nameCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("longName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<MoveAttribute, String>("date"));

    moveTable.setItems(FXCollections.observableArrayList(dC.getMoveList()));
    moveTable.setEditable(true);

    longNameCol.setCellValueFactory(new PropertyValueFactory<LocationName, String>("longName"));
    shortNameCol.setCellValueFactory(new PropertyValueFactory<LocationName, String>("shortName"));
    nodeTypeCol.setCellValueFactory(new PropertyValueFactory<LocationName, String>("nodeType"));

    locationTable.setItems(FXCollections.observableArrayList(dC.getLocationList()));
    locationTable.setEditable(true);

    nodeIDCoordCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, String>("nodeID"));
    nodeXCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Integer>("xCoord"));
    nodeYCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Integer>("yCoord"));
    floorCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, Floor>("floor"));
    buildingCol.setCellValueFactory(new PropertyValueFactory<HospitalNode, String>("building"));
    nodeTable.setItems(FXCollections.observableArrayList(dC.getNodeList()));
    nodeTable.setEditable(true);

    edge1Col.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeOneID"));
    edge2Col.setCellValueFactory(new PropertyValueFactory<HospitalEdge, String>("nodeTwoID"));

    edgeTable.setItems(FXCollections.observableArrayList(dC.getEdgeList()));
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

    requestTable.setItems(FXCollections.observableArrayList(dC.getServiceRequestList()));
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
                if (event.getCode() == KeyCode.BACK_SPACE
                    && !activeTableEnum.equals(SQLRepo.Table.SERVICE_REQUESTS)) {
                  removeItem();
                }
              }
            });

    addMoveButton.setOnMouseClicked(
        event -> {
          addMove(windowPop, confirmPop);
        });

    addEdgeButton.setOnMouseClicked(
        event -> {
          addEdge(windowPop, confirmPop);
        });

    addLocationButton.setOnMouseClicked(
        event -> {
          addLocation(windowPop, confirmPop);
        });

    addNodeButton.setOnMouseClicked(
        event -> {
          addNode(windowPop, confirmPop);
        });

    // Event handlers for switching tabs changing the current tab variable
    nameTab.setOnSelectionChanged(
        new EventHandler<Event>() {
          @Override
          public void handle(Event event) {
            changeTab(nameTab, SQLRepo.Table.LOCATION_NAME);
          }
        });

    edgeTab.setOnSelectionChanged(
        new EventHandler<Event>() {
          @Override
          public void handle(Event event) {
            changeTab(edgeTab, SQLRepo.Table.EDGE);
          }
        });

    moveTab.setOnSelectionChanged(
        new EventHandler<Event>() {
          @Override
          public void handle(Event event) {
            changeTab(moveTab, SQLRepo.Table.MOVE);
          }
        });

    nodeTab.setOnSelectionChanged(
        new EventHandler<Event>() {
          @Override
          public void handle(Event event) {
            changeTab(nodeTab, SQLRepo.Table.NODE);
          }
        });

    requestTab.setOnSelectionChanged(
        new EventHandler<Event>() {
          @Override
          public void handle(Event event) {
            changeTab(requestTab, SQLRepo.Table.SERVICE_REQUESTS);
          }
        });

    importButton.setOnMouseClicked(
        event -> {
          File selectedFile = selectChooser.showOpenDialog(App.getPrimaryStage());
          if (selectedFile == null) {
            // cancel
          } else {
            // add the file

            dC.importFromCSV(activeTableEnum, selectedFile.getAbsolutePath());
            // refresh
            switch (activeTableEnum) {
              case MOVE:
                activeTable.setItems(FXCollections.observableArrayList(dC.getMoveList()));
                break;
              case NODE:
                activeTable.setItems(FXCollections.observableArrayList(dC.getNodeList()));
                break;
              case LOCATION_NAME:
                activeTable.setItems(FXCollections.observableArrayList(dC.getLocationList()));
                break;
              case EDGE:
                activeTable.setItems(FXCollections.observableArrayList(dC.getEdgeList()));
                break;
            }
            /*
            try {
              dC.importFromCSV(
                      activeTableEnum,
                  selectedFile.getAbsolutePath());
              // refresh
              switch (activeTableEnum) {
                case MOVE:
                  activeTable.setItems(FXCollections.observableArrayList(dC.getMoveList()));
                  break;
                case NODE:
                  activeTable.setItems(FXCollections.observableArrayList(dC.getNodeList()));
                  break;
                case LOCATION_NAME:
                  activeTable.setItems(FXCollections.observableArrayList(dC.getLocationList()));
                  break;
                case EDGE:
                  activeTable.setItems(FXCollections.observableArrayList(dC.getEdgeList()));
                  break;
              }

            } catch (IOException e) {
              System.out.println("You messed up big time!!!!!!");
              System.out.println(e);
            }

             */
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

            dC.exportToCSV(
                activeTableEnum,
                selectedFile.getParentFile().getAbsolutePath(),
                selectedFile.getName());
            /*
            try {
              dC.exportToCSV(
                  activeTableEnum,
                  selectedFile.getParentFile().getAbsolutePath(),
                  selectedFile.getName());
            } catch (SQLException | IOException e) {
              System.out.println("You messed up big time!!!!!!");
              System.out.println(e);
            }

             */
          }
        });
  }

  private void addNode(Popup windowPop, Popup confirmPop) {
    HospitalNode toAdd;
    String nodeI = (IDFieldLoc.getText());
    int nodeX;
    int nodeY;
    String flr = floorField.getText();
    String building = buildingField.getText();
    try {
      nodeX = Integer.parseInt(xField.getText());
      nodeY = Integer.parseInt(yField.getText());
      toAdd = new HospitalNode(new NodeInitializer(nodeI, nodeX, nodeY, flr, building));
      // DatabaseController.INSTANCE.addToTable(DatabaseController.Table.NODE, toAdd);
      SQLRepo.INSTANCE.addNode(toAdd);
      confirmPop.show(App.getPrimaryStage());
      nodeTable.getItems().add((HospitalNode) toAdd);
      IDFieldLoc.clear();
      xField.clear();
      yField.clear();
      floorField.clear();
      buildingField.clear();
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      windowPop.show(App.getPrimaryStage());
    }
  }

  private void addLocation(Popup windowPop, Popup confirmPop) {
    LocationName toAdd;
    String longName = longNameField.getText();
    String shortName = shortNameField.getText();
    LocationName.NodeType type =
        LocationName.NodeType.stringToNodeType(locationTypeField.getText());
    try {
      toAdd = new LocationName(longName, shortName, type);
      // DatabaseController.INSTANCE.addToTable(DatabaseController.Table.LOCATION_NAME, toAdd);
      SQLRepo.INSTANCE.addLocation(toAdd);
      confirmPop.show(App.getPrimaryStage());
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
  }

  private void addMove(Popup windowPop, Popup confirmPop) {
    MoveAttribute toAdd;
    String nodeID = IDField.getText();
    String name = locationField.getText();
    String date = dateField.getText();
    // MoveAttribute newMoveAttribute;
    try {
      toAdd = new MoveAttribute(nodeID, name, date);
      // DatabaseController.INSTANCE.addToTable(DatabaseController.Table.MOVE, toAdd);
      SQLRepo.INSTANCE.addMove(toAdd);
      confirmPop.show(App.getPrimaryStage());
      moveTable.getItems().add(toAdd);
      IDField.clear();
      locationField.clear();
      dateField.clear();
    } catch (RuntimeException e) {
      // have an error pop up
      System.out.println("error caught");
      windowPop.show(App.getPrimaryStage());
    }
  }

  private void addEdge(Popup windowPop, Popup confirmPop) {
    HospitalEdge toAdd;
    String edge1 = edge1Field.getText();
    String edge2 = edge2Field.getText();
    try {
      toAdd = new HospitalEdge(edge1, edge2);
      // DatabaseController.INSTANCE.addToTable(DatabaseController.Table.EDGE, toAdd);
      SQLRepo.INSTANCE.addEdge(toAdd);
      confirmPop.show(App.getPrimaryStage());
      edgeTable.getItems().add(toAdd);
      edge1Field.clear();
      edge2Field.clear();
    } catch (RuntimeException e) {
      // have an error pop up
      System.out.println("error caught");
      windowPop.show(App.getPrimaryStage());
    }
  }

  private void removeItem() {
    Object selectedItem = activeTable.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
      activeTable.getItems().remove(selectedItem);
      // DatabaseController.INSTANCE.deleteFromTable(activeTableEnum, selectedItem);
      switch (activeTableEnum) {
        case NODE:
          SQLRepo.INSTANCE.deletenode((HospitalNode) selectedItem);
          break;
        case LOCATION_NAME:
          SQLRepo.INSTANCE.deleteLocation((LocationName) selectedItem);
          break;
        case MOVE:
          SQLRepo.INSTANCE.deleteMove((MoveAttribute) selectedItem);
          break;
        case SERVICE_REQUESTS:
          SQLRepo.INSTANCE.deleteServiceRequest((ServiceRequestData) selectedItem);
          break;
        case EDGE:
          SQLRepo.INSTANCE.deleteEdge((HospitalEdge) selectedItem);
          break;
      }
    }
  }

  private void changeTab(Tab tb, SQLRepo.Table table) {
    if (tb.isSelected()) {
      switch (table) {
        case EDGE:
          activeTable = edgeTable;
          activeTableEnum = table;
          importButton.setDisable(false);
          exportButton.setDisable(false);
          deleteButton.setDisable(false);
          break;
        case MOVE:
          activeTable = moveTable;
          activeTableEnum = table;
          importButton.setDisable(false);
          exportButton.setDisable(false);
          deleteButton.setDisable(false);
          break;
        case NODE:
          activeTable = nodeTable;
          activeTableEnum = table;
          importButton.setDisable(false);
          exportButton.setDisable(false);
          deleteButton.setDisable(false);
          break;
        case LOCATION_NAME:
          activeTable = locationTable;
          activeTableEnum = table;
          importButton.setDisable(false);
          exportButton.setDisable(false);
          deleteButton.setDisable(false);
          break;
        case SERVICE_REQUESTS:
          activeTable = requestTable;
          activeTableEnum = table;
          importButton.setDisable(false);
          exportButton.setDisable(false);
          deleteButton.setDisable(false);
          break;
      }
    }
  }
}
