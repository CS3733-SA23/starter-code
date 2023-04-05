package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.App;
import edu.wpi.teamA.database.DAOImps.NodeDAOImp;
import edu.wpi.teamA.database.ORMclasses.Node;
import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
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

  @FXML private TableView<Node> nodeTable;
  @FXML private TableColumn<Node, Integer> nodeIDCol;
  @FXML private TableColumn<Node, Integer> xCoorCol;
  @FXML private TableColumn<Node, Integer> yCoorCol;
  @FXML private TableColumn<Node, String> buildingCol;
  @FXML private TableColumn<Node, String> floorCol;
  @FXML private MFXButton importButton;
  @FXML private MFXButton exportButton;

  @FXML
  public void initialize() {
    //    table = new TableView<TableData>();
    //    nodeColumn = new TableColumn<TableData, String>("Node C");
    //    locationNameColumn = new TableColumn<TableData, String>("Location Name C");
    //    moveColumn = new TableColumn<TableData, String>("Move C");
    //    edgeDataColumn = new TableColumn<TableData, String>("Edge Data C");
    importButton.setOnMouseClicked(event -> importCSV());
    exportButton.setOnMouseClicked(event -> exportCSV());

    // displayNodeData();
  }

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }

  public ArrayList<Node> importCSV() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(App.getPrimaryStage());
    System.out.println(selectedFile.getPath());
    ArrayList<Node> nodeArray = NodeDAOImp.Import(selectedFile.getPath());
    displayNodeData(nodeArray);
    return nodeArray;
  }

  public void displayNodeData(ArrayList<Node> nodeArray) {
    nodeIDCol.setCellValueFactory(new PropertyValueFactory<Node, Integer>("nodeID"));
    xCoorCol.setCellValueFactory(new PropertyValueFactory<Node, Integer>("xcoord"));
    yCoorCol.setCellValueFactory(new PropertyValueFactory<Node, Integer>("ycoord"));
    floorCol.setCellValueFactory(new PropertyValueFactory<Node, String>("floor"));
    buildingCol.setCellValueFactory(new PropertyValueFactory<Node, String>("building"));

    nodeTable.setItems(FXCollections.observableArrayList(nodeArray));
    nodeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    nodeTable.getColumns(); // addAll(nodeIDCol, xCoorCol, yCoorCol, floorCol, buildingCol);
  }

  public void exportCSV() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Export CSV File to");
    File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
    System.out.println(selectedDirectory.getPath());
    NodeDAOImp.Export(selectedDirectory.getPath());
  }
}
