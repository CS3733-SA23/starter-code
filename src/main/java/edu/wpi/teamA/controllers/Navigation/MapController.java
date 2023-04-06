package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.App;
import edu.wpi.teamA.database.DAOImps.EdgeDAOImp;
import edu.wpi.teamA.database.DAOImps.LocNameDAOImp;
import edu.wpi.teamA.database.DAOImps.MoveDAOImp;
import edu.wpi.teamA.database.DAOImps.NodeDAOImp;
import edu.wpi.teamA.database.ORMclasses.Edge;
import edu.wpi.teamA.database.ORMclasses.LocationName;
import edu.wpi.teamA.database.ORMclasses.Move;
import edu.wpi.teamA.database.ORMclasses.Node;
import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class MapController implements IPageController {

  // Node table
  @FXML private TableView<Node> nodeTable;
  @FXML private TableColumn<Node, Integer> nodeIDCol;
  @FXML private TableColumn<Node, Integer> xCoorCol;
  @FXML private TableColumn<Node, Integer> yCoorCol;
  @FXML private TableColumn<Node, String> floorCol;
  @FXML private TableColumn<Node, String> buildingCol;

  // Location Name table
  @FXML private TableView<LocationName> locationNameTable;
  @FXML private TableColumn<LocationName, String> longNameCol;
  @FXML private TableColumn<LocationName, String> shortNameCol;
  @FXML private TableColumn<LocationName, String> nodeTypeCol;

  // Move table
  @FXML private TableView<Move> moveTable;
  @FXML private TableColumn<Move, Integer> moveNodeIDCol;
  @FXML private TableColumn<Move, String> moveLongNameCol;
  @FXML private TableColumn<Move, LocalDate> dateCol;

  // Edge table
  @FXML private TableView<Edge> edgeTable;
  @FXML private TableColumn<Edge, Integer> startNodeCol;
  @FXML private TableColumn<Edge, Integer> endNodeCol;

  @FXML private MFXButton nodeImportButton;
  @FXML private MFXButton nodeExportButton;
  @FXML private MFXButton locationImportButton;
  @FXML private MFXButton locationExportButton;
  @FXML private MFXButton moveImportButton;
  @FXML private MFXButton moveExportButton;
  @FXML private MFXButton edgeImportButton;
  @FXML private MFXButton edgeExportButton;

  @FXML
  public void initialize() {
    nodeImportButton.setOnMouseClicked(event -> importNodeCSV());
    nodeExportButton.setOnMouseClicked(event -> exportNodeCSV());
    locationImportButton.setOnMouseClicked(event -> importLocNameCSV());
    locationExportButton.setOnMouseClicked(event -> exportLocNameCSV());
    moveImportButton.setOnMouseClicked(event -> importMoveCSV());
    moveExportButton.setOnMouseClicked(event -> exportMoveCSV());
    edgeImportButton.setOnMouseClicked(event -> importEdgeCSV());
    edgeExportButton.setOnMouseClicked(event -> exportEdgeCSV());
  }

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }

  private File importHelper() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(App.getPrimaryStage());
    System.out.println(selectedFile.getPath());
    return selectedFile;
  }

  public void importNodeCSV() {
    File selectedFile = importHelper();
    ArrayList<Node> nodeArray = NodeDAOImp.Import(selectedFile.getPath());
    displayNodeData(nodeArray);
  }

  public void displayNodeData(ArrayList<Node> nodeArray) {
    nodeIDCol.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    xCoorCol.setCellValueFactory(new PropertyValueFactory<>("xcoord"));
    yCoorCol.setCellValueFactory(new PropertyValueFactory<>("ycoord"));
    floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
    buildingCol.setCellValueFactory(new PropertyValueFactory<>("building"));

    nodeTable.setItems(FXCollections.observableArrayList(nodeArray));
    nodeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    // nodeTable.getColumns();
  }

  public void importLocNameCSV() {
    File selectedFile = importHelper();
    ArrayList<LocationName> locNameArray = LocNameDAOImp.Import(selectedFile.getPath());
    displayLocNameData(locNameArray);
  }

  public void displayLocNameData(ArrayList<LocationName> locNameArray) {
    longNameCol.setCellValueFactory(new PropertyValueFactory<>("LongName"));
    shortNameCol.setCellValueFactory(new PropertyValueFactory<>("ShortName"));
    nodeTypeCol.setCellValueFactory(new PropertyValueFactory<>("NodeType"));

    locationNameTable.setItems(FXCollections.observableArrayList(locNameArray));
    locationNameTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    // locationNameTable.getColumns();
  }

  public void importMoveCSV() {
    File selectedFile = importHelper();
    ArrayList<Move> moveArray = MoveDAOImp.Import(selectedFile.getPath());
    displayMoveData(moveArray);
  }

  public void displayMoveData(ArrayList<Move> moveArray) {
    moveNodeIDCol.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    moveLongNameCol.setCellValueFactory(new PropertyValueFactory<>("longName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    moveTable.setItems(FXCollections.observableArrayList(moveArray));
    moveTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    // moveTable.getColumns();
  }

  public void importEdgeCSV() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(App.getPrimaryStage());
    System.out.println(selectedFile.getPath());
    ArrayList<Edge> edgeArray = EdgeDAOImp.Import(selectedFile.getPath());
    displayEdgeData(edgeArray);
  }

  public void displayEdgeData(ArrayList<Edge> edgeArray) {
    startNodeCol.setCellValueFactory(new PropertyValueFactory<>("startNode"));
    endNodeCol.setCellValueFactory(new PropertyValueFactory<>("endNode"));

    edgeTable.setItems(FXCollections.observableArrayList(edgeArray));
    edgeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    // edgeTable.getColumns();
  }

  public void exportNodeCSV() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Export CSV File to");
    File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
    System.out.println(selectedDirectory.getPath());
    NodeDAOImp.Export(selectedDirectory.getPath());
  }

  public void exportLocNameCSV() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Export CSV File to");
    File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
    System.out.println(selectedDirectory.getPath());
    LocNameDAOImp.Export(selectedDirectory.getPath());
  }

  public void exportMoveCSV() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Export CSV File to");
    File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
    System.out.println(selectedDirectory.getPath());
    MoveDAOImp.Export(selectedDirectory.getPath());
  }

  public void exportEdgeCSV() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Export CSV File to");
    File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
    System.out.println(selectedDirectory.getPath());
    EdgeDAOImp.Export(selectedDirectory.getPath());
  }
}
