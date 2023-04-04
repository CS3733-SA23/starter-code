package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.App;
import edu.wpi.teamA.database.NodeDAOImp;
import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class MapController implements IPageController {

  // @FXML private TableView mapData;
  @FXML private MFXButton importButton;
  @FXML private MFXButton exportButton;

  @FXML
  public void initialize() {
    importButton.setOnMouseClicked(event -> importCSV());
    exportButton.setOnMouseClicked(event -> exportCSV());
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
    NodeDAOImp.Import(selectedFile.getPath(), selectedFile.getName());
  }

  public void exportCSV() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Export CSV File to");
    File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
    System.out.println(selectedDirectory.getPath());
    NodeDAOImp.Export(selectedDirectory.getPath());
  }
}
