package edu.wpi.teame.Database;

import edu.wpi.teame.map.HospitalEdge;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EdgeDAO<E> extends DAO<HospitalEdge> {
  List<HospitalEdge> hospitalEdgeList;

  public EdgeDAO(Connection c) {
    activeConnection = c;
    table = "\"Edge\"";
  }

  @Override
  List<HospitalEdge> get() {
    hospitalEdgeList = new LinkedList<>();

    try {
      Statement stmt = DatabaseController.INSTANCE.getC().createStatement();

      String sql = "SELECT \"startNode\", \"endNode\" FROM teame.\"Edge\" ;";
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        hospitalEdgeList.add(new HospitalEdge(rs.getString("startNode"), rs.getString("endNode")));
      }
      return hospitalEdgeList;
    } catch (SQLException e) {
      throw new RuntimeException("Something went wrong");
    }
  }

  @Override
  void update(HospitalEdge obj, String attribute, String value) {
    String startNode = obj.getNodeOneID();
    String endNode = obj.getNodeTwoID();
    String sqlUpdate =
        "UPDATE \"Edge\" "
            + "SET \""
            + attribute
            + "\" = "
            + value
            + " WHERE \"endNode\" = "
            + endNode
            + " AND \"startNode\" = "
            + startNode
            + ";";

    try {
      Statement stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlUpdate);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(
          "Exception: Cannot duplicate two set of the same edges, start and end nodes have to exist (cannot create more ids)");
    }
  }

  @Override
  void delete(HospitalEdge edge) {
    String startNode = edge.getNodeOneID();
    String endNode = edge.getNodeTwoID();
    String sqlDelete =
        "DELETE FROM \"Edge\" WHERE \"startNode\" = "
            + startNode
            + " AND \"endNode\" = '"
            + endNode
            + "';";

    try {
      Statement stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlDelete);
      stmt.close();
    } catch (SQLException e) {
      System.out.println("error deleting");
    }
  }

  @Override
  void add(HospitalEdge edge) {
    String startNode = edge.getNodeOneID();
    String endNode = edge.getNodeTwoID();
    String sqlAdd = "INSERT INTO \"Edge\" VALUES('" + startNode + "','" + endNode + "');";
    try {
      Statement stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlAdd);
      stmt.close();
    } catch (SQLException e) {
      System.out.println("error adding");
    }
  }

  @Override
  void importFromCSV(String filePath, String tableName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line;
      List<String> rows = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        rows.add(line);
      }
      rows.remove(0);
      reader.close();
      Statement stmt = activeConnection.createStatement();

      String sqlDelete = "DELETE FROM \"" + tableName + "\";";
      stmt.execute(sqlDelete);

      for (String l1 : rows) {
        String[] splitL1 = l1.split(",");
        String sql =
            "INSERT INTO \""
                + tableName
                + "\""
                + "VALUES ("
                + splitL1[0]
                + ","
                + splitL1[1]
                + "); ";
        stmt.execute(sql);
      }

      System.out.println(
          "Imported " + (rows.size()) + " rows from " + filePath + " to " + tableName);

    } catch (IOException | SQLException e) {
      System.err.println("Error importing from " + filePath + " to " + tableName);
      e.printStackTrace();
    }
  }
}
