package edu.wpi.teamc.map;

public class Edge {
  private String id;
  private String startNode;
  private String endNode;

  public Edge(
      String id,
      String startNode,
      String endNode,
      String type,
      String length,
      String direction,
      String floor,
      String building,
      String teamAssigned) {
    this.id = id;
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public String getId() {
    return id;
  }

  public String getStartNode() {
    return startNode;
  }

  public String getEndNode() {
    return endNode;
  }
}

