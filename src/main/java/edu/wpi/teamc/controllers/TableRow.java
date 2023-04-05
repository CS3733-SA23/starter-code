package edu.wpi.teamc.controllers;

public class TableRow {
  private String NodeID;
  private String LongName;
  private String Date;
  private int index;

  public TableRow(String nodeID, String longName, String date) {
    this.NodeID = nodeID;
    this.LongName = longName;
    this.Date = date;
  }

  public TableRow(String nodeID, String longName, String date, int index) {
    this.NodeID = nodeID;
    this.LongName = longName;
    this.Date = date;
    this.index = index;
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

  public int getIndex() {
    return index;
  }
}
