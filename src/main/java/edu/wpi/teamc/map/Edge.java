package edu.wpi.teamc.map;

import lombok.Getter;

@Getter
public class Edge {
  private String id;
  private Node endNode;
  private int weight;

  public Edge(String id, Node endNode, int weight) {

    this.id = id;
    this.endNode = endNode;
    this.weight = weight;
  }
}
