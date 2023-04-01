package edu.wpi.teamA.database;

public class Move {
  Integer nodeID;

  String longName;

  String date;

  public Move(Integer nodeID, String longName, String date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }
}
