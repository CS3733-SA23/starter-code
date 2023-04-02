package edu.wpi.teamc.map;

import lombok.Getter;

@Getter
public class Edge {
  private String id;
  private Node endNode;

  public Edge(String id, Node endNode) {
    this.id = id;
    this.endNode = endNode;
  }
}
