package edu.wpi.teamc.map;

import lombok.Getter;

@Getter
public class Edge {
  private Node startNode;
  private Node endNode;

  public Edge(String id, Node startNode, Node endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }
}
