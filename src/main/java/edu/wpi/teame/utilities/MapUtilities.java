package edu.wpi.teame.utilities;

import edu.wpi.teame.map.HospitalNode;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class MapUtilities {
  private final int MAP_X = 5000;
  private final int MAP_Y = 3400;

  public ArrayList<Shape> currentShapes = new ArrayList<>();

  /**
   * draws a line given the starting and ending (x,y) coordinates and saves the line to a global
   * list of lines
   */
  public Line drawLine(int x1, int y1, int x2, int y2, Pane pane) {
    x1 = (int) convertX(x1, pane);
    y1 = (int) convertY(y1, pane);
    x2 = (int) convertX(x2, pane);
    y2 = (int) convertY(y2, pane);

    Line line = new Line(x1, y1, x2, y2);
    addShape(line, pane);
    return line;
  }

  public Circle drawHospitalNode(HospitalNode hospitalNode, Pane pane) {

    int x = hospitalNode.getXCoord();
    int y = hospitalNode.getYCoord();

    // TODO: change color dependent on NodeType
    return drawCircle(x, y, 4, pane);
  }

  public void drawRing(
      int x, int y, int radius, int thickness, Color innerColor, Color outerColor, Pane pane) {
    x = (int) convertX(x, pane);
    y = (int) convertY(y, pane);

    Circle innerCircle = new Circle(x, y, radius - thickness, innerColor);
    Circle outerCircle = new Circle(x, y, radius, outerColor);
    addShape(innerCircle, pane);
    addShape(outerCircle, pane);
  }

  public void drawRing(int x, int y, int radius, int thickness, Pane pane) {
    x = (int) convertX(x, pane);
    y = (int) convertY(y, pane);

    Circle innerCircle = new Circle(x, y, radius - thickness, Color.WHITE);
    Circle outerCircle = new Circle(x, y, radius, Color.BLACK);
    addShape(innerCircle, pane);
    addShape(outerCircle, pane);
  }

  public Circle drawCircle(int x, int y, int radius, Color color, Pane pane) {
    x = (int) convertX(x, pane);
    y = (int) convertY(y, pane);

    Circle circle = new Circle(x, y, radius, color);
    addShape(circle, pane);
    return circle;
  }

  public Circle drawCircle(int x, int y, int radius, Pane pane) {
    x = (int) convertX(x, pane);
    y = (int) convertY(y, pane);

    Circle circle = new Circle(x, y, radius, Color.BLACK);
    addShape(circle, pane);
    return circle;
  }

  public double convertY(int yCoord, Pane pane) {
    return convertCoord(yCoord, MAP_Y, pane.getHeight());
  }

  public double convertX(int xCoord, Pane pane) {
    return convertCoord(xCoord, MAP_X, pane.getWidth());
  }

  /**
   * @param coord
   * @param mapWidth
   * @return
   */
  private double convertCoord(int coord, int mapWidth, double paneWidth) {
    return coord * (paneWidth / mapWidth);
  }

  /**
   * @param shape
   * @param pane
   */
  private void addShape(Shape shape, Pane pane) {
    pane.getChildren().add(shape);
    currentShapes.add(shape);
  }

  //    public ArrayList<Shape> filterShapes(Class s) {
  //        ArrayList<Shape> result;
  //        for(Shape shape:currentShapes){
  //            if(shape.getClass()){
  //                result.add(shape);
  //            }
  //        }
  //    }
}
