package edu.wpi.teame.map;

import static java.util.Objects.hash;

import java.util.HashMap;
import java.util.NoSuchElementException;
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
    EXIT;

    public static NodeType stringToNodeType(String str) {
      switch (str.toUpperCase()) {
        case "HALL":
          return NodeType.HALL;
        case "CONF":
          return NodeType.CONF;
        case "DEPT":
          return NodeType.DEPT;
        case "INFO":
          return NodeType.INFO;
        case "SERV":
          return NodeType.SERV;
        case "LABS":
          return NodeType.LABS;
        case "RETL":
          return NodeType.RETL;
        case "STAI":
          return NodeType.STAI;
        case "ELEV":
          return NodeType.ELEV;
        case "REST":
          return NodeType.REST;
        case "BATH":
          return NodeType.BATH;
        case "EXIT":
          return NodeType.EXIT;
        default:
          throw new NoSuchElementException(
              "No such NodeType found! you tried to do a " + str + "!");
      }
    }

    public static String nodeToString(NodeType nodeType) {
      switch (nodeType) {
        case HALL:
          return "HALL";
        case CONF:
          return "CONF";
        case DEPT:
          return "DEPT";
        case INFO:
          return "INFO";
        case SERV:
          return "SERV";
        case LABS:
          return "LABS";
        case RETL:
          return "RETL";
        case STAI:
          return "STAI";
        case ELEV:
          return "ELEV";
        case REST:
          return "REST";
        case BATH:
          return "BATH";
        case EXIT:
          return "EXIT";
        default:
          throw new NoSuchElementException("No such NodeType found");
      }
    }
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
