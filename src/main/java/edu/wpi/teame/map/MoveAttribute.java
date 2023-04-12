package edu.wpi.teame.map;

import lombok.Getter;

public class MoveAttribute {
  @Getter String nodeID;
  @Getter String longName;
  @Getter String date;

  public MoveAttribute(String nodeID, String longName, String date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }

  public String getNodeID() {
    return nodeID;
  }

  public String getLongName() {
    return longName;
  }

  public String getDate() {
    return date;
  }
}
