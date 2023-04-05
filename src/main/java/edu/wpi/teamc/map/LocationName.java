package edu.wpi.teamc.map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationName {
  private String longName;
  private String shortName;
  private String nodeType;

  public LocationName(String longName, String shortName, String type) {
    this.longName = longName;
    this.shortName = shortName;
    this.nodeType = type;
  }
}
