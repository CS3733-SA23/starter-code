package edu.wpi.teame.utilities;

import edu.wpi.teame.map.HospitalNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MapUtilities {
  private final int MAP_X = 5000;
  private final int MAP_Y = 3400;

  private final Pane pane;

  ObservableList<Node> currentNodes = FXCollections.observableArrayList();

  public MapUtilities(Pane pane) {
    this.pane = pane;
  }

  /**
   * draws a line given the starting and ending (x,y) coordinates and saves the line to a global
   * list of lines
   */
  public Line drawLine(int x1, int y1, int x2, int y2) {
    x1 = (int) convertX(x1);
    y1 = (int) convertY(y1);
    x2 = (int) convertX(x2);
    y2 = (int) convertY(y2);

    Line line = new Line(x1, y1, x2, y2);
    addShape(line);
    return line;
  }

  public Circle drawHospitalNode(HospitalNode hospitalNode) {

    int x = hospitalNode.getXCoord();
    int y = hospitalNode.getYCoord();

    // TODO: change color dependent on NodeType
    return drawCircle(x, y, 4);
  }

  public Circle drawRing(
      int x, int y, int radius, int thickness, Color innerColor, Color outerColor) {
    x = (int) convertX(x);
    y = (int) convertY(y);

    Circle innerCircle = new Circle(x, y, radius - thickness, innerColor);
    Circle outerCircle = new Circle(x, y, radius, outerColor);
    addShape(outerCircle);
    addShape(innerCircle);

    return outerCircle;
  }

  public void drawRing(int x, int y, int radius, int thickness) {
    x = (int) convertX(x);
    y = (int) convertY(y);

    Circle innerCircle = new Circle(x, y, radius - thickness, Color.WHITE);
    Circle outerCircle = new Circle(x, y, radius, Color.BLACK);
    addShape(innerCircle);
    addShape(outerCircle);
  }

  public Circle drawCircle(int x, int y, int radius, Color color) {
    x = (int) convertX(x);
    y = (int) convertY(y);

    Circle circle = new Circle(x, y, radius, color);
    addShape(circle);
    return circle;
  }

  public Circle drawCircle(int x, int y, int radius) {
    x = (int) convertX(x);
    y = (int) convertY(y);

    Circle circle = new Circle(x, y, radius, Color.BLACK);
    addShape(circle);
    return circle;
  }

  public Label createLabel(int x, int y, String text) {
    Label label = new Label(text);
    label.setLayoutX(convertX(x));
    label.setLayoutY(convertY(y));

    addShape(label);

    return label;
  }

  public Label createLabel(int x, int y, int xOffset, int yOffset, String text) {

    Label label = new Label(text);
    label.setLayoutX(convertX(x + xOffset));
    label.setLayoutY(convertY(y + yOffset));

    addShape(label);

    return label;
  }

  public double convertY(int yCoord) {
    return ImageCoordToPane(yCoord, MAP_Y, pane.getHeight());
  }

  public double convertX(int xCoord) {
    return ImageCoordToPane(xCoord, MAP_X, pane.getWidth());
  }

  /**
   * @param coord
   * @param mapWidth
   * @return
   */
  private double ImageCoordToPane(int coord, int mapWidth, double paneWidth) {
    return coord * (paneWidth / mapWidth);
  }

  public double PaneXToImageX(double coord) {
    double paneWidth = this.pane.getWidth();
    return coord * (MAP_X / paneWidth);
  }

  public double PaneYToImageY(double coord) {
    double paneWidth = this.pane.getHeight();
    return coord * (MAP_Y / paneWidth);
  }

  private double convertCoord(int coord, int mapWidth, double paneWidth) {
    return coord * (paneWidth / mapWidth);
  }

  /** @param node */
  private void addShape(Node node) {
    pane.getChildren().add(node);
    currentNodes.add(node);
  }

  public void removeNode(Node node) {
    pane.getChildren().remove(node);
  }

  public void removeAllByType(Class obj) {
    System.out.println(obj);
    System.out.println("remove1 :" + pane.getChildren());
    this.pane.getChildren().removeAll(filterShapes(obj));
    System.out.println("remove2 :" + pane.getChildren());
  }

  public void removeAll() {
    this.pane.getChildren().removeAll(currentNodes);
  }

  public ObservableList<Node> filterShapes(Class obj) {
    ObservableList<Node> result = currentNodes;
    result.removeIf(s -> (s.getClass() != obj));
    return result;
  }

  public ObservableList<Node> getCurrentNodes() {
    return currentNodes;
  }
}
