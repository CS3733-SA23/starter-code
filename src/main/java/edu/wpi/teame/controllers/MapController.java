package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.pathfinding.AStarPathfinder;
import edu.wpi.teame.utilities.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MapController {
  @FXML AnchorPane mapPane;
  @FXML AnchorPane mapPane1;
  @FXML AnchorPane mapPane11;
  @FXML AnchorPane mapPane111;
  @FXML AnchorPane mapPane1111;

  @FXML Tab floorOneTab;
  @FXML Tab floorTwoTab;
  @FXML Tab floorThreeTab;
  @FXML Tab lowerLevelTwoTab;
  @FXML Tab lowerLevelOneTab;
  @FXML MFXComboBox<String> currentLocationList;
  @FXML MFXComboBox<String> destinationList;
  @FXML private Label pathLabel;
  @FXML MFXButton menuButton;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarSignage;
  @FXML MFXButton menuBarMaps;
  @FXML MFXButton menuBarDatabase;
  @FXML MFXButton menuBarBlank;
  @FXML MFXButton menuBarExit;
  @FXML VBox menuBar;

  Floor currentFloor = Floor.LOWER_TWO;
  String curLocFromComboBox;
  String destFromComboBox;
  MapUtilities mapUtil = new MapUtilities(whichPane(currentFloor));

  ObservableList<String> floorLocations =
      FXCollections.observableArrayList(
          SQLRepo.INSTANCE.getLongNamesFromMove(
              SQLRepo.INSTANCE.getMoveAttributeFromFloor(currentFloor)));

  @FXML
  public void initialize() {
    lowerLevelOneTab.setOnSelectionChanged(event -> refreshTab(Floor.LOWER_ONE));
    lowerLevelTwoTab.setOnSelectionChanged(event -> refreshTab(Floor.LOWER_TWO));
    floorOneTab.setOnSelectionChanged(event -> refreshTab(Floor.ONE));
    floorTwoTab.setOnSelectionChanged(event -> refreshTab(Floor.TWO));
    floorThreeTab.setOnSelectionChanged(event -> refreshTab(Floor.THREE));

    currentLocationList.setOnAction(
        event -> {
          curLocFromComboBox = currentLocationList.getValue();
          destFromComboBox = destinationList.getValue();
          displayPath(curLocFromComboBox, destFromComboBox, currentFloor);
        });

    destinationList.setOnAction(
        event -> {
          curLocFromComboBox = currentLocationList.getValue();
          destFromComboBox = destinationList.getValue();
          displayPath(curLocFromComboBox, destFromComboBox, currentFloor);
        });

    // Initially set the menu bar to invisible
    menuBar.setVisible(false);

    // When the menu button is clicked, invert the value of menuVisibility and set the menu bar to
    // that value
    // (so each time the menu button is clicked it changes the visibility of menu bar back and
    // forth)
    menuButton.setOnMouseClicked(
        event -> {
          menuBar.setVisible(!menuBar.isVisible());
        });

    // Navigation controls for the button in the menu bar
    menuBarHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    menuBarServices.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.SERVICE_REQUESTS);
          menuBar.setVisible(!menuBar.isVisible());
        });
    menuBarSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    menuBarMaps.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    menuBarDatabase.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_DATA_EDITOR));
    menuBarExit.setOnMouseClicked((event -> Platform.exit()));

    // makes the buttons get highlighted when the mouse hovers over them
    mouseSetup(menuBarHome);
    mouseSetup(menuBarServices);
    mouseSetup(menuBarSignage);
    mouseSetup(menuBarMaps);
    mouseSetup(menuBarDatabase);
    mouseSetup(menuBarExit);

    refreshTab(currentFloor);
  }

  @FXML
  public void refreshTab(Floor floor) {
    currentFloor = floor;
    floorLocations =
        FXCollections.observableArrayList(
            SQLRepo.INSTANCE.getLongNamesFromMove(
                SQLRepo.INSTANCE.getMoveAttributeFromFloor(currentFloor)));
    currentLocationList.setItems(floorLocations);
    destinationList.setItems(floorLocations);
    currentLocationList.setValue("");
    destinationList.setValue("");
    pathLabel.setText("");
    refreshPath(whichPane(floor));
    mapUtil = new MapUtilities(whichPane(currentFloor));
  }

  @FXML
  public void displayPath(String from, String to, Floor whichFloor) {
    if (from.equals("") || to.equals("")) {
      return;
    }
    refreshPath(whichPane(whichFloor));
    AStarPathfinder pf = new AStarPathfinder();

    String toNodeID = SQLRepo.INSTANCE.getNodeIDFromName(to) + "";
    String fromNodeID = SQLRepo.INSTANCE.getNodeIDFromName(from) + "";

    List<HospitalNode> path =
        pf.findPath(HospitalNode.allNodes.get(fromNodeID), HospitalNode.allNodes.get(toNodeID));
    if (path == null) {
      System.out.println("Path does not exist");
      return;
    }
    ArrayList<String> pathNames = new ArrayList<>();
    for (HospitalNode node : path) {
      pathNames.add(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));
    }
    pathLabel.setText(pathNames.toString());
    drawPath(path, whichPane(whichFloor));
  }

  /**
   * draws the path with lines connecting each node on the map
   *
   * @param path
   */
  private void drawPath(List<HospitalNode> path, AnchorPane curPane) {

    // create circle to symbolize start
    int x1 = path.get(0).getXCoord();
    int y1 = path.get(0).getYCoord();
    mapUtil.drawRing(x1, y1, 8, 2, WHITE, BLACK);
    mapUtil.createLabel(x1, y1, 5, 5, "Current Location");

    // draw the lines between each node
    int x2, y2;
    for (int i = 1; i < path.size(); i++) {
      HospitalNode node = path.get(i);
      x2 = node.getXCoord();
      y2 = node.getYCoord();

      mapUtil.drawLine(x1, y1, x2, y2);

      x1 = x2;
      y1 = y2;
    }

    // create circle to symbolize end
    mapUtil.drawCircle(x1, y1, 8, BLACK);
    mapUtil.createLabel(x1, y1, 5, 5, "Destination");
  }

  /** removes all the lines in the currentLines list */
  public void refreshPath(AnchorPane curPane) {
    curPane.getChildren().removeAll(mapUtil.getCurrentNodes());
  }

  public AnchorPane whichPane(Floor curFloor) {
    switch (curFloor) {
      case ONE:
        return mapPane;
      case TWO:
        return mapPane1;
      case THREE:
        return mapPane11;
      case LOWER_ONE:
        return mapPane111;
      case LOWER_TWO:
        return mapPane1111;
    }
    return mapPane;
  }

  private void mouseSetup(MFXButton btn) {
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center; -fx-border-color: #192d5a; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#192d5aff", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center;");
          btn.setTextFill(WHITE);
        });
  }
}
