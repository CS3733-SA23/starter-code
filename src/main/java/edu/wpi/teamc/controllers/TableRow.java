package edu.wpi.teamc.controllers;

public class TableRow {
  private String NodeID;
  private String LongName;
  private String Date;

  public TableRow() {
    this.NodeID = "";
    this.LongName = "";
    this.Date = "";
  }

  public TableRow(String nodeID, String longName, String date) {
    this.NodeID = nodeID;
    this.LongName = longName;
    this.Date = date;
  }

  public String getNodeID() {
    return NodeID;
  }

  public void setNodeID(String nodeID) {
    this.NodeID = nodeID;
  }

  public String getLongName() {
    return LongName;
  }

  public void setLongName(String longName) {
    this.LongName = longName;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String date) {
    this.Date = date;
  }
}
