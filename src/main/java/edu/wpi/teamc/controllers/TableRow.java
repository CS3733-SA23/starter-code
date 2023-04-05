package edu.wpi.teamc.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableRow {
  private String NodeID;
  private String LongName;
  private String Date;
  private int index;
  private String ID;
  private String status;
  private String start;
  private String end;
  private String name;
  private String info, room;

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

  public TableRow(
      String ID, String name, String status, String start, String end, String info, String room) {
    this.ID = ID;
    this.name = name;
    this.status = status;
    this.start = start;
    this.end = end;
    this.info = info;
    this.room = room;
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

  public String getID() {
    return ID;
  }
}
