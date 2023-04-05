package edu.wpi.teamA.database.ORMclasses;

public class Node {
  public Integer nodeID;

  public Integer xcoord;

  public Integer ycoord;

  public String floor;

  public String building;

  public Node(Integer nodeID, Integer xccord, Integer ycoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xcoord = xccord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }

  public Integer getNodeID() {
    return nodeID;
  }

  public Integer getXccord() {
    return xcoord;
  }

  public Integer getYcoord() {
    return ycoord;
  }

  public String getFloor() {
    return floor;
  }

  public String getBuilding() {
    return building;
  }
}
