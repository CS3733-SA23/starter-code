package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

import Database.DatabaseController;
import Database.DatabaseGraphController;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.pathfinding.AStarPathfinder;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

// public List<String> getLongNamesFromMove(List<MoveAttribute> mv)

public class MapController {
  @FXML AnchorPane mapPan;
  @FXML private MFXButton backButton;
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
    mouseSetup(backButton);
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    lowerLevelOneTab.setOnSelectionChanged(event ->
            refreshPage(Floor.LOWER_ONE)
            );
    lowerLevelTwoTab.setOnSelectionChanged(event -> refreshPage(Floor.LOWER_TWO));
    floorOneTab.setOnSelectionChanged(event -> refreshPage(Floor.ONE));
    floorTwoTab.setOnSelectionChanged(event -> refreshPage(Floor.TWO));
    floorThreeTab.setOnSelectionChanged(event -> refreshPage(Floor.THREE));

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
    refreshPage(currentFloor);
  }

  @FXML
  public void refreshPage(Floor floor) {
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
    switch (floor){
      case TWO -> mapPan.setId("mapPane11");
      case THREE -> mapPan.setId("mapPane111");
      case LOWER_TWO -> mapPan.setId("mapPane1111");
      case LOWER_ONE -> mapPan.setId("mapPane11111");
      default -> mapPan.setId("mapPane1");
    }
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

  private double convertYCoord(int yCoord) {
    double paneHeight = mapPan.getHeight();
    double mapHeight = 3400;

    return yCoord * (paneHeight / mapHeight);
  }

  private double convertXCoord(int xCoord) {
    double paneWidth = mapPan.getWidth();
    double mapWidth = 5000;

    return xCoord * (paneWidth / mapWidth);
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
    Circle startCircleOutside = new Circle(convertXCoord(x1), convertYCoord(y1), 4);
    startCircleOutside.setFill(BLACK);
    Circle startCircleInside = new Circle(convertXCoord(x1), convertYCoord(y1), 3);
    startCircleInside.setFill(WHITE);
    mapPan.getChildren().add(startCircleOutside);
    mapPan.getChildren().add(startCircleInside);
    currentCircles.add(startCircleInside);
    currentCircles.add(startCircleOutside);

    // draw the lines between each node
    int x2, y2;
    for (int i = 1; i < path.size(); i++) {
      HospitalNode node = path.get(i);
      x2 = node.getXCoord();
      y2 = node.getYCoord();

      drawLine(x1, y1, x2, y2);

      x1 = x2;
      y1 = y2;
    }

    // create circle to symbolize end
    Circle endCircle = new Circle(convertXCoord(x1), convertYCoord(y1), 4);
    endCircle.setFill(BLACK);
    mapPan.getChildren().add(endCircle);
    currentCircles.add(endCircle);
  }

  /**
   * draws a line given the starting and ending (x,y) coordinates and saves the line to a global
   * list of lines
   */
  private void drawLine(int x1, int y1, int x2, int y2) {
    x1 = (int) this.convertXCoord(x1);
    y1 = (int) this.convertYCoord(y1);
    x2 = (int) this.convertXCoord(x2);
    y2 = (int) this.convertYCoord(y2);

    Line line = new Line(x1, y1, x2, y2);
    mapPan.getChildren().add(line);
    currentLines.add(line);
  }

  /** removes all the lines in the currentLines list */
  public void refreshPath() {
    mapPan.getChildren().removeAll(currentCircles);
    mapPan.getChildren().removeAll(currentLines);
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
