package edu.wpi.teamc.map;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
 private Map<String, Node> nodes = new HashMap<>();

  /** Empty Constructor for Graph */
  Graph() {}

  /**
   * Constructor for graph
   *
   * @param nodes - HashMap of nodes
   */
  Graph( Map<String, Node> nodes) {
    this.nodes = nodes;
  }

  /**
   * Adds a node to the map
   *
   * @param node - node to be added
   */
  public void addNode(Node node) {
    // check if node already exists
    if (nodes.containsKey(node.getNodeID())) {
      System.out.println("Node already exists");
      return;
    } else {
      nodes.put(node.getNodeID(), node);
    }
  }

  /**
   * Removes a node from the graph
   *
   * @param node -   the node to be removed
   */
  public void removeNode(Node node) {
    // check if node exists
    if (!nodes.containsKey(node.getNodeID())) {
      System.out.println("Node does not exist");
      return;
    } else {
      nodes.remove(node.getNodeID());
    }
    //TODO  remove node from connected nodes or edge list

  }

  /**
   * Returns the node with the given ID
   *
   * @param nodeID - ID of the node to be returned
   * @return Node with the given ID
   */
  public Node getNode(String nodeID) {
    return nodes.get(nodeID);
  }

  /**
   * Returns the HashMap of nodes
   *
   * @return HashMap of nodes
   */
  public Map<String, Node> getNodes() {
    return nodes;
  }

  /**
   * Returns the number of nodes in the graph
   *
   * @return number of nodes in the graph
   */
  public int getNumNodes() {
    return nodes.size();
  }

  /**
   * Returns the number of edges in the graph
   *
   * @return number of edges in the graph
   */
  public int getNumEdges() {
    int numEdges = 0;
    for (Node node : nodes.values()) {
      numEdges += node.getEdges().size();
    }
    return numEdges;
  }

  /**
   * Returns a list of the neighbors of a given node
   *
   * @param node - node to find neighbors of
   * @return list of neighbors
   */
  public List<Node> getNeighbors(Node node) {
      List<Node> neighbors = null;
      int i = 0;
      for (Edge edge : node.getEdges()) {
          neighbors.set(i, edge.getEndNode());
          i++;
      }

      return neighbors;
  }


    /**
     * Returns a list of directions from start to end
     *
     * @param start - start node
     * @param end - end node
     * @return list of directions
     */
  public List<String> getDirections(Node start, Node end) {
      //TODO A* algorithm
      List<String> directions = new LinkedList<>();
      List<Node> closedSet = new LinkedList<>();
      List<Node> openSet = new LinkedList<>();
        openSet.add(start);


      return directions;
  }

}
