package edu.wpi.teamc.map;

import java.sql.*;
import java.util.*;

public class Graph {
  private Map<String, GraphNode> nodes = new HashMap<>();

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

  public void syncWithDB() {
    Connection connection = null;

    try {
      // Load the PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");

      // Establish the connection
      String url = "jdbc:postgresql://database.cs.wpi.edu/teamcdb";
      String user = "teamc";
      String password = "teamc30";
      connection = DriverManager.getConnection(url, user, password);

      Statement nodeSt = connection.createStatement();
      Statement edgeSt = connection.createStatement();

      // queries
      String queryDisplayNodes = "SELECT * FROM " + "\"hospitalNode\".node";
      String queryDisplayEdges = "SELECT * FROM " + "\"hospitalNode\".edge";

      ResultSet nodes = nodeSt.executeQuery(queryDisplayNodes);
      ResultSet edges = edgeSt.executeQuery(queryDisplayEdges);
      while (nodes.next()) {
        addNode(
            new GraphNode(
                nodes.getString("nodeID"),
                nodes.getInt("xcoord"),
                nodes.getInt("ycoord"),
                nodes.getString("floorNum"),
                nodes.getString("building")));
      }
      while (edges.next()) {
        GraphNode src = this.nodes.get(edges.getString("startNode"));
        GraphNode dest = this.nodes.get(edges.getString("endNode"));
        String origID = src.getNodeID() + "_" + dest.getNodeID();
        String reverseID = dest.getNodeID() + "_" + src.getNodeID();

        src.getGraphEdges().add(new GraphEdge(origID, src, dest));
        dest.getGraphEdges().add(new GraphEdge(reverseID, dest, src));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // Close the connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
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

    System.out.print(": Total dist: " + totalDist);
  }
}
