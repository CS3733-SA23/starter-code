package edu.wpi.teamc.map;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
  private Map<String, GraphNode> nodes = new HashMap<>();
  private final String NODE_FILE = "src/main/resources/edu/wpi/teamc/csvs/L1Nodes.csv";
  private final String EDGE_FILE = "src/main/resources/edu/wpi/teamc/csvs/L1Edges.csv";

  /** Empty Constructor for Graph */
  public Graph() {}

  /**
   * Constructor for graph
   *
   * @param nodes - HashMap of nodes
   */
  Graph(Map<String, GraphNode> nodes) {
    this.nodes = nodes;
  }

  /**
   * Initializes the graph with L1 Floor Nodes & Edges
   *
   * @throws IOException
   */
  public void init() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(NODE_FILE));
    String line = "", delim = ",";

    // ignore first line
    reader.readLine();
    line = reader.readLine();

    // add all nodes to graph
    while (line != null) {
      String[] node = line.split(delim);
      GraphNode temp =
          new GraphNode(node[0], parseInt(node[1]), parseInt(node[2]), node[3], node[4]);
      addNode(temp);
      line = reader.readLine();
    }

    reader = new BufferedReader(new FileReader(EDGE_FILE));

    // ignore first line
    reader.readLine();
    line = reader.readLine();

    // add all edges
    while (line != null) {
      String[] edge = line.split(delim);

      GraphEdge orig = new GraphEdge(edge[0], nodes.get(edge[1]), nodes.get(edge[2]));

      // reverse the edgeID for an edge going in the other direction
      String[] nodeID = edge[0].split("_");
      String reverse = nodeID[1] + "_" + nodeID[0];
      GraphEdge otherDirection = new GraphEdge(reverse, nodes.get(edge[2]), nodes.get(edge[1]));

      GraphNode start = nodes.get(edge[1]);
      GraphNode end = nodes.get(edge[2]);

      // add the edges to each Node's edge list
      start.getGraphEdges().add(orig);
      end.getGraphEdges().add(otherDirection);

      line = reader.readLine();
    }
  }
  /**
   * Adds a node to the map
   *
   * @param node - node to be added
   */
  public void addNode(GraphNode node) {
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
  public void removeNode(GraphNode node) {
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
  public Map<String, GraphNode> getNodes() {
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
        return visited; // if we found the end node, return the list of visited nodes
      }
      for (Node neighbor : getNeighbors(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.add(neighbor); // add the neighbor to the queue to be visited
        }
      }
    }
    return visited; // if we didn't find the end node, return the list of visited nodes
  }
  /**
   * Returns a list of directions from start to end
   *
   * @param start - start node
   * @param end - end node
   * @return list of directions
   */
  public List<GraphEdge> getDirections_Astar(GraphNode start, GraphNode end) {
    // implement a* algorithm for pathfinding from start to end
    PriorityQueue<GraphEdge> open = new PriorityQueue<>();
    LinkedList<GraphEdge> closed = new LinkedList<>();

    // set heuristic vals for all immediate edges
    for (GraphEdge edge : start.getGraphEdges()) {
      edge.setHeuristic(end);
    }

    // add all immediate edges
    open.addAll(start.getGraphEdges());

    while (!open.isEmpty()) {
      // pick the best edge
      GraphEdge current = open.peek();

      // check if the current edge would reach the dest
      if (end.equals(current.getEndNode())) {
        closed.add(current);
        return closed;
      }

      // check edges of endNode of current edge
      for (GraphEdge neighbor : nodes.get(current.getEndNode().getNodeID()).getGraphEdges()) {
        // if they haven't been added yet
        if (!closed.contains(neighbor) && !open.contains(neighbor)) {
          neighbor.setHeuristic(end);
          open.add(neighbor);
        }
      }
      closed.add(current);
      open.remove(current);
    }
    return null;
  }

  /** Prints the graph */
  public void printGraph() {
    for (Node node : nodes.values()) {
      System.out.print(node.getNodeID() + ", Edges: ");
      for (Edge edge : node.getEdges()) {
        System.out.print(edge.getEndNode().getNodeID() + " ");
      }
      System.out.println();
    }
  }

  /**
   * Prints the directions from start to end
   *
   * @param start - start node
   * @param end - end node
   */
  public void printDirections(String start, String end) {
    Node startNode = nodes.get(start);
    Node endNode = nodes.get(end);
    Node saveTemp = null;
    List<Node> directions = getDirections_BFS(startNode, endNode);
    double totalDist = 0;

    System.out.println();
    for (Node node : directions) {
      if (saveTemp != null) {
        totalDist +=
            Math.hypot(
                saveTemp.getXCoord() - node.getXCoord(), saveTemp.getYCoord() - node.getYCoord());
      }

      System.out.print(" --> " + node.getNodeID());
      saveTemp = node;
    }

    System.out.print(": Total dist: " + totalDist);
  }

  public void printDirectionsAStar(String start, String end) {
    GraphNode startNode = nodes.get(start);
    GraphNode endNode = nodes.get(end);
    List<GraphEdge> directions = getDirections_Astar(startNode, endNode);
    double totalDist = 0;

    if (directions == null) {
      System.out.println("No path exists!");
      return;
    }

    System.out.print("\n" + directions.get(0).getStartNode().getNodeID());
    for (GraphEdge edge : directions) {
      System.out.print(" --> " + edge.getEndNode().getNodeID());
      totalDist += edge.getWeight();
    }

    // purely for comparison sake
    System.out.print(": Total dist: " + totalDist);
    System.out.println(
        "\nTotal dist estimated by straight line: "
            + (Math.abs(startNode.getXCoord() - endNode.getXCoord())
                + Math.abs(startNode.getYCoord() - endNode.getYCoord())));
  }
}
