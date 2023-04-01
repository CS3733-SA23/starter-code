package edu.wpi.teamc.map;

import lombok.Getter;

@Getter
public class Edge implements Comparable<Edge> {
  private String id;
  private Node startNode;
  private Node endNode;
  private double weight;
  private double heuristic;

  public Edge(String id, Node startNode, Node endNode) {
    this.id = id;
    this.startNode = startNode;
    this.endNode = endNode;
    this.weight =
        Math.hypot(
            startNode.getXCoord() - endNode.getXCoord(),
            startNode.getYCoord() - endNode.getYCoord());
  }

  /**
   * Use manhattan distance to set heuristic value
   *
   * @param targetNode Target destination
   */
  public void setHeuristic(Node targetNode) {
    this.heuristic =
        Math.hypot(
            startNode.getXCoord() - targetNode.getXCoord(),
            startNode.getYCoord() - targetNode.getYCoord());
  }

  @Override
  public int compareTo(Edge edge) {
    return Double.compare(weight + heuristic, edge.weight + edge.heuristic);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else {
      String[] id = ((Edge) o).id.split("_");
      String reverseID = id[1] + "_" + id[0];

      return reverseID.equals(this.id);
    }
  }
}
