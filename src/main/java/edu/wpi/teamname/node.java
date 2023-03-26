package edu.wpi.teamname;

public class node {
  String nodeID;
  int xcoord;
  int ycoord;
  String floor;
  String building;
  String nodeType;
  String longName;
  String shortName;

  public node(
      String nodeID,
      int xcoord,
      int ycoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
  }
}
