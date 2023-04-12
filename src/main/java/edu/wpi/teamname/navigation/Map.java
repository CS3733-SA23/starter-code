package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.DataManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import net.kurobako.gesturefx.GesturePane;
import lombok.Getter;
import lombok.Setter;

public class Map {

  Color borderColor = new Color(0.1, 0.4, 0.9, 1);
  Color insideColor = new Color(0.05, 0.7, 1, 1);
  float circleR = 10.0f;
  float lineT = 10.0f;
  int lineTout = 2;

  public Map() throws SQLException {
    this.graph = new Graph();
  }

  public Graph graph;
  public ArrayList<Emergency> emergencies;


  private Point2D centerPoint;
  private Point2D centerTL;
  @Getter @Setter private ArrayList<Shape> prevPath = new ArrayList<Shape>();


  private ArrayList<Shape> makeShapePath(ArrayList<Node> nodes) {
    ArrayList<Shape> shapes = new ArrayList<Shape>();

    Circle c;
    Path path;

    for (int j = 0; j < 2; j++) {
      path = new Path();
      if (j == 0) {
        path.setStroke(borderColor);
      } else {
        path.setStroke(insideColor);
      }
      path.setStrokeWidth(lineT - (lineTout * 2 * j));
      path.getElements().add(new MoveTo(nodes.get(0).getX(), nodes.get(0).getY()));

      for (int i = 1; i < nodes.size(); i++) {
        path.getElements().add(new LineTo(nodes.get(i).getX(), nodes.get(i).getY()));
      }
      path.setStrokeLineJoin(StrokeLineJoin.ROUND);
      shapes.add(path);
    }

    for (int i = 0; i < nodes.size(); i++) {

      if (i == 0 || i == nodes.size() - 1) {
        c = new Circle(nodes.get(i).getX(), nodes.get(i).getY(), circleR + lineTout);
        c.setFill(borderColor);
        shapes.add(c);

        c = new Circle(nodes.get(i).getX(), nodes.get(i).getY(), circleR);
        c.setFill(insideColor);
        shapes.add(c);
      }
    }
    prevPath = shapes;
    return shapes;
  }

  /**
   * @param parent
   * @param firstClick
   * @param secondClick
   * @param floor1
   * @param floor2
   */
  public void drawAStarPath(
      Pane parent, Point2D firstClick, Point2D secondClick, String floor1, String floor2) {

    //    String floor = "L1";

    List<Node> allNodes = this.graph.getNodes();

    //    System.out.println(firstClick);
    //    System.out.println(secondClick); // Coordinates in inner, now goes up to 5000

    int startIndex = -1;
    int endIndex = -1;
    double leastDistance;
    double nodeDist;

    Point2D currentClick;
    String currentFloor;
    int checkIndex;

    for (int j = 0; j < 2; j++) {
      if (j == 0) {
        // Start Node
        currentFloor = floor1;
        currentClick = firstClick;
      } else {
        // End Node
        currentFloor = floor2;
        currentClick = secondClick;
      }

      leastDistance = Double.MAX_VALUE;
      checkIndex = -1;

      for (int i = 0; i < allNodes.size(); i++) {
        if (i == startIndex) {
          continue;
        } else {
          Node currentNode = allNodes.get(i);
          if (currentNode.getFloor().equals(currentFloor)) {
            nodeDist = currentClick.distance(currentNode.getX(), currentNode.getY());
            if (nodeDist < leastDistance) {
              leastDistance = nodeDist;
              checkIndex = i;
            }
          }
        }
      }

      if (j == 0) {
        // Start Node
        startIndex = checkIndex;
      } else {
        // End Node
        endIndex = checkIndex;
      }
    }

    Node startNode = allNodes.get(startIndex);
    Node endNode = allNodes.get(endIndex);

    drawAStarPath(parent, startNode, endNode);
  }

  public void drawAStarPath(Pane parent, String floor1, String floor2, int sNode, int eNode) {

    //    String floor = "L1";

    List<Node> allNodes = this.graph.getNodes();

    //    System.out.println(firstClick);
    //    System.out.println(secondClick); // Coordinates in inner, now goes up to 5000

    Node startNode = allNodes.get(/*startIndex*/ (sNode - 100) / 5);
    Node endNode = allNodes.get(/*endIndex*/ (eNode - 100) / 5);

    drawAStarPath(parent, startNode, endNode);
  }

  public void drawAStarPath(Pane parent, Node startNode, Node endNode) {
    ArrayList<Node> nodePath = this.graph.AStar(startNode, endNode);

    ArrayList<Shape> shapes = makeShapePath(nodePath);

    System.out.println(nodePath);

    parent.getChildren().addAll(shapes);
  }

  public ObservableList<String> getAllNodeNames(String floor) throws SQLException {
    ObservableList<String> nodeNames = FXCollections.observableArrayList();
    for (Node n : DataManager.getAllNodes()) {
      if (n.getFloor().equals(floor)) {
        nodeNames.addAll(("" + n.getId()));
      }
    }
    return nodeNames;
  }

  String[] floorArr = {
    "Lower Level 2", "Lower Level 1", "Ground Floor", "First Floor", "Second Floor", "Third Floor"
  };

  public ObservableList<String> getAllFloors(String floor) throws SQLException {
    ObservableList<String> floorNames = FXCollections.observableArrayList();

    for (String f : floorArr) {

      floorNames.addAll(f);
    }

    return floorNames;
  }

  EventHandler<MouseEvent> makeVisible =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          Circle outer = ((Circle) event.getSource());
          System.out.println("V");
          System.out.println(outer.getId());
          outer.setVisible(true);
        }
      };

  EventHandler<MouseEvent> hide =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          System.out.println("H");
          Circle outer = ((Circle) event.getSource());
          System.out.println(outer.getId());
          outer.setVisible(false);
        }
      };

  public ArrayList<javafx.scene.Node> makeAllFloorNodes(String floor) throws SQLException {
    ArrayList<javafx.scene.Node> nodes = new ArrayList<javafx.scene.Node>();
    for (Node n : DataManager.getAllNodes()) {
      if (n.getFloor().equals(floor)) {
        //        StackPane N = new StackPane();

        Circle outer = new Circle(n.getX(), n.getY(), circleR + lineTout);
        outer.setFill(borderColor);
        outer.setId("" + n.getId());
        Circle inner = new Circle(n.getX(), n.getY(), circleR);
        inner.setFill(insideColor);

        //        outer.setVisible(false);
        //        inner.setVisible(false);

        outer.setOnMouseEntered(makeVisible);
        //        text. makeVistimbe

        outer.setOnMouseExited(hide);

        //        N.getChildren().addAll(outer, inner);

        //        nodes.add(N);

        nodes.add(outer);
        nodes.add(inner);
      }
    }
    return nodes;
  }

  /** */
  public void drawEmergencies() {}

  /** */
  public void drawLocationNames() {}

  /** */
  public void clearMap() {}

  private double getMapWitdh() {
    return 0;
  }

  private double getMapHeight() {
    return 0;
  }

  /** @param parent */
  public void centerAndZoom(GesturePane parent) {
    double parentW = getMapWitdh();
    double parentH = getMapHeight();
    parentW = 760;
    parentH = 512;

    Point2D scaleOneDim = new Point2D(760 * 2, 512 * 2); // hard Coded

    double scaleX = parentW / scaleOneDim.getX();
    double scaleY = parentH / scaleOneDim.getY();

    System.out.println(scaleX + ", " + scaleY);

    double scaleFactor = Double.min(scaleX, scaleY);

    centerPoint = new Point2D(2250, 1000); // Hard Coded
    double scale = parent.getCurrentScale();
    Point2D CMin = new Point2D((parentW / 2) * (1 / scale), (parentH / 2) * (1 / scale));
    centerTL = centerPoint.subtract(CMin);

    parent.zoomTo(scaleFactor, Point2D.ZERO);
    parent.centreOn(centerTL); // Actually Moves the Top left corner
  }
}
