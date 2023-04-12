package edu.wpi.teame.Database;

import edu.wpi.teame.map.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseUtility {

  Connection activeConnection;

  public DatabaseUtility(Connection c) {
    this.activeConnection = c;
  }

  int getNodeIDFromName(String longname) throws RuntimeException {
    try {
      Statement stmt = activeConnection.createStatement();
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

  public String getShortNameFromNodeID(String nodeID) throws SQLException {
    String sqlMove = "SELECT \"longName\" FROM \"Move\" WHERE \"nodeID\" = '" + nodeID + "';";
    Statement stmt = activeConnection.createStatement();
    ResultSet rs = stmt.executeQuery(sqlMove);
    String longName;
    if (rs.next()) {
      longName = rs.getString("longName");
    } else {
      throw new RuntimeException("Could not find long name for node ID: " + nodeID);
    }

    String sqlLocationName =
        "SELECT \"shortName\" FROM \"LocationName\" WHERE \"longName\" = '" + longName + "';";
    rs = stmt.executeQuery(sqlLocationName);
    String shortName;
    if (rs.next()) {
      shortName = rs.getString("shortName");
    } else {
      throw new RuntimeException("Could not find short name for long name: " + longName);
    }
    return shortName;
  }

  public void updateMoveWithoutObject(
      String nodeID, String oldLocationName, String columnName, String value) {
    String updateSQL =
        "UPDATE \"Move\" "
            + "SET \""
            + columnName
            + "\" = '"
            + value
            + "' WHERE \"nodeID\" = '"
            + nodeID
            + "' AND \"longName\" = '"
            + oldLocationName
            + "';";
    try {
      Statement stmt = activeConnection.createStatement();
      stmt.executeUpdate(updateSQL);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(
          "Exception: Cannot duplicate two set of the same edges, start and nodes have to exist (cannot create more ids)");
    }
  }

  String getNameFromNodeID(int nodeID) throws RuntimeException {
    try {
      Statement stmt = activeConnection.createStatement();
      String sql = "SELECT \"longName\" FROM teame.\"Move\" WHERE \"nodeID\" = " + nodeID + ";";

      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) {
        return rs.getString("longName");
      } else {
        System.out.println("There is no location linked to that node");
        return "";
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  List<MoveAttribute> getMoveAttributeFromFloor(Floor fl) {
    String floor = Floor.floorToString(fl);
    List<MoveAttribute> moveAttributes = new LinkedList<>();

    try {
      Statement stmt = activeConnection.createStatement();

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

  List<String> getLongNamesFromMove(List<MoveAttribute> mv) {
    List<String> longNames = new ArrayList<>();
    for (MoveAttribute moveAttribute : mv) {
      longNames.add(moveAttribute.getLongName());
    }
    return longNames;
  }

  List<String> getLongNamesFromLocationName(List<LocationName> ln) {
    List<String> longNames = new ArrayList<>();
    for (LocationName locationName : ln) {
      longNames.add(locationName.getLongName());
    }
    return longNames;
  }

  String getNodeTypeFromNodeID(int nodeID) {
    String sql = "";

    try {
      Statement stmt = activeConnection.createStatement();

      sql =
          "SELECT L.\"nodeType\" FROM \"Node\" N, \"Move\" M, \"LocationName\" L "
              + "WHERE N.\"nodeID\" = M.\"nodeID\" AND M.\"longName\" = L.\"longName\""
              + "AND N.\"nodeID\" = "
              + nodeID
              + ";";
      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) return rs.getString("nodetype");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return "";
  }

  List<HospitalNode> getNodesFromFloor(Floor fl) {
    List<HospitalNode> nodeList = new LinkedList<>();

    try {
      Statement stmt = activeConnection.createStatement();
      String sql = "SELECT * FROM \"Node\" WHERE \"floor\" = '" + Floor.floorToString(fl) + "';";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        nodeList.add(
            new HospitalNode(
                new NodeInitializer(
                    rs.getInt("nodeID") + "",
                    rs.getInt("xcoord"),
                    rs.getInt("ycoord"),
                    rs.getString("floor"),
                    rs.getString("building"))));
      }
      if (nodeList.isEmpty()) System.out.println("There was a problem returning the nodes");
    } catch (SQLException e) {
      throw new RuntimeException("There was a problem retrieving the nodes");
    }
    return nodeList;
  }
}
