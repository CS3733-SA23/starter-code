package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pathfinding.HospitalEdge;
import pathfinding.HospitalNode;
import pathfinding.NodeInitializer;

public class DatabaseGraphController {
  DatabaseController DBC;
  private static List<HospitalNode> nodeList = new ArrayList<>();
  private static List<HospitalEdge> edgeList = new ArrayList<>();

  public DatabaseGraphController(DatabaseController DBC) {
    this.DBC = DBC;
  }

  public List<HospitalNode> getHospitalNodes() {
    return nodeList;
  }

  public List<HospitalEdge> getHospitalEdges() {
    return edgeList;
  }

  public void retrieveFromTable() {

    nodeList = new ArrayList<>();
    edgeList = new ArrayList<>();

    List<String> eList = new ArrayList<>();
    List<String> nList = new ArrayList<>();

    String queryCountE = "SELECT COUNT(*) FROM teame.l1edges;";
    String queryCountN = "SELECT COUNT(*) FROM teame.l1nodes;";
    String queryCountEID = "SELECT edgeID FROM teame.l1edges;";
    String queryCountNID = "SELECT nodeID FROM teame.l1nodes;";

    try (Statement stmt = DBC.getC().createStatement()) {
      ResultSet rsn = stmt.executeQuery(queryCountN);
      if (rsn.next()) {
        int nodeCount = rsn.getInt(1);
        ResultSet rsNodes = stmt.executeQuery(queryCountNID);
        for (int i = 1; i <= nodeCount; i++) {
          if (rsNodes.next()) {
            nList.add(rsNodes.getString("nodeID"));
          }
        }
      }
      ResultSet rse = stmt.executeQuery(queryCountE);
      if (rse.next()) {
        int edgeCount = rse.getInt(1);
        ResultSet rsEdges = stmt.executeQuery(queryCountEID);
        for (int i = 1; i <= edgeCount; i++) {
          if (rsEdges.next()) {
            String newid = rsEdges.getString("edgeid");
            eList.add(newid);
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    // Retrieve edges
    for (String edgeId : eList) {
      String edgeQuery = "SELECT * FROM teame.l1edges WHERE edgeid = '" + edgeId + "';";
      try {
        Statement stmt = DBC.getC().createStatement();
        ResultSet rs = stmt.executeQuery(edgeQuery);

        if (rs.next()) {
          edgeList.add(extractEdgeFromResultSet(rs));
        }
      } catch (SQLException m) {
        System.out.println(m.getMessage());
      }
    }
    if (edgeList.isEmpty()) {
      System.out.println("No edges retrieved for the given list of IDs.");
    } else {
      System.out.println("Edges retrieved successfully.");
    }

    // Retrieve nodes
    for (String nodeId : nList) {
      String nodeQuery = "SELECT * FROM teame.l1nodes WHERE nodeid = '" + nodeId + "'";
      try (Statement stmt = DBC.getC().createStatement()) {
        ResultSet rs = stmt.executeQuery(nodeQuery);
        if (rs.next()) {
          nodeList.add(extractNodeFromResultSet(rs));
        }
      } catch (SQLException d) {
        System.out.println(d.getMessage());
      }
    }
    if (nodeList.isEmpty()) {
      System.out.println("No nodes retrieved for the given list of IDs");
    } else {
      System.out.println("Nodes retrieved successfully.");
    }
  }

  private HospitalNode extractNodeFromResultSet(ResultSet rs) throws SQLException {
    String nodeID = rs.getString("nodeid");
    int xCoord = rs.getInt("xcoord");
    int yCoord = rs.getInt("ycoord");
    String floor = rs.getString("floor");
    String building = rs.getString("building");

    return new HospitalNode(new NodeInitializer(nodeID, xCoord, yCoord, floor, building));
  }

  private HospitalEdge extractEdgeFromResultSet(ResultSet rs) throws SQLException {
    String startNode = rs.getString("startNode");
    String endNode = rs.getString("endNode");

    return new HospitalEdge(startNode, endNode);
  }

  public int getNodeIDFromName(String longname) throws RuntimeException {
    try {
      Statement stmt = DBC.getC().createStatement();
      String sql = "SELECT \"nodeID\" FROM teame.\"Move\" WHERE \"longName\" = '" + longname + "';";

      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) {
        return rs.getInt("nodeID");
      } else {
        System.out.println("That is not a valid nodeID");
        return 0;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /*
  I get a floor, Need list of MoveAttributes(nodeID, longName, date)
   */
  //public
}
