package edu.wpi.teame.map;

import static java.util.Objects.hash;

import java.util.HashMap;
import lombok.Getter;

public class LocationName {

  public static HashMap<String, LocationName> allLocations = new HashMap<>();

  public enum NodeType {
    HALL,
    CONF,
    DEPT,
    INFO,
    SERV,
    LABS,
    RETL,
    STAI,
    ELEV,
    REST,
    BATH,
    EXIT
  }

  @Getter String longName;
  @Getter String shortName;
  @Getter NodeType nodeType;

  public LocationName(String longName, String shortName, NodeType nodeType) {
    this.longName = longName;
    this.shortName = shortName;
    this.nodeType = nodeType;

    allLocations.put(this.longName, this);
  }

  @Override
  public int hashCode() {
    return hash(longName);
  }
}
