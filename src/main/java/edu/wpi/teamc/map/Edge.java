package edu.wpi.teamc.map;

import lombok.Getter;

@Getter
public class Edge {
  private String id;
  private Node startNode;
  private Node endNode;
  private int weight;

  public Edge(String id, Node startNode, Node endNode) {
    this.id = id;
    this.startNode = startNode;
    this.endNode = endNode;
    this.weight = 0;
  }
}
