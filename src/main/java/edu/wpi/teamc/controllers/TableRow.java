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
  private String s1, s2, s3, s4, s5, s6, s7;

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

  public TableRow(String s1, String s2, String s3, String s4, String s5, String s6) {
    this.s1 = s1;
    this.s2 = s2;
    this.s3 = s3;
    this.s4 = s4;
    this.s5 = s5;
    this.s6 = s6;
  }

  public TableRow(String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
    this.s1 = s1;
    this.s2 = s2;
    this.s3 = s3;
    this.s4 = s4;
    this.s5 = s5;
    this.s6 = s6;
    this.s7 = s7;
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
