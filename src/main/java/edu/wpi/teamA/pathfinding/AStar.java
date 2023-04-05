package edu.wpi.teamA.pathfinding;

import edu.wpi.teamA.database.ORMclasses.Edge;
import java.util.ArrayList;

public class AStar {
  private final Graph graph = new Graph();
  private final int startID;
  private final int endID;

  private ArrayList<Integer> path = new ArrayList<Integer>();

  public AStar(int startID, int endID) {
    graph.prepGraph();
    this.startID = startID;
    this.endID = endID;
    this.path = setPath();
  }

  //    public class Wrapping {
  //        Node currentNode;
  //        Wrapping previousPath;
  //        boolean noPrevPath;
  //
  //        public Wrapping(Node currentNode, Wrapping previousPath) {
  //            this.currentNode = currentNode;
  //            this.previousPath = previousPath;
  //            noPrevPath = false;
  //        }
  //
  //        public Wrapping(Node currentNode) {
  //            this.currentNode = currentNode;
  //            noPrevPath = true;
  //        }
  //
  //        public void addPath(Wrapping path) {
  //            this.previousPath = path;
  //            noPrevPath = false;
  //        }
  //    }

  /**
   * pathOfNodesAStar: A* Algorithm Implementation
   *
   * @param startID
   * @param endID
   * @return path of nodes
   */
  public ArrayList<Integer> setPath() {
    ArrayList<Integer> queue = new ArrayList<>();
    ArrayList<Integer> nodesToReset = new ArrayList<>();

    GraphNode endNode = graph.getGraphNode(endID);
    int endX = endNode.getXcoord();
    int endY = endNode.getYcoord();
    GraphNode currentNode = graph.getGraphNode(startID);
    int currentX = currentNode.getXcoord();
    int currentY = currentNode.getYcoord();

    // currentNode info
    currentNode.setgCost(0);
    currentNode.sethCost((int) (Math.hypot(endX - currentX, endX - currentX)));

    while (currentNode.getNodeID() != endID) {
      for (int i = 0; i < currentNode.edgeCount(); i++) {
        Edge currentEdge = currentNode.getEdge(i);
        int otherNodeID;
        GraphNode otherGNode;
        if (currentEdge.getStartNode()
            == currentNode
                .getNodeID()) { // check whether node is starting node or ending node in the
          // edge
          otherNodeID = currentEdge.getEndNode();
        } else {
          otherNodeID = currentEdge.getStartNode();
        }
        otherGNode = graph.getGraphNode(otherNodeID);
        if (!otherGNode.isVisited()) {
          int gCost =
              currentNode.getgCost()
                  + (int)
                      Math.hypot(
                          currentX - otherGNode.getXcoord(), currentY - otherGNode.getYcoord());
          int hCost =
              (int) Math.hypot(endX - otherGNode.getXcoord(), endY - otherGNode.getYcoord());
          if (otherGNode.getPrev().getNodeID() == otherNodeID) {
            otherGNode.setgCost(gCost);
            otherGNode.sethCost(hCost);
            otherGNode.setPrev(currentNode);
            insertIntoPQ(queue, otherGNode); // Not implemented yet
            nodesToReset.add(currentNode.getNodeID());
          } else if (otherGNode.getfCost() > (gCost + hCost)) {
            removeFromPQ(queue, otherNodeID);
            otherGNode.setgCost(gCost);
            otherGNode.sethCost(hCost);
            otherGNode.setPrev(currentNode);
            insertIntoPQ(queue, otherGNode); // Not implemented yet
          }
        }
      }

      currentNode.setVisited(true);

      currentNode = graph.getGraphNode(queue.remove(0));
      currentX = currentNode.getXcoord();
      currentY = currentNode.getYcoord();
    }

    ArrayList<Integer> path = getOrder(startID, endID);

    resetNodes(nodesToReset);

    return path;
  }

  private void insertIntoPQ(ArrayList<Integer> pQ, GraphNode node) {
    boolean isAdded = false;
    for (int i = 0; i < pQ.size(); i++) {
      if (graph.getGraphNode(pQ.get(i)).getfCost() > node.getfCost()) {
        pQ.add(i, node.getNodeID());
        isAdded = true;
        break;
      }
    }
    if (!isAdded) {
      pQ.add(node.getNodeID());
    }
  }

  private void removeFromPQ(ArrayList<Integer> pQ, int nodeID) {

    for (int i = 0; i < pQ.size(); i++) {
      if (pQ.get(i) == nodeID) {
        pQ.remove(i);
        break;
      }
    }
  }

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
      stringPath = ", then go to node " + path.get(i);
    }

    return stringPath + ". You have reached your destination.";
  }
}
