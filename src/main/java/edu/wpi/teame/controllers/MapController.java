package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.Database.DatabaseController;
import edu.wpi.teame.Database.DatabaseGraphController;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.pathfinding.AStarPathfinder;
import edu.wpi.teame.utilities.MapUtilities;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

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
  Floor currentFloor = Floor.ONE;
  String curLocFromComboBox;
  String destFromComboBox;
  MapUtilities mapUtil = new MapUtilities();

  DatabaseController db = DatabaseController.INSTANCE;
  DatabaseGraphController graphController = new DatabaseGraphController(db);
  ObservableList<String> floorLocations =
      FXCollections.observableArrayList(
          graphController.getLongNamesFromMove(
              graphController.getMoveAttributeFromFloor(currentFloor)));

  @FXML
  public void initialize() {
    // mouseSetup(backButton1);
    // backButton1.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    // refreshPage(currentFloor);
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
    refreshTab(currentFloor);
  }

  @FXML
  public void refreshTab(Floor floor) {
    currentFloor = floor;
    floorLocations =
        FXCollections.observableArrayList(
            graphController.getLongNamesFromMove(
                graphController.getMoveAttributeFromFloor(currentFloor)));
    currentLocationList.setItems(floorLocations);
    destinationList.setItems(floorLocations);
    currentLocationList.setValue("");
    destinationList.setValue("");
    pathLabel.setText("");
    refreshPath(whichPane(floor));
  }

  @FXML
  public void displayPath(String from, String to, Floor whichFloor) {
    if (from.equals("") || to.equals("")) {
      return;
    }
    refreshPath(whichPane(whichFloor));
    AStarPathfinder pf = new AStarPathfinder();

    String toNodeID = graphController.getNodeIDFromName(to) + "";
    String fromNodeID = graphController.getNodeIDFromName(from) + "";

    List<HospitalNode> path =
        pf.findPath(HospitalNode.allNodes.get(fromNodeID), HospitalNode.allNodes.get(toNodeID));
    if (path == null) {
      System.out.println("Path does not exist");
      return;
    }
    ArrayList<String> pathNames = new ArrayList<>();
    for (HospitalNode node : path) {
      pathNames.add(graphController.getNameFromNodeID(node.getNodeID()));
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
    mapUtil.drawRing(x1, y1, 4, 3, BLACK, WHITE, curPane);

    // draw the lines between each node
    int x2, y2;
    for (int i = 1; i < path.size(); i++) {
      HospitalNode node = path.get(i);
      x2 = node.getXCoord();
      y2 = node.getYCoord();

      mapUtil.drawLine(x1, y1, x2, y2, curPane);

      x1 = x2;
      y1 = y2;
    }

    // create circle to symbolize end
    mapUtil.drawCircle(x1, y1, 4, BLACK, curPane);
  }

  /** removes all the lines in the currentLines list */
  public void refreshPath(AnchorPane curPane) {
    curPane.getChildren().removeAll(mapUtil.currentShapes);
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
}
