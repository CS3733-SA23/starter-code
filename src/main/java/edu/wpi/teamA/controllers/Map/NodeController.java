package edu.wpi.teamA.controllers.Map;

import edu.wpi.teamA.App;
import edu.wpi.teamA.database.DAOImps.NodeDAOImp;
import edu.wpi.teamA.database.ORMclasses.Node;
import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class NodeController {
  private TableView<Node> nodeTable;
  private TableColumn<Node, Integer> nodeIDCol;
  private TableColumn<Node, Integer> xCoorCol;
  private TableColumn<Node, Integer> yCoorCol;
  private TableColumn<Node, String> floorCol;
  private TableColumn<Node, String> buildingCol;

  public NodeController(
      TableView<Node> nodeTable,
      TableColumn<Node, Integer> nodeIDCol,
      TableColumn<Node, Integer> xCoorCol,
      TableColumn<Node, Integer> yCoorCol,
      TableColumn<Node, String> buildingCol,
      TableColumn<Node, String> floorCol) {
    this.nodeTable = nodeTable;
    this.nodeIDCol = nodeIDCol;
    this.xCoorCol = xCoorCol;
    this.yCoorCol = yCoorCol;
    this.floorCol = floorCol;
    this.buildingCol = buildingCol;
  }

  public ArrayList<Node> importCSV() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(App.getPrimaryStage());
    System.out.println(selectedFile.getPath());
    ArrayList<Node> nodeArray = NodeDAOImp.Import(selectedFile.getPath());
    // displayNodeData(nodeArray);
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
}
