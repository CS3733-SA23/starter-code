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

  /**
   * Initializes the DatabaseGraphController by passing in valid DatabaseController
   *
   * @param DBC Valid DatabaseController
   */
  public DatabaseGraphController(DatabaseController DBC) {
    try {
      this.DBC = DBC;
    } catch (RuntimeException e) {
      throw new RuntimeException("Couldn't connect to the Database\nMake sure you're on WPI wifi");
    }
  }

  /**
   * Updates list of nodes and edges and returns list of nodes
   *
   * @return list of nodes
   */
  public List<HospitalNode> getHospitalNodes() {
    try {
      retrieveFromTable();
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
    return nodeList;
  }

  /**
   * Updates list of nodes and edges and return list of edges
   *
   * @return list of edges
   */
  public List<HospitalEdge> getHospitalEdges() {
    try {
      retrieveFromTable();
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
    return edgeList;
  }

  /**
   * Updates list of nodes and edges from Database
   *
   * @throws RuntimeException SQL query error will through exception
   */
  public void retrieveFromTable() throws RuntimeException {

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
      throw new RuntimeException("There was a problem getting the information");
    }
    if (edgeList.isEmpty()) {
      System.out.println("No edges retrieved");
    }
    if (nodeList.isEmpty()) {
      System.out.println("No nodes retrieved");
    }
  }

  /**
   * Helper to extract and build a HospitalNode from ResultSet
   *
   * @param rs ResultSet to extract from
   * @return newly created HospitalNode
   * @throws SQLException If pulling from resultset fails, exception thrown
   */
  private HospitalNode extractNodeFromResultSet(ResultSet rs) throws SQLException {
    int nodeID = rs.getInt("nodeID");
    int xCoord = rs.getInt("xcoord");
    int yCoord = rs.getInt("ycoord");
    String floor = rs.getString("floor");
    String building = rs.getString("building");

    return new HospitalNode(new NodeInitializer(nodeID + "", xCoord, yCoord, floor, building));
  }

  /**
   * Helper to extract and build a HospitalEdge from ResultSet
   *
   * @param rs ResultSet to extract from
   * @return newly created HospitalEdge
   * @throws SQLException If pulling from ResultSet fails, exception thrown
   */
  private HospitalEdge extractEdgeFromResultSet(ResultSet rs) throws SQLException {
    String startNode = rs.getString("startNode");
    String endNode = rs.getString("endNode");

    return new HospitalEdge(startNode, endNode);
  }

  /**
   * Obtains the nodeID from given longName corresponding to move table
   *
   * @param longname
   * @return int that represents the nodeID
   * @throws RuntimeException if SQL query fails
   */
  public int getNodeIDFromName(String longname) throws RuntimeException {
    try {
      Statement stmt = DBC.getC().createStatement();
      String sql = "SELECT \"nodeID\" FROM teame.\"Move\" WHERE \"longName\" = '" + longname + "';";

      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) {
        return rs.getInt("nodeID");
      } else {
        System.out.println("There is no node linked to that location");
        return 0;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<MoveAttribute> getMoveAttributeFromFloor(Floor fl) {
    String floor = Floor.floorToString(fl);
    List<MoveAttribute> moveAttributes = new LinkedList<>();

    try {
      Statement stmt = DBC.getC().createStatement();

      String sql =
          "SELECT \"nodeID\", \"longName\", \"date\" FROM teame.\"Node\" NATURAL JOIN teame.\"Move\" WHERE floor = '"
              + floor
              + "';";
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        moveAttributes.add(
            new MoveAttribute(
                rs.getInt("nodeid") + "", rs.getString("longName"), rs.getString("date")));
      }

      return moveAttributes;
    } catch (SQLException e) {
      throw new RuntimeException("Something went wrong");
    }
  }
}
