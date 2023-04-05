package edu.wpi.teamA.database.ORMclasses;

import java.time.LocalDate;

public class Move {
  Integer nodeID;

  String longName;

  LocalDate date;

  public Move(Integer nodeID, String longName, LocalDate date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }
}
