package edu.wpi.teamc.databaseClasses;

import java.sql.*;
import java.util.List;

public class Node {
  private String nodeID;
  private int xCoord;
  private int yCoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;
  List<String> connectedNodes;

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
   * @param connectedNodes - list of nodes connected to this node ex: [C001L1, C002L1, C003L1]
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
      List<String> connectedNodes) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.connectedNodes = connectedNodes;
  }
  // getters
  public String getNodeID() {
    return nodeID;
  }

  public int getXCoord() {
    return xCoord;
  }

  public int getYCoord() {
    return yCoord;
  }

  public String getFloor() {
    return floor;
  }

  public String getBuilding() {
    return building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public String getShortName() {
    return shortName;
  }

  public List<String> getConnectedNodes() {
    return connectedNodes;
  }

  // setters
  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public void setXCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public void setYCoord(int yCoord) {
    this.yCoord = yCoord;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  /**
   * Adds a node to the list of connected nodes
   *
   * @param nodeID - ID of the node to be added
   */
  void addConnectedNode(String nodeID) {
    if (connectedNodes.contains(nodeID)) {
      System.out.println("Node already connected");
      return;
    } else {
      connectedNodes.add(nodeID);
    }
  }

  /**
   * Removes a node from the list of connected nodes
   *
   * @param nodeID - ID of the node to be removed
   */
  void removeConnectedNode(String nodeID) {
    if (!connectedNodes.contains(nodeID)) {
      System.out.println("Node not connected");
      return;
    } else {
      connectedNodes.remove(nodeID);
    }
  }

  void updateNodeCoordintes(
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
