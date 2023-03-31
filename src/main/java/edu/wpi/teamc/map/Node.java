package edu.wpi.teamc.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
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
  private String nodeType;
  private String longName;
  private String shortName;
  private List<Edge> edges;

  /**
   * Constructor for Node
   *
   * @param nodeID - ID of the node ex: CCONF001L1
   * @param xCoord - x coordinate of the node ex: 2255
   * @param yCoord - y coordinate of the node ex: 849
   * @param floor - floor of the node ex: L1
   * @param building - building of the node ex: CCONF
   * @param nodeType - type of the node ex: HALL
   * @param longName - long name of the node ex: Outpatient Fluoroscopy Floor L1
   * @param shortName - short name of the node ex: Lab C001L1
   * @param edges - list of edges connected to the node
   */
  public Node(
      String nodeID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      List<Edge> edges) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.edges = edges;
  }

  /**
   * Constructor for Node
   *
   * @param nodeID - ID of the node ex: CCONF001L1
   * @param xCoord - x coordinate of the node ex: 2255
   * @param yCoord - y coordinate of the node ex: 849
   * @param floor - floor of the node ex: L1
   * @param building - building of the node ex: CCONF
   * @param nodeType - type of the node ex: HALL
   * @param longName - long name of the node ex: Outpatient Fluoroscopy Floor L1
   * @param shortName - short name of the node ex: Lab C001L1
   */
  public Node(
      String nodeID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.edges = new LinkedList<>();
  }

  public void addEdge(String edgeID, Node startNode, Node endNode) {
    Edge temp = new Edge(edgeID, startNode, endNode);
    edges.add(temp);
  }

  public void updateNodeCoordinates(
      Connection connection, String nodeID, int xCoordinate, int yCoordinate) {
    try {
      PreparedStatement stmtUpdateCoord =
          connection.prepareStatement(
              "UPDATE \"hospitalNode\".node\n"
                  + "SET xcoord = ?, ycoord = ?\n"
                  + "WHERE \"nodeID\" = ?;");
      // set parameters for prepared statement
      stmtUpdateCoord.setInt(1, xCoordinate);
      stmtUpdateCoord.setInt(2, yCoordinate);
      stmtUpdateCoord.setString(3, nodeID);

      int rowsUpdated = stmtUpdateCoord.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("update successful!");
      } else {
        System.out.println("Node not found: Invalid nodeID");
      }
    } catch (SQLException e) {
      System.out.println("SQL exception occurred: " + e.getMessage());
    }
  }
}
