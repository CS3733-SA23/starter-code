package edu.wpi.teamA.database.ORMclasses;

public class Node {
  public Integer nodeID;

  public Integer xccord;

  public Integer ycoord;

  public String floor;

  public String building;

  public Node(Integer nodeID, Integer xccord, Integer ycoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xccord = xccord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }
}
