package edu.wpi.teamc.map;

import lombok.Getter;

@Getter
public class GraphEdge extends Edge implements Comparable<GraphEdge> {
  private String id;
  private double weight;
  private double heuristic;

  public GraphEdge(String id, Node startNode, Node endNode) {
    super(startNode, endNode);
    this.id = id;
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
  public void setHeuristic(GraphNode targetNode) {
    this.heuristic =
        Math.hypot(
            getStartNode().getXCoord() - targetNode.getXCoord(),
            getEndNode().getYCoord() - targetNode.getYCoord());

    /*
    add extra weight to edges who's ending floor does not match target floor
    */
    if (!getEndNode().getFloor().equals(targetNode.getFloor())) {
      this.heuristic += 200;
    }
  }

  /**
   * Override compareTo function to provide desired ordering when GraphEdges are inserted into a
   * priority queue
   *
   * @param edge the object to be compared.
   * @return int
   */
  @Override
  public int compareTo(GraphEdge edge) {
    return Double.compare(weight + heuristic, edge.weight + edge.heuristic);
  }

  /**
   * Override equals to equate edges that travel between the same two nodes
   *
   * @param o GraphEdge object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else {
      String[] id = ((GraphEdge) o).getId().split("_");
      String reverseID = id[1] + "_" + id[0];

      return reverseID.equals(this.getId());
    }
  }
}
