package edu.wpi.teamA.database.ORMclasses;

public class LocationName {
  String longName;

  String shortName;

  String nodeType;

  public LocationName(String longName, String shortName, String nodeType) {
    this.longName = longName;
    this.shortName = shortName;
    this.nodeType = nodeType;
  }
}
