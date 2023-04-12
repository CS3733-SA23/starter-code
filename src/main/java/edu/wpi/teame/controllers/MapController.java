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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

// public List<String> getLongNamesFromMove(List<MoveAttribute> mv)

public class MapController {
  @FXML AnchorPane mapPane;
  // @FXML private MFXButton backButton;
  // @FXML MFXButton backButton1;
  @FXML Tab floorOneTab;
  @FXML Tab floorTwoTab;
  @FXML Tab floorThreeTab;
  @FXML Tab lowerLevelTwoTab;
  @FXML Tab lowerLevelOneTab;

  @FXML MFXComboBox<String> currentLocationList;
  @FXML MFXComboBox<String> destinationList;
  @FXML private Label pathLabel;
  @FXML private StackPane imagePane;
  @FXML private ImageView mapImage;
  Floor currentFloor = Floor.ONE;
  String curLocFromComboBox;
  String destFromComboBox;
  MapUtilities mapUtil = new MapUtilities();

  ArrayList<Line> currentLines = new ArrayList<>();
  ArrayList<Circle> currentCircles = new ArrayList<>();

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
          displayPath(curLocFromComboBox, destFromComboBox);
        });

    destinationList.setOnAction(
        event -> {
          curLocFromComboBox = currentLocationList.getValue();
          destFromComboBox = destinationList.getValue();
          displayPath(curLocFromComboBox, destFromComboBox);
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
    System.out.println(floorLocations);
    currentLocationList.setItems(floorLocations);
    destinationList.setItems(floorLocations);
    currentLocationList.setValue("");
    destinationList.setValue("");
    pathLabel.setText("");
    refreshPath();
  }

  @FXML
  public void displayPath(String from, String to) {
    if (from.equals("") || to.equals("")) {
      return;
    }
    refreshPath();
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
    drawPath(path);
  }

  /**
   * draws the path with lines connecting each node on the map
   *
   * @param path
   */
  private void drawPath(List<HospitalNode> path) {

    // create circle to symbolize start
    int x1 = path.get(0).getXCoord();
    int y1 = path.get(0).getYCoord();
    mapUtil.drawRing(x1, y1, 4, 3, BLACK, WHITE, mapPane);

    // draw the lines between each node
    int x2, y2;
    for (int i = 1; i < path.size(); i++) {
      HospitalNode node = path.get(i);
      x2 = node.getXCoord();
      y2 = node.getYCoord();

      mapUtil.drawLine(x1, y1, x2, y2, mapPane);

      x1 = x2;
      y1 = y2;
    }

    // create circle to symbolize end
    mapUtil.drawCircle(x1, y1, 4, BLACK, mapPane);
  }

  /** removes all the lines in the currentLines list */
  public void refreshPath() {
    mapPane.getChildren().removeAll(mapUtil.currentShapes);
  }
}
