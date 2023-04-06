package edu.wpi.teamA.database.ORMclasses;

import lombok.Getter;
import lombok.Setter;

public class Node {
  @Getter @Setter private Integer nodeID;

  @Getter @Setter private Integer xcoord;

  @Getter @Setter private Integer ycoord;
  @Getter @Setter private String floor;
  @Getter @Setter private String building;

  public Node(Integer nodeID, Integer xcoord, Integer ycoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }
}
