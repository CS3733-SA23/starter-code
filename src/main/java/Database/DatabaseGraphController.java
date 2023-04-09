package Database;

import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalEdge;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.MoveAttribute;
import edu.wpi.teame.map.NodeInitializer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseGraphController {
  DatabaseController DBC;
  private static List<HospitalNode> nodeList = new LinkedList<>();
  private static List<HospitalEdge> edgeList = new LinkedList<>();

  public DatabaseGraphController(DatabaseController DBC) {
    try {
      this.DBC = DBC;
    } catch (RuntimeException e) {
      throw new RuntimeException("Couldn't connect to the Database\nMake sure you're on WPI wifi");
    }
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

    String queryCountEID = "SELECT * FROM teame.\"Edge\";";
    String queryCountNID = "SELECT * FROM teame.\"Node\";";

    try {
      Statement stmt = DBC.getC().createStatement();
      ResultSet rsn = stmt.executeQuery(queryCountNID);
      while (rsn.next()) {
        HospitalNode hn = extractNodeFromResultSet(rsn);
        nodeList.add(hn);
      }

      ResultSet rse = stmt.executeQuery(queryCountEID);
      while (rse.next()) {
        HospitalEdge he = extractEdgeFromResultSet(rse);
        edgeList.add(he);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    if (edgeList.isEmpty()) {
      System.out.println("No edges retrieved");
    }
    if (nodeList.isEmpty()) {
      System.out.println("No nodes retrieved");
    } else {
      System.out.println("Edges and Nodes retrieved successfully.");
    }
  }

  private HospitalNode extractNodeFromResultSet(ResultSet rs) throws SQLException {
    int nodeID = rs.getInt("nodeID");
    int xCoord = rs.getInt("xcoord");
    int yCoord = rs.getInt("ycoord");
    String floor = rs.getString("floor");
    String building = rs.getString("building");

    return new HospitalNode(new NodeInitializer(nodeID + "", xCoord, yCoord, floor, building));
  }

  private HospitalEdge extractEdgeFromResultSet(ResultSet rs) throws SQLException {
    String startNode = rs.getString("startNode");
    String endNode = rs.getString("endNode");

    return new HospitalEdge(startNode, endNode);
  }


}
