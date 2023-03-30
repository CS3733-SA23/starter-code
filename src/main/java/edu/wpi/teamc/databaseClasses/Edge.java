package edu.wpi.teamc.databaseClasses;

import lombok.Getter;

@Getter
public class Edge {
  private String id;
  private String startNode;
  private String endNode;

  public Edge(String id, String startNode, String endNode) {
    this.id = id;
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public String getEdgeID() {
    return this.id;
  }
}
