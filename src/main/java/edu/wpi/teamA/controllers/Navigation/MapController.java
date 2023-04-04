package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.App;
import edu.wpi.teamA.database.*;
import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class MapController implements IPageController {

  @FXML private TableView<TableData> table;
  @FXML private TableColumn<TableData, Node> nodeColumn;
  @FXML private TableColumn<TableData, String> locationNameColumn;
  @FXML private TableColumn<TableData, String> moveColumn;
  @FXML private TableColumn<TableData, String> edgeDataColumn;
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

    //    ObservableList<TableData> data =
    //        FXCollections.observableArrayList(
    //            new TableData("file1", "file1", "25 MB", "12/01/2017"),
    //            new TableData("file2", "file2", "30 MB", "01/11/2019"),
    //            new TableData("file3", "file3", "50 MB", "12/04/2017"),
    //            new TableData("file4", "file4", "75 MB", "25/09/2018"));

    nodeColumn.setCellValueFactory(new PropertyValueFactory<>("node"));
    locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));
    moveColumn.setCellValueFactory(new PropertyValueFactory<>("move"));
    edgeDataColumn.setCellValueFactory(new PropertyValueFactory<>("edgeData"));

    //    table.setItems(data);
    //    table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    //    table.getColumns().addAll(nodeColumn, locationNameColumn, moveColumn, edgeDataColumn);
  }

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }

  public void importCSV() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(App.getPrimaryStage());
    System.out.println(selectedFile.getPath());
    NodeDAOImp.Import(selectedFile.getPath());

    ArrayList<Node> nodeArray = NodeDAOImp.getNodeArray();
    ArrayList<LocationName> locationArray = LocNameDAOImp.getLocNameArray();
    ArrayList<Move> moveArray = MoveDAOImp.getMoveArray();
    ArrayList<Edge> edgeArray = EdgeDAOImp.getEdgeArray();
    ArrayList<TableData> data = new ArrayList<TableData>();

    for (int i = 0; i < nodeArray.size(); i++) {
      data.add(
          new TableData(
              nodeArray.get(i), locationArray.get(i), moveArray.get(i), edgeArray.get(i)));
    }
  }

  public void exportCSV() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Export CSV File to");
    File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
    System.out.println(selectedDirectory.getPath());
    NodeDAOImp.Export(selectedDirectory.getPath());
  }
}
