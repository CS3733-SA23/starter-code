package edu.wpi.teamA.database.ORMclasses;

import lombok.Getter;
import lombok.Setter;

public class LocationName {
  @Getter @Setter private String longName;
  @Getter @Setter private String shortName;

  @Getter @Setter private String nodeType;

  public LocationName(String longName, String shortName, String nodeType) {
    this.longName = longName;
    this.shortName = shortName;
    this.nodeType = nodeType;
  }
}
