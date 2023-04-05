package edu.wpi.teamA.pathfinding;

import java.util.HashMap;

public class Graph {
  private HashMap<Integer, GraphNode> pathfindingArea;

  public Graph() {
    this.pathfindingArea = new HashMap<>();
  }

  public void prepGraph() {

    // List<l1nodes> allNodes = NodeDAO.getALLNodes();

    // traverse through the node entities  (l1nodes)

    /**
     * for (l1nodes node: allNodes) { GraphNode graphNode; graphNode - new GraphNode(node.getNodeID,
     * nodegetxCoord(), node.getYCoord()); }
     */
  }

  public void addNodeToGraph(GraphNode graphNode) {
    pathfindingArea.put(graphNode.getNodeID(), graphNode);
  }

  public GraphNode getGraphNode(int key) {
    return pathfindingArea.get(key);
  }
}
