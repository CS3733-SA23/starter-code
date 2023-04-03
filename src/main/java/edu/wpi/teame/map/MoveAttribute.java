package edu.wpi.teame.map;

public class MoveAttribute {
  public String nodeID;
  public String longName;
  public String date;

  public MoveAttribute(String nodeID, String longName, String date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }

  public String getNodeID() {
    return nodeID;
  }

  public String getLongName(){
    return longName;
  }

  public String getDate(){
    return date;
  }
}
