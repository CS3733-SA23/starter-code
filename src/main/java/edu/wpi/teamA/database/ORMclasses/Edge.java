package edu.wpi.teamA.database.ORMclasses;

public class Edge {
  public Integer startNode;
  public Integer endNode;

  public Edge(Integer startNode, Integer endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public Integer getStartNode() {
    return startNode;
  }

  public Integer getEndNode() {
    return endNode;
  }
}
