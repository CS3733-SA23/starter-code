package edu.wpi.teamc;

import edu.wpi.teamc.map.Graph;
import edu.wpi.teamc.map.Node;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    // CApp.launch(CApp.class, args);
    Graph graph = new Graph();
    try {
      graph.init();
    } catch (IOException e) {

    }
    System.out.println("hi");

    graph.addNode(new Node("test", 0, 0, "test", "test", "test", "test", "test"));
    Node node = graph.getNode("test");
    node.addEdge("difjifj", new Node("test2", 0, 0, "test", "test", "test", "test", "test"));
    graph.printGraph();
  }
}
