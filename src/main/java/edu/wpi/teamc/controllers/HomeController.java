package edu.wpi.teamc.controllers;

import edu.wpi.teamc.Cdb;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.Desktop;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomeController {

  private String filePath;
  private Desktop desktop = Desktop.getDesktop();

  @FXML private Label myLabel;

  @FXML MFXButton signageButton2;

  @FXML private Button flowerDeliveryPage;

  @FXML private Button furnitureDeliveryPage;

  @FXML private Button helpPage;

  @FXML private Button mealDeliveryPage;

  @FXML private Button officeSuppliesPage;

  @FXML private Button roomReservationPage;

  @FXML private Button signagePage;

  @FXML private Button logOut;
  @FXML private Button editMap;

  @FXML private Button mapHistory;

  @FXML private Button mapPage;

  @FXML private Button importButton;

  @FXML private Button exportButton;

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FLOWER);
  }

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.FURNITURE);
  }

  @FXML
  void getHelpPage(ActionEvent event) {
    Navigation.navigate(Screen.HELP);
  }

  @FXML
  void getMealDeliveryPage(ActionEvent event) {
    Navigation.navigate(Screen.MEAL);
  }

  @FXML
  void getOfficeSuppliesPage(ActionEvent event) {
    Navigation.navigate(Screen.OFFICE_SUPPLY);
  }

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(ActionEvent event) {
    Navigation.navigate(Screen.SIGNAGE);
  }

  @FXML
  void getPathfindingPage(ActionEvent event) {
    Navigation.navigate(Screen.PATHFINDING_PAGE);
  }

  @FXML
  void getExit(ActionEvent event) {
    System.exit(0);
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {}

  @FXML
  void getEditMap(ActionEvent event) {}

  @FXML
  void getLogOut(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN);
  }

  @FXML
  void getMapHistory(ActionEvent event) {
    Navigation.navigate(Screen.MAP_HISTORY_PAGE);
  }

  @FXML
  void getMapPage(ActionEvent event) {}

  @FXML
  void getImportButton(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        desktop.open(file);
        filePath = file.getAbsolutePath();
        Cdb.importCSVNode(filePath);
        Cdb.importCSVEdge(filePath);
        Cdb.importCSVLocationName(filePath);
        Cdb.importCSVMove(filePath);

      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  @FXML
  void getExportButton(ActionEvent event) {}
}
