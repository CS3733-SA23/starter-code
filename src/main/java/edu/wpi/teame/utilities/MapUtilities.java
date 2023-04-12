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
import javafx.scene.shape.Shape;

public class MapUtilities {
  private final int MAP_X = 5000;
  private final int MAP_Y = 3400;

  private Pane pane;

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

    System.out.println("Hospital Node X: " + x);
    System.out.println("Hospital Node Y: " + y);

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

    currentNodes.add(label);

    return label;
  }

  public double convertY(int yCoord) {
    return convertCoord(yCoord, MAP_Y, pane.getWidth());
  }

  public double convertX(int xCoord) {
    return convertCoord(xCoord, MAP_X, pane.getHeight());
  }

  /**
   * @param coord
   * @param mapWidth
   * @return
   */
  private double convertCoord(int coord, int mapWidth, double paneWidth) {
    return coord * (paneWidth / mapWidth);
  }

  /** @param shape */
  private void addShape(Shape shape) {
    pane.getChildren().add(shape);
    currentNodes.add(shape);
  }

  public void removeNode(Node node) {
    pane.getChildren().remove(node);
  }

  public void removeAll(Class obj) {
    pane.getChildren().remove(filterShapes(obj));
  }

  public ObservableList<Node> filterShapes(Class obj) {
    ObservableList<Node> result = currentNodes;
    result.removeIf(s -> (s.getClass() != obj));
    return result;
  }

  public ObservableList<Node> getCurrentNodes() {
    return this.currentNodes;
  }
}
