package edu.wpi.teamc.map;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
  private String nodeID;
  private int xCoord;
  private int yCoord;
  private String floor;
  private String building;

  /**
   * Constructor for Node
   *
   * @param nodeID - ID of the node ex: CCONF001L1
   * @param xCoord - x coordinate of the node ex: 2255
   * @param yCoord - y coordinate of the node ex: 849
   * @param floor - floor of the node ex: L1
   * @param building - building of the node ex: CCONF
   */
  public Node(
      String nodeID, int xCoord, int yCoord, String floor, String building, List<Edge> edges) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
  }

  /**
   * Constructor for Node
   *
   * @param nodeID - ID of the node ex: CCONF001L1
   * @param xCoord - x coordinate of the node ex: 2255
   * @param yCoord - y coordinate of the node ex: 849
   * @param floor - floor of the node ex: L1
   * @param building - building of the node ex: CCONF
   */
  public Node(String nodeID, int xCoord, int yCoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
  }
}
