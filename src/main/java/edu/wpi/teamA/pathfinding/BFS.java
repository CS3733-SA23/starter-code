package edu.wpi.teamA.pathfinding;

import edu.wpi.teamA.database.ORMclasses.Edge;
import java.util.ArrayList;

public class BFS {
  private final Graph graph = new Graph();
  int startID, endID;
  private ArrayList<Integer> path = new ArrayList<Integer>();

  public BFS(int startID, int endID) {
    this.graph.prepGraph();
    this.startID = startID;
    this.endID = endID;
    this.path = setPath();
  }

  /**
   * setPath: BFS Algorithm Implementation
   *
   * @return path of nodes as integer IDs
   */
  public ArrayList<Integer> setPath() {

    ArrayList<Integer> queue = new ArrayList<>();
    ArrayList<Integer> nodesToReset = new ArrayList<>();

    int currentID = startID;
    System.out.println(currentID);

    GraphNode currentGNode = graph.getGraphNode(currentID);

    while (currentID != endID) {
      for (int i = 0; i < currentGNode.edgeCount(); i++) {

        Edge currentEdge = currentGNode.getEdge(i);
        int otherNodeID;

        if (currentEdge.getStartNode()
            == currentID) { // check whether node is starting node or ending node in the edge
          otherNodeID = currentEdge.getEndNode();
        } else {
          otherNodeID = currentEdge.getStartNode();
        }
        GraphNode otherGNode = graph.getGraphNode(otherNodeID);

        //                boolean otherVisited = false;
        //                for (int j = 0; j < visited.size(); j++) {
        //                    if (otherNode
        //                            .getNodeID() == visited.get(j).getNodeID()) { // check if node
        // has been visited
        //                        otherVisited = true;
        //                    }
        //                }

        if (!otherGNode.isVisited()) { // if not visited, add to queue and add to wrapping queue
          otherGNode.setPrev(currentGNode);
          nodesToReset.add(otherNodeID);
          queue.add(otherNodeID);
          otherGNode.setVisited(true);
        }
      }

      currentGNode.setVisited(true);
      currentID = queue.remove(0);
      currentGNode = graph.getGraphNode(currentID);
    }

    ArrayList<Integer> path = getOrder(startID, endID);

    resetNodes(nodesToReset);

    return path;
  }

  /**
   * getOrder: ???
   *
   * @param startID
   * @param endID
   * @return ????
   */
  private ArrayList<Integer> getOrder(int startID, int endID) {
    ArrayList<Integer> path = new ArrayList<>();
    int currentID = endID;
    GraphNode currentGNode = graph.getGraphNode(currentID);
    while (currentID != startID) { // while the path still has a previous path
      path.add(0, currentID);
      currentGNode = currentGNode.getPrev();
      currentID = currentGNode.getNodeID();
    }
    path.add(0, startID);
    return path;
  }

  /**
   * resetNodes: ???
   *
   * @param ??
   * @return ????
   */
  private void resetNodes(ArrayList<Integer> resetList) {
    for (int i : resetList) {
      System.out.println("RESET NODES: i-" + i);
      graph.getGraphNode(i).reset();
    }
  }

  public GraphNode getGraphNode(int key) {
    return graph.getGraphNode(key);
  }

  public String toString() {
    String stringPath = "Start at node " + path.get(0);

    for (int i = 1; i < path.size(); i++) {
      stringPath += ", then go to node " + path.get(i);
    }

    return stringPath + ". You have reached your destination.";
  }
}
