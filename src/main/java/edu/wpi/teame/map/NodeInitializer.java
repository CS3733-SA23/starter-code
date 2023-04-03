package edu.wpi.teame.map;

public class NodeInitializer {

  public String nodeID;
  public int xCoord;
  public int yCoord;
  public Floor floor;
  public String building;

  public NodeInitializer(String nodeID, int xCoord, int yCoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = Floor.stringToFloor(floor);
    this.building = building;
  }
}
