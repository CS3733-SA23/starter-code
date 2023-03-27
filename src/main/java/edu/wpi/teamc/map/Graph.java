package edu.wpi.teamc.map;

import static java.lang.Long.parseLong;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
  private Map<String, Node> nodes = new HashMap<>();

  /** Empty Constructor for Graph */
  public Graph() {}

  /**
   * Constructor for graph
   *
   * @param nodes - HashMap of nodes
   */
  Graph(Map<String, Node> nodes) {
    this.nodes = nodes;
  }

  public void init() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("L1Nodes.csv"));
    String line = "", delim = ",";
    line = reader.readLine();

    // add all nodes to graph
    while (line != null) {
      String[] node = line.split(delim);
      Node temp =
          new Node(
              node[0],
              parseLong(node[1]),
              parseLong(node[2]),
              node[3],
              node[4],
              node[5],
              node[6],
              node[7]);
      addNode(temp);
      line = reader.readLine();
    }

    reader = new BufferedReader(new FileReader("L1Edges.csv"));
    line = reader.readLine();

    while (line != null) {
      String[] edge = line.split(delim);
      Edge temp = new Edge(edge[0], nodes.get(edge[2]));
      Node node = nodes.get(edge[1]);

      node.getEdges().add(temp);

      line = reader.readLine();
    }
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
   * @param node - the node to be removed
   */
  public void removeNode(Node node) {
    // check if node exists
    if (!nodes.containsKey(node.getNodeID())) {
      System.out.println("Node does not exist");
      return;
    } else {
      nodes.remove(node.getNodeID());
    }
    // TODO  remove node from connected nodes or edge list

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
    LinkedList<Node> neighbors = new LinkedList<>();

    for (Edge edge : node.getEdges()) {
      neighbors.add(edge.getEndNode());
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
  public List<Node> getDirections_BFS(Node start, Node end) {
    LinkedList queue = new LinkedList();
    LinkedList<Node> visited = new LinkedList<>();
    queue.add(start);
    visited.add(start);
    while (!queue.isEmpty()) {
      Node current = (Node) queue.remove();
      if (current.equals(end)) {
        return visited;
      }
      for (Node neighbor : getNeighbors(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.add(neighbor);
        }
      }
    }
    return null;
  }
/** Returns a list of directions from start to end
   *
   * @param start - start node
   * @param end - end node
   * @return list of directions
   */
/*public List<Node> getDirections_Astar(Node start, Node end) {
    // implement a* algorithm for pathfinding from start to end
    LinkedList<Node> open = new LinkedList<>();
    LinkedList<Node> closed = new LinkedList<>();
    open.add(start);
    while (!open.isEmpty()) {
      Node current = open.remove();
      if (current.equals(end)) {
        return closed;
      }
      for (Node neighbor : getNeighbors(current)) {
        if (!closed.contains(neighbor)) {
          neighbor.setCost(current.getCost() + 1);
          neighbor.setHeuristic(neighbor.getCost() + neighbor.getDistance(end));
          open.add(neighbor);
        }
      }
      closed.add(current);
    }
    return null;
  }*/

  /** Prints the graph
   */
  public void printGraph() {
    for (Node node : nodes.values()) {
      System.out.println(node.getNodeID());
      for (Edge edge : node.getEdges()) {
        System.out.println(edge.getEndNode().getNodeID());
      }
    }
  }


  /**
   * Prints the directions from start to end
   * @param start - start node
   * @param end - end node
   */
    public void printDirections(Node start, Node end) {
        List<Node> directions = getDirections_BFS(start, end);
        for (Node node : directions) {
        System.out.println(node.getNodeID());
        }
    }

}
