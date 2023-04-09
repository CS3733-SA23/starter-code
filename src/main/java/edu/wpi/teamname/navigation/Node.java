package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.*;
import edu.wpi.teamname.database.DataManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Node implements Comparable<Node> {
  @Getter @Setter int id;
  @Getter @Setter private String floor;
  @Getter @Setter private String building;
  @Getter @Setter private int x;
  @Getter @Setter private int y;
  @Getter @Setter private Node parent = null;
  @Getter @Setter private List<Node> neighbors;
  //  @Getter @Setter private List<Edge> edges;
  @Getter @Setter private List<Edge> edges;
  // f: sum of g and h;
  @Getter @Setter private double f = Double.MAX_VALUE;
  // g: Distance from start and node n
  @Getter @Setter private double g = Double.MAX_VALUE;
  // heuristic: WILL NEED A FUNCTION TO FIND THIS
  @Getter @Setter private double h;

  public Node(int ID, int x, int y, String Floor, String Building) {
    this.x = x;
    this.y = y;
    this.floor = Floor;
    this.building = Building;
    this.h = 0;
    this.id = ID;
    this.neighbors = new ArrayList<>();
    //    this.edges = new ArrayList<>();
  }

  @Override
  public int compareTo(Node n) {
    return Double.compare(this.f, n.f);
  }

  /**
   * * Gets all the nodes in the database and puts them into an array list
   *
   * @return An array list of all the nodes in the database
   * @throws SQLException
   */
  //  public static ArrayList<Node> getAllNodes() throws SQLException {
  //    DatabaseConnection dbc = new DatabaseConnection();
  //    Connection connection = dbc.DbConnection();
  //    ArrayList<Node> list = new ArrayList<Node>();
  //
  //    try (connection) {
  //      String query = "SELECT * FROM \"Node\"";
  //      Statement statement = connection.createStatement();
  //      ResultSet rs = statement.executeQuery(query);
  //
  //      while (rs.next()) {
  //        int id = rs.getInt("nodeID");
  //        int xcoord = rs.getInt("xcoord");
  //        int ycoord = rs.getInt("ycoord");
  //        String floor = rs.getString("floor");
  //        String building = rs.getString("building");
  //        list.add(new Node(id, xcoord, ycoord, floor, building));
  //      }
  //    }
  //    return list;
  //  }

  //  public void addEdge(Edge edge, Node s, Node e) {
  //    if (!this.edges.contains(edge)) {
  //      edges.add(edge);
  //
  //      if (this.id == edge.startNodeID) {
  //        this.neighbors.add(e);
  //      } else {
  //        this.neighbors.add(s);
  //      }
  //    }
  //  }

  public double findWeight(Node b) {
    int x1 = this.x;
    int x2 = b.getX();
    int y1 = this.y;
    int y2 = b.getY();

    double x = Math.pow((x2 - x1), 2);
    double y = Math.pow((y2 - y1), 2);
    return Math.sqrt(x + y);
  }

  /** @return */
  public String toString() {
    String nei = "";
    for (Node n : neighbors) {
      nei += " " + Integer.toString(n.getId());
    }
    return "NodeID:"
        + id
        + " Xcord:"
        + x
        + " Ycord:"
        + y
        + " Heu: "
        + h
        + "Neighbors:"
        + nei
        + "Floor"
        + floor
        + "Building:"
        + building;
  }

  public double calculateHeuristic(Node target) {
    // Heuristic will return distance from target
    return Math.sqrt(
        (target.getX() - this.x) * (target.getX() - this.x)
            + (target.getY() - this.y) * (target.getY() - this.y));
  }

  /**
   * Checks if this.nodeID has switched with given nodeID to swap positions, floor and building in
   * the table with it in the edge table also swaps correlating information as well as switching the
   * longNames with eachother in LocationName table
   *
   * @param move
   * @return Boolean
   */
  public boolean moveNode(Move move) throws SQLException {
    Connection connection = DataManager.DbConnection();
    boolean done = false;
    int swapNodeID = move.getNodeID();
    String swapLongN = move.getLongName();
    int thisNode = this.id;

    String getLn = "Select \"longName\" from \"Move\" where \"nodeID\" = " + thisNode;
    try (PreparedStatement s = connection.prepareStatement(getLn)) {
      ResultSet rowsUpdated = s.executeQuery();
      String longN = rowsUpdated.getString("longName");
    } catch (SQLException e2) {
      System.out.println("Error getting long name.");
    }

    /** Might use sync functions with this feature */
    // A starting node, B is node being swapped with

    // rowAlocN = select longName from LocationName where longN = longN
    // rowBlocN = select longName from LocationName where longN = swapLongN
    // rowAnode = select floor, building from Node where thisNode = thisNode
    // rowBnode = select floor, building from Node where thisNode = swapNodeID

    // put rowAlocN where longN = swapLongN
    // put rowAnode where thisNode = swapNodeID
    // insert nodeID, longN, date into Move

    // put rowBnode where thisNode = thisNode
    // put rowBlocN where longN = longN

    // insert swapNodeID, swapLongN, date into Move

    String query = "";

    try (PreparedStatement pstmtUpdate = connection.prepareStatement(query)) {
      int rowsUpdated = pstmtUpdate.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("successfully updated");
      } else {
        System.out.println("not updated");
      }
    } catch (SQLException e) {
      System.out.println("Error updating LocationName record for node ID " + thisNode);
    }

    return done;
  }
}