package edu.wpi.teamA;

import edu.wpi.teamA.pathfinding.Graph;
import edu.wpi.teamA.pathfinding.GraphNode;
import edu.wpi.teamA.pathfinding.SearchController;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hey");
    // App.launch(App.class, args);
    final Graph graph = new Graph();
    SearchController cont = new SearchController(graph);

    GraphNode nodeA = new GraphNode(11, 0, 0, "", "", "", "", "");
    GraphNode nodeB = new GraphNode(12, 0, 0, "", "", "", "", "");
    GraphNode nodeC = new GraphNode(13, 0, 0, "", "", "", "", "");
    GraphNode nodeD = new GraphNode(14, 0, 0, "", "", "", "", "");
    GraphNode nodeE = new GraphNode(15, 0, 0, "", "", "", "", "");

    graph.addNodeToGraph(nodeA);
    graph.addNodeToGraph(nodeB);
    graph.addNodeToGraph(nodeC);
    graph.addNodeToGraph(nodeD);
    graph.addNodeToGraph(nodeE);
    Edge edgeAB = new Edge(nodeA.getNodeID(), nodeB.getNodeID());
    Edge edgeBC = new Edge(nodeB.getNodeID(), nodeC.getNodeID());
    Edge edgeCD = new Edge(nodeC.getNodeID(), nodeD.getNodeID());
    Edge edgeAE = new Edge(nodeA.getNodeID(), nodeE.getNodeID());
    Edge edgeAC = new Edge(nodeA.getNodeID(), nodeC.getNodeID());

    nodeA.addEdge(edgeAB);
    nodeB.addEdge(edgeAB);
    nodeB.addEdge(edgeBC);
    nodeC.addEdge(edgeBC);
    nodeA.addEdge(edgeAE);
    nodeE.addEdge(edgeAE);
    nodeC.addEdge(edgeCD);
    nodeD.addEdge(edgeCD);
    nodeA.addEdge(edgeAC);
    nodeC.addEdge(edgeAC);

    ArrayList<Integer> path = cont.pathOfNodesBFS(nodeC.getNodeID(), nodeA.getNodeID());
    System.out.println("Start: ");
    System.out.println(path.size());
    for (int i = 0; i < path.size(); i++) {
      System.out.println(path.get(i));
    }
  }
}
