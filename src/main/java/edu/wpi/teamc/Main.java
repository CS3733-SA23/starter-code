package edu.wpi.teamc;

import edu.wpi.teamc.map.Graph;

public class Main {

  public static void main(String[] args) {
    Graph graph = new Graph();
    graph.syncWithDB();

    String startNode = "150";
    String endNode = "100";

    graph.printDirectionsAStar(startNode, endNode);
    CApp.launch(CApp.class, args);
  }
}
