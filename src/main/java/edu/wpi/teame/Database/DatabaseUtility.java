package edu.wpi.teame.Database;

import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.MoveAttribute;
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

  public int getNodeIDFromName(String longname) throws RuntimeException {
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

  public String getNameFromNodeID(int nodeID) throws RuntimeException {
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

  public List<MoveAttribute> getMoveAttributeFromFloor(Floor fl) {
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

  public List<String> getLongNamesFromMove(List<MoveAttribute> mv) {
    List<String> longNames = new ArrayList<>();
    for (MoveAttribute moveAttribute : mv) {
      longNames.add(moveAttribute.getLongName());
    }
    return longNames;
  }

  public String getNodeTypeFromNodeID(int nodeID) {
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
}
