package Database;

import edu.wpi.teame.entities.ServiceRequestData;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;
import pathfinding.*;

public enum DatabaseController {
  INSTANCE;

  public enum Table {
    LOCATION_NAME,
    MOVE,
    NODE,
    EDGE,
    SERVICE_REQUESTS
  }

  private Connection c;
  public List<MoveAttribute> moveList = new ArrayList<>();

  DatabaseController(String username, String password) {
    c = this.connectToDatabase(username, password);
    // this.retrieveFromTable();
  }

  DatabaseController() {
    c = this.connectToDatabase("teame", "teame50");
  }

  private Connection connectToDatabase(String username, String password) {
    Connection c = null;
    try {
      Class.forName("org.postgresql.Driver");
      c =
          DriverManager.getConnection(
              "jdbc:postgresql://database.cs.wpi.edu:5432/teamedb", username, password);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");
    return c;
  }

  /**
   * Delete attributes from the edge or node table based on user's input
   *
   * @return void
   */
  public void deleteFromTable(Table t, Object obj) {
    String sqlDelete = "";
    switch (t) {
      case MOVE:
        MoveAttribute moveAttribute = (MoveAttribute) obj;
        String nodeId = moveAttribute.getNodeID();
        sqlDelete =
                "DELETE FROM \"Move\" WHERE \"nodeID\" = '" + nodeId + "';";
        break;
      case EDGE:
        HospitalEdge edge = (HospitalEdge) obj;
        String startNode = edge.getNodeOneID();
        String endNode = edge.getNodeTwoID();
        sqlDelete = "DELETE FROM \"Edge\" WHERE \"startNode\" = " + startNode + " AND \"endNode\" = '" + endNode + "';";
        break;
      case NODE:
        HospitalNode node = (HospitalNode) obj;
        String nodeID = node.getNodeID();
        sqlDelete =
                "DELETE FROM \"Node\" WHERE \"nodeID\" = '" + nodeID + "';";
        break;
      case LOCATION_NAME:
        LocationName locationName = (LocationName) obj;
        String lName = locationName.getLongName();
        String shortName = locationName.getShortName();
        sqlDelete =
                "DELETE FROM \"LocationName\" WHERE \"longName\" = " + lName + " AND \"shortName\" = '" + shortName + "';";
        break;
      case SERVICE_REQUESTS:
        ServiceRequestData serviceRequestData = (ServiceRequestData) obj;
        JSONObject requestData = serviceRequestData.getRequestData();
        ServiceRequestData.Status status = serviceRequestData.getRequestStatus();
        String staffAssigned = serviceRequestData.getAssignedStaff();
        ServiceRequestData.RequestType requestType = serviceRequestData.getRequestType();
        sqlDelete =
                "DELETE FROM \"ServiceRequests\" WHERE \"requestdata\" = " + requestData + " AND \"status\" = '" + status + "' AND \"staffassigned\" = '" + staffAssigned + "' AND \"requestType\" = '" + requestType + "';";
        break;
    }

    Statement stmt;
    try {
      stmt = c.createStatement();
      int rs = stmt.executeUpdate(sqlDelete);
      stmt.close();
    } catch (SQLException e) {
      System.out.println();
    }
  }

  public void addToTable(Table table, Object obj) {
    String insertTable = "";
    switch (table) {
      case MOVE:
        MoveAttribute moveAttribute = (MoveAttribute) obj;
        String nodeId = moveAttribute.getNodeID();
        String longName = moveAttribute.getLongName();
        String date = moveAttribute.getDate();
        insertTable =
            "INSERT INTO \"Move\" VALUES(" + nodeId + ",'" + longName + "' , '" + date + "');";
        break;
      case EDGE:
        HospitalEdge edge = (HospitalEdge) obj;
        String startNode = edge.getNodeOneID();
        String endNode = edge.getNodeTwoID();
        insertTable = "INSERT INTO \"Edge\" VALUES(" + startNode + ",'" + endNode + "');";
        ;
        break;
      case NODE:
        HospitalNode node = (HospitalNode) obj;
        String nodeID = node.getNodeID();
        int xcoord = node.getXCoord();
        int ycoord = node.getYCoord();
        Floor floor = node.getFloor();
        String building = node.getBuilding();
        insertTable =
            "INSERT INTO \"Node\" VALUES("
                + nodeID
                + ",'"
                + xcoord
                + "' , '"
                + ycoord
                + "' , '"
                + floor
                + "' , '"
                + building
                + "');";
        break;
      case LOCATION_NAME:
        LocationName locationName = (LocationName) obj;
        String lName = locationName.getLongName();
        String shortName = locationName.getShortName();
        LocationName.NodeType nodeType = locationName.getNodeType();
        insertTable =
            "INSERT INTO \"Move\" VALUES(" + lName + ",'" + shortName + "' , '" + nodeType + "');";
        break;
      case SERVICE_REQUESTS:
        ServiceRequestData serviceRequestData = (ServiceRequestData) obj;
        JSONObject requestData = serviceRequestData.getRequestData();
        ServiceRequestData.Status status = serviceRequestData.getRequestStatus();
        String staffAssigned = serviceRequestData.getAssignedStaff();
        ServiceRequestData.RequestType requestType = serviceRequestData.getRequestType();
        insertTable =
            "INSERT INTO \"ServiceRequests\" VALUES("
                + requestData
                + ",'"
                + status
                + "' , '"
                + staffAssigned
                + "' , '"
                + requestType
                + "');";
        break;
    }

    Statement stmt;
    try {
      stmt = c.createStatement();
      ;
      int update = stmt.executeUpdate(insertTable);
      System.out.println(update);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Description: Fills a list with moveAttribute objects, with each row being an object and having
   * a nodeID, longName, date
   *
   * @return list of move attribute objects
   */
  public List<MoveAttribute> getMoveList() {
    List<String> mList = new ArrayList<>();
    String queryCountM = "SELECT COUNT(*) FROM teame.\"Move\";";
    String queryMID = "SELECT \"nodeID\" FROM teame.\"Move\";";
    try (Statement stmt = c.createStatement()) {
      ResultSet rsm = stmt.executeQuery(queryCountM);
      if (rsm.next()) {
        int moveCount = rsm.getInt(1);
        ResultSet rsMoves = stmt.executeQuery(queryMID);
        for (int i = 0; i <= moveCount; i++) {
          if (rsMoves.next()) {
            mList.add(rsMoves.getString("nodeID"));
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    // Retrieve move
    for (String nodeID : mList) {
      String moveQuery =
          "SELECT * FROM teame.\"Move\" WHERE \"nodeID\" = '"
              + nodeID
              + "'  ORDER BY \"nodeID\" ASC ;";
      try (Statement stmt = c.createStatement()) {
        ResultSet rs = stmt.executeQuery(moveQuery);
        if (rs.next()) {
          moveList.add(extractMoveFromResultSet(rs));
        }
      } catch (SQLException d) {
        throw new RuntimeException();
      }
    }
    if (moveList.isEmpty()) {
      System.out.println("Move table not retrieved");
    } else {
      System.out.println("Move table retrieved successfully");
    }
    return moveList;
  }

  public List<MoveAttribute> getServiceRequests() {
    List<MoveAttribute> moveAttributes = new LinkedList<>();

    try {
      Statement stmt = DatabaseController.INSTANCE.getC().createStatement();

      String sql =
              "SELECT \"nodeID\", \"longName\", \"date\" FROM teame.\"Node\" NATURAL JOIN teame.\"Move\" WHERE floor = '"
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


  public List<MoveAttribute> getEdges() {
    List<MoveAttribute> moveAttributes = new LinkedList<>();

    try {
      Statement stmt = DatabaseController.INSTANCE.getC().createStatement();

      String sql =
              "SELECT \"nodeID\", \"longName\", \"date\" FROM teame.\"Node\" NATURAL JOIN teame.\"Move\" WHERE floor = '"
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

  public List<MoveAttribute> getNodes() {
    List<MoveAttribute> moveAttributes = new LinkedList<>();

    try {
      Statement stmt = DatabaseController.INSTANCE.getC().createStatement();

      String sql =
              "SELECT \"nodeID\", \"longName\", \"date\" FROM teame.\"Node\" NATURAL JOIN teame.\"Move\" WHERE floor = '" + "';";
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

  public List<MoveAttribute> getLocationName() {
    List<MoveAttribute> moveAttributes = new LinkedList<>();

    try {
      Statement stmt = DatabaseController.INSTANCE.getC().createStatement();

      String sql =
              "SELECT \"nodeID\", \"longName\", \"date\" FROM teame.\"Node\" NATURAL JOIN teame.\"Move\" WHERE floor = '" + "';";
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

  /**
   * Extracts a MoveTable object from the given ResultSet
   *
   * @param rs The ResultSet to extract the node, long name, and date from.
   * @return A MoveTable object extracted from the given ResultSet.
   * @throws SQLException if an error occurs while accessing the ResultSet.
   */
  private MoveAttribute extractMoveFromResultSet(ResultSet rs) throws SQLException {
    return new MoveAttribute(
        rs.getString("nodeID"), rs.getString("longName"), rs.getString("date"));
  }

  /**
   * This method imports data from a CSV file to a specified database table.
   *
   * @param filePath The file path of the CSV file to be imported.
   * @param tableName The name of the database table to import data to.
   * @throws FileNotFoundException if the specified file path is not found.
   */
  public void importFromCSV(String filePath, String tableName) throws FileNotFoundException {
    try {
      // Load CSV file
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line;
      List<String> rows = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        rows.add(line);
      }
      rows.remove(0);
      reader.close();

      Statement stmt = c.createStatement();
      for (String l1 : rows) {
        String[] splitL1 = l1.split(",");
        System.out.println(l1);
        String sql =
            "INSERT INTO \""
                + tableName
                + "\" VALUES ("
                + splitL1[0]
                + ", '"
                + splitL1[1]
                + "', '"
                + splitL1[2]
                + "'); ";
        System.out.println(sql);
        stmt.execute(sql);
      }

      System.out.println(
          "Imported " + (rows.size()) + " rows from " + filePath + " to " + tableName);

    } catch (IOException | SQLException e) {
      System.err.println("Error importing from " + filePath + " to " + tableName);
      e.printStackTrace();
    }
  }

  /**
   * This method exports the data from a specified database table to a CSV file.
   *
   * @param name The name of the database table to export data from.
   * @param filePath The file path to save the CSV file.
   * @param fileName The name of the CSV file.
   * @throws SQLException if there is an error accessing the database.
   * @throws IOException if there is an error creating or writing to the CSV file.
   */
  public void exportToCSV(String name, String filePath, String fileName)
      throws SQLException, IOException {

    // Initialization
    Statement stmt = null;
    stmt = c.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM \"" + name + "\";");

    // Makes new file or finds existing one
    File file = new File(filePath + File.separator + fileName);

    // Initializes the FileWriter to edit the right file
    FileWriter fileWriter;
    if (file.exists()) {
      fileWriter = new FileWriter(file, true); // appends to file if it already exists
    } else {
      file.createNewFile();
      fileWriter = new FileWriter(file); // adds to new file
    }

    // Writes the header row
    int numOfCols = rs.getMetaData().getColumnCount();
    for (int i = 1; i <= numOfCols; i++) {
      fileWriter.append(rs.getMetaData().getColumnName(i));
      if (i < numOfCols) {
        fileWriter.append(",");
      } else {
        fileWriter.append("\n");
      }
    }

    // Writes in each row of data
    while (rs.next()) {
      for (int i = 1; i <= numOfCols; i++) {
        fileWriter.append(rs.getString(i));
        if (i < numOfCols) {
          fileWriter.append(",");
        } else {
          fileWriter.append("\n");
        }
      }
    }

    // Closers
    fileWriter.close();
    rs.close();
    stmt.close();
  }

  public Connection getC() {
    return c;
  }

  private void exitDatabaseProgram() {
    try {
      c.close();
      System.out.println("Database Connection Closed");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }
}
