package edu.wpi.teamA.database.ORMclasses;

import lombok.Getter;
import lombok.Setter;

public class Edge {
  @Getter @Setter private Integer startNode;
  @Getter @Setter private Integer endNode;

  public Edge(Integer startNode, Integer endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }
}
