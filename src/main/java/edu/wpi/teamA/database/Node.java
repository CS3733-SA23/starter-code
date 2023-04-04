package edu.wpi.teamA.database;

public class Node {
  Integer nodeID;

  Integer xccord;

  Integer ycoord;

  String floor;

  String building;

  public Node(Integer nodeID, Integer xccord, Integer ycoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xccord = xccord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }
}
