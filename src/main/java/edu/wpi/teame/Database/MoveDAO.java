package edu.wpi.teame.Database;

import edu.wpi.teame.map.MoveAttribute;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MoveDAO<E> extends DAO<MoveAttribute> {
  List<MoveAttribute> moveAttributes;

  public MoveDAO(Connection c) {
    activeConnection = c;
    table = "\"Move\"";
  }

  /**
   * Description: Fills a list with moveAttribute objects, with each row being an object and having
   * a nodeID, longName, date
   *
   * @return list of move attribute objects
   */
  public List<MoveAttribute> get() {
    moveAttributes = new ArrayList<>();
    String query = "SELECT * FROM teame.\"Move\" ORDER BY \"nodeID\" ASC;";

    try (Statement stmt = activeConnection.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
      while (rs.next()) {
        moveAttributes.add(
            new MoveAttribute(
                rs.getString("nodeID"), rs.getString("longName"), rs.getString("date")));
      }
      System.out.println("Move table retrieved successfully");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return moveAttributes;
  }

  public void update(MoveAttribute moveAttribute, String attribute, String value) {
    String nodeID = moveAttribute.getNodeID();
    String sqlUpdate =
        "UPDATE \"Move\" "
            + "SET \""
            + attribute
            + "\" = '"
            + value
            + "' WHERE \"nodeID\" = '"
            + nodeID
            + "';";

    try {
      Statement stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlUpdate);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(
          "Exception: Cannot duplicate two set of the same edges, start and end nodes have to exist (cannot create more ids)");
    }
  }

  public void delete(MoveAttribute moveAttribute) {
    String nodeId = moveAttribute.getNodeID();
    String longName = moveAttribute.getLongName();
    String sqlDelete =
        "DELETE FROM \"Move\" WHERE \"nodeID\" = '"
            + nodeId
            + "' AND \"longName\" = '"
            + longName
            + "';";

    Statement stmt;
    try {
      stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlDelete);
      stmt.close();
    } catch (SQLException e) {
      System.out.println("error deleting");
    }
  }

  public void add(MoveAttribute moveAttribute) {
    int nodeId = Integer.parseInt(moveAttribute.getNodeID());
    String longName = moveAttribute.getLongName();
    String date = moveAttribute.getDate();
    String sqlAdd =
        "INSERT INTO \"Move\" VALUES(" + nodeId + ",'" + longName + "' , '" + date + "');";

    Statement stmt;
    try {
      stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlAdd);
    } catch (SQLException e) {
      System.out.println("error adding");
    }
  }

  public void importFromCSV(String filePath, String tableName) {
    try {
      BufferedReader mreader = new BufferedReader(new FileReader(filePath));
      String line;
      List<String> rows = new ArrayList<>();
      while ((line = mreader.readLine()) != null) {
        rows.add(line);
      }
      rows.remove(0);
      mreader.close();
      Statement stmt = activeConnection.createStatement();

      String sqlDelete = "DELETE FROM \"" + tableName + "\";";
      stmt.execute(sqlDelete);

      for (String l1 : rows) {
        String[] splitL1 = l1.split(",");
        String sql =
            "INSERT INTO "
                + "\""
                + tableName
                + "\""
                + " VALUES ("
                + splitL1[0]
                + ",'"
                + splitL1[1]
                + "','"
                + splitL1[2]
                + "'); ";
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
