package edu.wpi.teamc.map;

import java.util.HashMap;

public class Map {
  HashMap<String, Node> nodes = new HashMap<>();

  /** Empty Constructor for Map */
  Map() {}

  /**
   * Constructor for Map
   *
   * @param nodes - HashMap of nodes
   */
  Map(HashMap<String, Node> nodes) {
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
   * Removes a node from the map
   *
   * @param nodeID - ID of the node to be removed
   */
  public void removeNode(String nodeID) {
    // check if node exists
    if (!nodes.containsKey(nodeID)) {
      System.out.println("Node does not exist");
      return;
    } else {
      nodes.remove(nodeID);
    }

    // TODO  remove node from connected nodes or edge list
  }

  /**
   * Adds a node to the list of connected nodes
   *
   * @param nodeID - ID of the node to be added
   */
  public void addConnectedNode(String nodeID, String connectedNodeID) {
    // TODO
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
  public HashMap<String, Node> getNodes() {
    return nodes;
  }

  /**
   * Returns the number of nodes in the map
   *
   * @return number of nodes in the map
   */
  public int getNumNodes() {
    return nodes.size();
  }

  /**
   * Returns the number of edges in the map
   *
   * @return number of edges in the map
   */
  public int getNumEdges() {
    return 0; // TODO
  }
}
