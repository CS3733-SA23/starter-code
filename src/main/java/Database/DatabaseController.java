package Database;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pathfinding.Floor;
import pathfinding.HospitalEdge;
import pathfinding.HospitalNode;
import pathfinding.MoveAttribute;

public class DatabaseController {
  private Connection c;
  private static List<HospitalNode> nodeList = new ArrayList<>();
  private static List<HospitalEdge> edgeList = new ArrayList<>();
  private static List<MoveAttribute> moveList = new ArrayList<>();

  public static void main(String[] args) throws SQLException, IOException {
    Scanner s1 = new Scanner(System.in);
    System.out.print("Please enter your username (will default to \"teame\"): ");
    String username = s1.nextLine(); // Unused in this Prototype
    System.out.print("Please enter your password (will default to \"teame50\"): ");
    String password = s1.nextLine(); // Unused in this Prototype
    System.out.println();

    DatabaseController DBC1 = new DatabaseController("teame", "teame50");
    MoveAttribute mA = new MoveAttribute("1200", "Hall 3 Level 1", "1/1/2023");

    // DBC1.importFromCSV("C:\\Users\\thesm\\OneDrive\\Desktop\\Test.csv", "l1nodes");

    boolean exit = true;
    while (exit) {
      System.out.println("\nWhat would you like to do?");
      System.out.println(
          "Choices: update, retrieve, delete, display info, export table, import table, HELP, EXIT, add to table move)");
      String function = s1.nextLine().toLowerCase().trim();

      switch (function) {
        case "update":
          DBC1.updateTable();
          break;

        case "delete":
          DBC1.deleteFromTable(mA);
          break;

        case "help":
          DBC1.help();
          break;

        case "exit":
          DBC1.exitDatabaseProgram();
          exit = false;
          break;
        case "add to table move":
          DBC1.addToTable(mA);

        case "retrieve":
          DBC1.retrieveFromTable();
          break;
        case "export table":
          DBC1.exportToCSV("Move", "C:\\filepath...", "Name of CSV file");
          break;

        case "import table":
          System.out.println("What's the filepath?");
          String filepath = s1.nextLine();
          try {
            DBC1.importFromCSV(filepath, "l1nodes");
          } catch (IOException e) {
            System.out.println("Something went wrong");
          }
        default:
          System.out.println("Please enter a valid action");
      }
    }
  }

  public DatabaseController(String username, String password) {
    c = this.connectToDatabase(username, password);
    this.retrieveFromTable();
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
  private void deleteFromTable(MoveAttribute moveAttribute) {
    Statement stmt;
    String nodeId = moveAttribute.nodeID;

    try {
      stmt = c.createStatement();
      String sql = "DELETE FROM \"Move\" WHERE \"nodeID\" = '" + nodeId + "';";
      int rs = stmt.executeUpdate(sql);
      stmt.close();
      if (rs > 0) {
        System.out.println("Row Deleted successfully from " + nodeId);
      }
    } catch (SQLException e) {
      System.out.println();
    }

    this.retrieveFromTable();
  }

  private void addToTable(MoveAttribute moveAttribute) {
    Statement stmt;
    String nodeId = moveAttribute.nodeID;
    String longName = moveAttribute.longName;
    String date = moveAttribute.date;

    try {
      stmt = c.createStatement();
      String insertTable =
          "INSERT INTO \"Move\" VALUES(" + nodeId + ",'" + longName + "' , '" + date + "');";
      int update = stmt.executeUpdate(insertTable);
      System.out.println(update);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * The node and edge data are retrieved using SQL queries that count the number of nodes and edges
   * in the table, select all node and edge IDs from the table, and then retrieve the corresponding
   * node and edge data using those IDs.
   *
   * @return void
   */
  public void retrieveFromTable() {

    nodeList = new ArrayList<>();
    edgeList = new ArrayList<>();

    List<String> eList = new ArrayList<>();
    List<String> nList = new ArrayList<>();

    String queryCountE = "SELECT COUNT(*) FROM teame.l1edges;";
    String queryCountN = "SELECT COUNT(*) FROM teame.l1nodes;";
    String queryEID = "SELECT edgeID FROM teame.l1edges;";
    String queryNID = "SELECT nodeID FROM teame.l1nodes;";

    try (Statement stmt = c.createStatement()) {
      ResultSet rsn = stmt.executeQuery(queryCountN);
      if (rsn.next()) {
        int nodeCount = rsn.getInt(1);
        ResultSet rsNodes = stmt.executeQuery(queryNID);
        for (int i = 1; i <= nodeCount; i++) {
          if (rsNodes.next()) {
            nList.add(rsNodes.getString("nodeID"));
          }
        }
      }
      ResultSet rse = stmt.executeQuery(queryCountE);
      if (rse.next()) {
        int edgeCount = rse.getInt(1);
        ResultSet rsEdges = stmt.executeQuery(queryEID);
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
        Statement stmt = c.createStatement();
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
      try (Statement stmt = c.createStatement()) {
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

  public List<HospitalNode> getHospitalNodes() {
    return nodeList;
  }

  public List<HospitalEdge> getHospitalEdges() {
    return edgeList;
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
      String moveQuery = "SELECT * FROM teame.\"Move\" WHERE \"nodeID\" = '" + nodeID + "';";
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
   * Extracts a HospitalNode object from the given ResultSet.
   *
   * @param rs The ResultSet to extract the node from.
   * @return A HospitalNode object extracted from the given ResultSet.
   * @throws SQLException if an error occurs while accessing the ResultSet.
   */
  private HospitalNode extractNodeFromResultSet(ResultSet rs) throws SQLException {
    String nodeID = rs.getString("nodeid");
    int xCoord = rs.getInt("xcoord");
    int yCoord = rs.getInt("ycoord");
    String floor = rs.getString("floor");
    String building = rs.getString("building");
    String nodeType = rs.getString("nodeType");
    String longName = rs.getString("longName");
    String shortName = rs.getString("shortName");
    return new HospitalNode(nodeID, xCoord, yCoord, Floor.stringToFloor(floor), building);
  }

  /**
   * Description: Extracts a HospitalEdge object from the given ResultSet.
   *
   * @param rs The ResultSet to extract the edge from.
   * @return A HospitalEdge object extracted from the given ResultSet.
   * @throws SQLException if an error occurs while accessing the ResultSet.
   */
  private HospitalEdge extractEdgeFromResultSet(ResultSet rs) throws SQLException {
    String startNode = rs.getString("startNode");
    String endNode = rs.getString("endNode");

    return new HospitalEdge(startNode, endNode);
  }

  /**
   * It prompts the user to input which table they want to update (nodes or edges), then the ID of
   * the node or edge they want to update, and finally which attribute they want to update (xcoord,
   * ycoord, longname, edgeid, startnode, endnode)
   *
   * @return void
   */
  private void updateTable() {

    boolean doneUpdating = true;
    while (doneUpdating) {

      Scanner s1 = new Scanner(System.in);
      System.out.println("Which table would you like to update (Nodes, Edges): ");
      String tabletoUpdate = s1.nextLine().toLowerCase();

      if (tabletoUpdate.equals("nodes")) {
        System.out.println("Please type the Node ID you would like to update: ");
        String nodetoUpdate = s1.nextLine();

        System.out.println("Which attribute would you like to update (xcoord, ycoord, longname)");
        String attributeToUpdate = s1.nextLine().toLowerCase().trim();

        try {
          this.updateAttribute("l1nodes", nodetoUpdate, attributeToUpdate, "nodeid");
        } catch (RuntimeException e) {
          System.out.println("Invalid Input");
        }
      } else if (tabletoUpdate.equals("edges")) {
        System.out.println("Please type the Edge ID you would like to update: ");
        String edgetoUpdate = s1.nextLine();

        System.out.println("Which attribute would you like to update (edgeid, startnode, endnode)");
        String attributeToUpdate = s1.nextLine().toLowerCase().trim();

        try {
          this.updateAttribute("l1edges", edgetoUpdate, attributeToUpdate, "edgeid");
        } catch (RuntimeException e) {
          System.out.println("Invalid Input");
        }
      } else {
        System.out.println("Invalid Table Name");
      }

      System.out.print("\nAre you done updating (y/n)?: ");
      String ans = s1.nextLine();
      if (ans.equals("y")) doneUpdating = false;
    }

    this.retrieveFromTable();
  }

  /**
   * This method updates the value of a specific attribute in a specific row of a given table.
   *
   * @param tabletoEdit The name of the table to edit.
   * @param idToUpdate The value of the ID attribute for the row to update.
   * @param attributeToEdit The name of the attribute to update.
   * @param idType The name of the ID attribute for the table.
   * @return void
   */
  public void updateAttribute(
      String tabletoEdit, String idToUpdate, String attributeToEdit, String idType) {
    Scanner s1 = new Scanner(System.in);
    Statement stmt = null;
    String sql;

    System.out.println("Please enter the new " + attributeToEdit + ": ");
    String newval = s1.nextLine();
    try {
      stmt = c.createStatement();
      sql =
          "UPDATE "
              + tabletoEdit
              + " SET "
              + attributeToEdit
              + " = '"
              + newval
              + "' WHERE "
              + idType
              + " = '"
              + idToUpdate
              + "';";
      int rs = stmt.executeUpdate(sql);
      stmt.close();
      if (rs > 0) {
        System.out.println("Successfully updated " + attributeToEdit + " for node " + idToUpdate);
      } else {
        System.out.println("Your entry is invalid please try again");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void help() {
    System.out.println("");
    System.out.println("");

    System.out.println("Help Page:\n");
    boolean exit = false;
    Scanner s1 = new Scanner(System.in);

    // User Operations:
    // System.out.println("\tUser Operations:\n");
    System.out.println("\tThe User inputs username to database.");
    System.out.println("\tThe User inputs password to database.");
    System.out.println(
        "\tThe User inputs which operation they wish to use: \n\t\t(update, retrieve, delete, display info, "
            + "export table, import table, help, exit).");
    System.out.println(
        "\tThe user then inputs the id of what they want to modify in the database.");
    System.out.println(
        "\tThe User inputs all other necessary information for the specified editing operation.");
    System.out.println(
        "\tThe User then inputs whether or not they want to edit the database further.");
    System.out.println(
        "\tAlternatively, the user could have inputted the list and adress of the file they "
            + "wanted to import or export.");
    System.out.println("\nType \"exit\" to leave the help screen at any time:");

    while (!exit) {
      String response = s1.nextLine().toLowerCase();
      if (response.equals("exit")) {
        exit = true;
      }
    }
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
            "INSERT INTO "
                + tableName
                + " VALUES ('"
                + splitL1[0]
                + "', "
                + Integer.parseInt(splitL1[1])
                + ", "
                + Integer.parseInt(splitL1[2])
                + ", '"
                + splitL1[3]
                + "', "
                + " '"
                + splitL1[4]
                + "', "
                + " '"
                + splitL1[5]
                + "', "
                + " '"
                + splitL1[6]
                + "', "
                + " '"
                + splitL1[7]
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
    ResultSet rs = stmt.executeQuery("SELECT * FROM " + name);

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
