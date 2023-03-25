package edu.wpi.teamname.navigation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
    for (Node node : nodes.values()) {
      node.removeConnectedNode(nodeID);
    }
  }

  /**
   * Adds a node to the list of connected nodes
   *
   * @param nodeID - ID of the node to be added
   */
  public void addConnectedNode(String nodeID, String connectedNodeID) {
    // check if node exists
    if (!nodes.containsKey(nodeID)) {
      System.out.println("Node does not exist");
      return;
    } else {
      if (nodes.get(nodeID).getConnectedNodes().contains(connectedNodeID)) {
        System.out.println("Node already connected");
        return;
      } else {
        nodes.get(nodeID).addConnectedNode(connectedNodeID);
      }
    }
  }

  /**
   * Removes a node from the list of connected nodes
   *
   * @param nodeID - ID of the node to be removed
   */
  public void removeConnectedNode(String nodeID, String connectedNodeID) {
    // check if node exists
    if (!nodes.containsKey(nodeID)) {
      System.out.println("Node does not exist");
      return;
    } else {
      if (!nodes.get(nodeID).getConnectedNodes().contains(connectedNodeID)) {
        System.out.println("Node not connected");
        return;
      } else {
        nodes.get(nodeID).removeConnectedNode(connectedNodeID);
      }
    }
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
    int numEdges = 0;
    for (Node node : nodes.values()) {
      numEdges += node.getConnectedNodes().size();
    }
    return numEdges;
  }

  /**
   * Find the shortest path between two nodes using A*
   *
   * @param startNodeID - ID of the starting node
   * @param endNodeID - ID of the ending node
   * @return List of node IDs in the shortest path
   */
  public List<String> findShortestPath(String startNodeID, String endNodeID) {
    List<String> returnList = new LinkedList<>();
    // check if nodes exist
    if (!nodes.containsKey(startNodeID) || !nodes.containsKey(endNodeID)) {
      System.out.println("Node does not exist");
      return null;
    }
    // check if nodes are the same
    if (startNodeID.equals(endNodeID)) {
      System.out.println("Start and end nodes are the same");
      return null;
    }
    // check if nodes are connected
    if (!nodes.get(startNodeID).getConnectedNodes().contains(endNodeID)) {
      System.out.println("Nodes are not connected");
      return null;
    }
    // add start node to return list
    returnList.add(startNodeID);

    // .. to do ...

    return returnList;
  }
}
