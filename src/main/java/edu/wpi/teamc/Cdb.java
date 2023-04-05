package edu.wpi.teamc;

import edu.wpi.teamc.map.*;
import edu.wpi.teamc.map.Edge;
import edu.wpi.teamc.map.Node;
import edu.wpi.teamc.serviceRequest.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cdb implements IServiceRequest {
  static Connection connection = null;

  public static void main(String[] args) {
    try {
      // Load the PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");

      // Establish the connection
      String url = "jdbc:postgresql://database.cs.wpi.edu/teamcdb";
      String user = "teamc";
      String password = "teamc30";
      connection = DriverManager.getConnection(url, user, password);

      /*Meal meal = new Meal("A", "None");
      MealRequest mr = new MealRequest(meal, "1", "none", PENDING);
      Requester rq = new Requester(90, "Bob");
      Cdb.addMeal(mr, rq);*/

      Scanner scanner = new Scanner(System.in);
      // database tables turned into two arrayLists
      List<Node> databaseNodeList = new ArrayList<Node>();
      List<Edge> databaseEdgeList = new ArrayList<Edge>();
      List<LocationName> databaseLocationNameList = new ArrayList<LocationName>();
      List<Move> databaseMoveList = new ArrayList<Move>();
      // load database into lists
      loadDatabaseTables(
          databaseNodeList, databaseEdgeList, databaseLocationNameList, databaseMoveList);
      // variables
      String nodeID;
      String startNode;
      String endNode;
      int xCoordinate;
      int yCoordinate;
      String locationNameLong;
      String locationNameShort;
      String csvFileName;
      boolean continueProg = true;
      // switch case for menu options
      while (continueProg) {
        displayInstructions();
        String command = scanner.nextLine().toLowerCase();
        switch (command) {
          case "display node and edge information":
            displayNodeAndEdgeInfo(databaseNodeList, databaseEdgeList);
            break;
          case "update node coordinates":
            System.out.println("please type the nodeID of the node you would like to update:");
            nodeID = scanner.nextLine();
            System.out.println("Enter new x coordinate:");
            xCoordinate = scanner.nextInt();
            System.out.println("Enter new y coordinate:");
            yCoordinate = scanner.nextInt();
            // update the coordinates with given nodeID and values
            updateCoordinates(connection, databaseNodeList, xCoordinate, yCoordinate, nodeID);
            break;
          case "update name of location node":
            System.out.println("please type the nodeID of the node you would like to update:");
            nodeID = scanner.nextLine();
            System.out.println("Enter new location name in full:");
            locationNameLong = scanner.nextLine();
            System.out.println("Enter the shortened version of the new location name:");
            locationNameShort = scanner.nextLine();
            // update the location name of the given nodeID
            updateLocationName(
                databaseLocationNameList,
                databaseMoveList,
                locationNameLong,
                locationNameShort,
                nodeID);
            break;
          case "get specific node":
            System.out.println("please enter the node ID of the node you would like to get");
            nodeID = scanner.nextLine();
            getNode(databaseNodeList, nodeID);
            break;
          case "export node table into a csv file":
            csvFileName = "src/main/resources/edu/wpi/teamc/Exportedcsvs/Node.csv";
            exportNodesToCSV(csvFileName, databaseNodeList);
            break;
          case "export edge table into a csv file":
            csvFileName = "src/main/resources/edu/wpi/teamc/Exportedcsvs/Edge.csv";
            exportEdgesToCSV(csvFileName, databaseEdgeList);
            break;
          case "export location name table into a csv file":
            csvFileName = "src/main/resources/edu/wpi/teamc/Exportedcsvs/LocationName.csv";
            exportLocationNamesToCSV(csvFileName, databaseLocationNameList);
            break;
          case "export move table into a csv file":
            csvFileName = "src/main/resources/edu/wpi/teamc/Exportedcsvs/Move.csv";
            exportMovesToCSV(csvFileName, databaseMoveList);
            break;
          case "import from a csv file into the node table":
            csvFileName = "src/main/resources/edu/wpi/teamc/csvFiles/Node.csv";
            importCSVNode(csvFileName, databaseNodeList);
            break;
          case "import from a csv file into the edge table":
            csvFileName = "src/main/resources/edu/wpi/teamc/csvFiles/Edge.csv";
            importCSVEdge(csvFileName, databaseEdgeList, databaseNodeList);
            break;
          case "import from a csv file into the location name table":
            csvFileName = "src/main/resources/edu/wpi/teamc/csvFiles/LocationName.csv";
            importCSVLocationName(csvFileName, databaseLocationNameList);
            break;
          case "import from a csv file into the move table":
            csvFileName = "src/main/resources/edu/wpi/teamc/csvFiles/Move.csv";
            importCSVMove(csvFileName, databaseMoveList);
            break;
          case "delete a node":
            System.out.println("please enter the node ID of the node you would like to delete");
            nodeID = scanner.nextLine();
            deleteNode(connection, databaseNodeList, nodeID);
            break;
          case "delete an edge":
            System.out.println(
                "please enter the starting NodeID of the edge you would like to delete");
            startNode = scanner.nextLine();
            System.out.println(
                "please enter the ending NodeID of the edge you would like to delete");
            endNode = scanner.nextLine();
            deleteEdge(connection, databaseEdgeList, startNode, endNode);
            break;
          case "display move information":
            displayMoveInfo(databaseMoveList);
            break;
          case "help":
            System.out.println("");
            break;
          case "exit":
            continueProg = false;
            break;
          default:
            System.out.println("Command not found");
            break;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // Close the connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  // meal request adding + updating
  static void addMeal(MealRequest mealReq, Requester requester) {
    try {
      String MEALREQUEST = "\"ServiceRequests\".\"mealRequest\"";
      // query
      String queryInsertMealReq = "INSERT INTO " + MEALREQUEST + " VALUES (?,?,?,?,?,?);";
      PreparedStatement preparedStatement = connection.prepareStatement(queryInsertMealReq);
      {
        preparedStatement.setInt(1, requester.getRequesterID());
        preparedStatement.setString(2, requester.getRequesterName());
        preparedStatement.setString(
            3, "mealName"); // adds meal by meal name not my class -> can later figure out how
        // to
        preparedStatement.setString(4, mealReq.getStat());
        preparedStatement.setString(5, mealReq.getRoom());
        preparedStatement.setString(6, mealReq.getSpecialNotes());

        // System.out.println(preparedStatement);
        // Step 3: Execute the query or update query
        preparedStatement.executeUpdate();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void addConferenceRoomRequest(ConferenceRoomRequest confReq, Requester requester) {
    try {
      String CONFREQUEST = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // query
      String queryInsertMealReq = "INSERT INTO " + CONFREQUEST + " VALUES (?,?,?,?,?,?);";
      PreparedStatement preparedStatement = connection.prepareStatement(queryInsertMealReq);
      {
        preparedStatement.setInt(1, requester.getRequesterID());
        preparedStatement.setString(2, requester.getRequesterName());
        preparedStatement.setString(
            3,
            confReq.getStat()); // adds meal by meal name not my class -> can later figure out how
        // to
        preparedStatement.setString(4, confReq.getStartTime());
        preparedStatement.setString(5, confReq.getEndTime());
        preparedStatement.setString(6, confReq.getAddtionalNotes());

        preparedStatement.executeUpdate();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void updateConferenceRoomRequest(ConferenceRoomRequest confReq) {
    try {
      String CONFREQUEST = "\"ServiceRequests\".\"conferenceRoomRequest\"";
      // query
      String updateConfQuery =
          "UPDATE  "
              + CONFREQUEST
              + " SET \"status\"=?, "
              + "\"startTime\"=? "
              + "\"endTime\"=? "
              + "WHERE "
              + "\"status\"=? "
              + "AND \"startTime\"=?;"
              + "AND \"endTime\"=?;";

      PreparedStatement preparedStatement = connection.prepareStatement(updateConfQuery);
      {
        preparedStatement.setString(
            3,
            confReq.getStat()); // adds meal by meal name not my class -> can later figure out how
        // to
        preparedStatement.setString(4, confReq.getStartTime());
        preparedStatement.setString(5, confReq.getEndTime());
        preparedStatement.executeUpdate();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void updateMealRequest(MealRequest mealReq) {
    try {
      String MEAL = "\"ServiceRequests\".\"mealRequest\"";
      // query
      String updateMealQuery =
          "UPDATE  "
              + MEAL
              + " SET \"meal\"=?, "
              + "\"status\"=? "
              + "\"room\"=? "
              + "WHERE "
              + "\"meal\"=? "
              + "AND \"status\"=?;"
              + "AND \"room\"=?;";

      PreparedStatement preparedStatement = connection.prepareStatement(updateMealQuery);
      {
        preparedStatement.setString(
            3,
            mealReq
                .getSelection()
                .getMealName()); // adds meal by meal name not my class -> can later figure out how
        // to
        preparedStatement.setString(4, mealReq.getStat());
        preparedStatement.setString(5, mealReq.getRoom());
        preparedStatement.executeUpdate();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void loadDatabaseTables(
      List<Node> databaseNodeList,
      List<Edge> databaseEdgeList,
      List<LocationName> databaseLocationNameList,
      List<Move> databaseMoveList) {

    try {
      Statement stmtNode = connection.createStatement();
      Statement stmtEdge = connection.createStatement();
      Statement stmtLocationName = connection.createStatement();
      Statement stmtMove = connection.createStatement();
      // table names
      String node = "\"hospitalNode\".node";
      String edge = "\"hospitalNode\".edge";
      String locationName = "\"hospitalNode\".\"locationName\"";
      String move = "\"hospitalNode\".\"move\"";
      // queries
      String queryDisplayNodes = "SELECT * FROM " + node;
      String queryDisplayEdges = "SELECT * FROM " + edge;
      String queryDisplayLocationNames = "SELECT * FROM " + locationName;
      String queryDisplayMoves = "SELECT * FROM " + move;

      ResultSet rsNodes = stmtNode.executeQuery(queryDisplayNodes);
      ResultSet rsEdges = stmtEdge.executeQuery(queryDisplayEdges);
      ResultSet rsLocationNames = stmtLocationName.executeQuery(queryDisplayLocationNames);
      ResultSet rsMoves = stmtMove.executeQuery(queryDisplayMoves);
      while (rsNodes.next()) {
        String nodeID = rsNodes.getString("nodeID");
        int xCoord = rsNodes.getInt("xcoord");
        int yCoord = rsNodes.getInt("ycoord");
        String floor = rsNodes.getString("floorNum");
        String building = rsNodes.getString("building");

        databaseNodeList.add(new Node(nodeID, xCoord, yCoord, floor, building));
      }
      while (rsEdges.next()) {
        String startNode = rsEdges.getString("startNode");
        String endNodeString = rsEdges.getString("endNode");
        Node startNodeObject = null;
        Node endNodeObject = null;
        for (Node n : databaseNodeList) {
          if (n.getNodeID().equals(startNode)) {
            startNodeObject = n;
          }
          if (n.getNodeID().equals(endNodeString)) {
            endNodeObject = n;
          }
        }
        Edge edge1 = new Edge(startNodeObject, endNodeObject);
        databaseEdgeList.add(edge1);
      }
      while (rsLocationNames.next()) {
        String locationNameLong = rsLocationNames.getString("longName");
        String locationNameShort = rsLocationNames.getString("shortName");
        String nodeType = rsLocationNames.getString("nodeType");
        databaseLocationNameList.add(
            new LocationName(locationNameLong, locationNameShort, nodeType));
      }
      while (rsMoves.next()) {
        String nodeID = rsMoves.getString("nodeID");
        String longName = rsMoves.getString("longName");
        Date date = rsMoves.getDate("moveDate");
        databaseMoveList.add(new Move(nodeID, longName, date));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void syncNodeDB(Node node, String operation) {
    try {
      // table names
      String NODE = "\"hospitalNode\".node";
      // queries
      String queryInsertNodesDB = "INSERT INTO " + NODE + " VALUES (?,?,?,?,?);";
      String queryUpdateNodesDB =
          "UPDATE  "
              + NODE
              + " SET \"nodeID\"=?, xcoord=?, ycoord=?, \"floorNum\"=?, building=? WHERE \"nodeID\"=?; ";
      String queryDeleteNodesDB = "DELETE FROM " + NODE + " WHERE \"nodeID\"=?; ";

      PreparedStatement ps;
      if (operation.equals("insert")) {
        ps = connection.prepareStatement(queryInsertNodesDB);
      } else if (operation.equals("update")) {
        ps = connection.prepareStatement(queryUpdateNodesDB);
      } else if (operation.equals("delete")) {
        ps = connection.prepareStatement(queryDeleteNodesDB);
      } else {
        throw new Exception("Invalid operation");
      }
      ps.setString(1, node.getNodeID());
      ps.setInt(2, node.getXCoord());
      ps.setInt(3, node.getYCoord());
      ps.setString(4, node.getFloor());
      ps.setString(5, node.getBuilding());
      if (operation.equals("update")) {
        ps.setString(6, node.getNodeID());
      }
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void syncEdgeDB(Edge edge, String operation) {
    try {
      // table names
      String EDGE = "\"hospitalNode\".edge";
      // queries

      String queryInsertEdgesDB = "INSERT INTO " + EDGE + " VALUES (?,?); ";
      String queryUpdateEdgesDB =
          "UPDATE  "
              + EDGE
              + " SET \"startNode\"=?, \"endNode\"=? WHERE \"startNode\"=? AND \"endNode\"=?;";
      String queryDeleteEdgesDB =
          "DELETE FROM " + EDGE + " WHERE \"startNode\"=? AND \"endNode\"=?; ";

      PreparedStatement ps;
      if (operation.equals("insert")) {
        ps = connection.prepareStatement(queryInsertEdgesDB);
      } else if (operation.equals("update")) {
        ps = connection.prepareStatement(queryUpdateEdgesDB);
      } else if (operation.equals("delete")) {
        ps = connection.prepareStatement(queryDeleteEdgesDB);
      } else {
        throw new Exception("Invalid operation");
      }

      ps.setString(1, edge.getStartNode().getNodeID());
      ps.setString(2, edge.getEndNode().getNodeID());
      if (operation.equals("update")) {
        ps.setString(3, edge.getStartNode().getNodeID());
        ps.setString(4, edge.getEndNode().getNodeID());
      }
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void syncLocationNameDB(LocationName locationName, String operation) {
    try {
      // table names
      String LOCATIONNAME = "\"hospitalNode\".\"locationName\"";
      // queries
      String queryInsertLocationNamesDB = "INSERT INTO " + LOCATIONNAME + " VALUES (?,?,?); ";
      String queryUpdateLocationNamesDB =
          "UPDATE  "
              + LOCATIONNAME
              + " SET \"longName\"=?, \"shortName\"=?, \"nodeType\"=? WHERE \"longName\"=?; ";
      String queryDeleteLocationNamesDB = "DELETE FROM " + LOCATIONNAME + " WHERE \"longName\"=?; ";

      PreparedStatement ps;
      if (operation.equals("insert")) {
        ps = connection.prepareStatement(queryInsertLocationNamesDB);
      } else if (operation.equals("update")) {
        ps = connection.prepareStatement(queryUpdateLocationNamesDB);
      } else if (operation.equals("delete")) {
        ps = connection.prepareStatement(queryDeleteLocationNamesDB);
      } else {
        throw new Exception("Invalid operation");
      }

      ps.setString(1, locationName.getLongName());
      ps.setString(2, locationName.getShortName());
      ps.setString(3, locationName.getNodeType());
      if (operation.equals("update")) {
        ps.setString(4, locationName.getLongName());
      }
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void syncMoveDB(Move move, String operation) {
    try {
      // table names
      String MOVE = "\"hospitalNode\".move";
      // queries
      String queryInsertMovesDB = "INSERT INTO " + MOVE + " VALUES (?,?,?); ";
      String queryUpdateMovesDB =
          "UPDATE  "
              + MOVE
              + " SET \"nodeID\"=?, \"longName\"=?, \"moveDate\"=? WHERE \"nodeID\"=?; ";
      String queryDeleteMovesDB = "DELETE FROM " + MOVE + " WHERE \"nodeID\"=?; ";

      PreparedStatement ps;
      if (operation.equals("insert")) {
        ps = connection.prepareStatement(queryInsertMovesDB);
      } else if (operation.equals("update")) {
        ps = connection.prepareStatement(queryUpdateMovesDB);
      } else if (operation.equals("delete")) {
        ps = connection.prepareStatement(queryDeleteMovesDB);
      } else {
        throw new Exception("Invalid operation");
      }

      ps.setString(1, move.getNodeID());
      ps.setString(2, move.getLongName());
      ps.setDate(3, move.getDate());
      if (operation.equals("update")) {
        ps.setString(4, move.getNodeID());
      }
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static void displayInstructions() {
    System.out.println(
        "===========================================\n"
            + "Options:\n"
            + "Display node and edge information\n"
            + "Update node coordinates\n"
            + "Update name of location node\n"
            + "Get specific node\n"
            + "Export node table into a CSV file\n"
            + "Export edge table into a CSV file\n"
            + "Export location name table into a CSV file\n"
            + "Export move table into a CSV file\n"
            + "Import from a CSV file into the node table\n"
            + "import from a CSV file into the edge table\n"
            + "import from a CSV file into the location name table\n"
            + "import from a CSV file into the move table\n"
            + "Delete a node\n"
            + "Delete an edge\n"
            + "display move information\n"
            + "Help\n"
            + "Exit\n"
            + "===========================================\n");
  }

  static void displayNodeAndEdgeInfo(List<Node> databaseNodeList, List<Edge> databaseEdgeList) {
    System.out.println("Node information:\n");
    for (Node node : databaseNodeList) {
      System.out.println(
          node.getNodeID()
              + "\t"
              + node.getXCoord()
              + "\t"
              + node.getYCoord()
              + "\t"
              + node.getFloor()
              + "\t"
              + node.getBuilding());
    }
    System.out.println(
        "----------------------------------------------------------------------------------------------------------------------------");
    System.out.println("Edge information: \n");
    for (Edge edge : databaseEdgeList) {
      System.out.println(edge.getStartNode().getNodeID() + "\t" + edge.getEndNode().getNodeID());
    }
  }

  static void displayMoveInfo(List<Move> databaseMoveList) {
    System.out.println("Move information:\n");
    for (Move move : databaseMoveList) {
      System.out.println(move.getNodeID() + "\t" + move.getLongName() + "\t" + move.getDate());
    }
  }

  static void updateCoordinates(
      Connection connection,
      List<Node> databaseNodeList,
      int xCoordinate,
      int yCoordinate,
      String nodeID) {
    for (Node node : databaseNodeList) {
      if (node.getNodeID().equals(nodeID)) {
        // update coordinates in node
        node.setXCoord(xCoordinate);
        node.setYCoord(yCoordinate);
        syncNodeDB(node, "update");
        break;
      }
    }
  }

  /**
   * Updates the long and short location name in the database based on nodeID
   *
   * @param databaseLocationNameList list of location names in the database
   * @param databaseMoveList list of moves in the database
   * @param locationNameLong new long name
   * @param locationNameShort new short name
   * @param nodeID nodeID of the node to update
   */
  static void updateLocationName(
      List<LocationName> databaseLocationNameList,
      List<Move> databaseMoveList,
      String locationNameLong,
      String locationNameShort,
      String nodeID) {
    String oldLongName = "";
    for (Move move : databaseMoveList) {
      if (move.getNodeID().equals(nodeID)) {
        oldLongName = move.getLongName();
        move.setLongName(locationNameLong);
        move.setDate(Date.valueOf(LocalDate.now()));
      }
    }
    for (LocationName locationName : databaseLocationNameList) {
      if (locationName.getLongName().equals(oldLongName)) {
        locationName.setLongName(locationNameLong);
        locationName.setShortName(locationNameShort);
        break;
      }
    }
  }

  static void getNode(List<Node> databaseNodeList, String nodeID) {
    System.out.println("Node:\n");
    for (Node node : databaseNodeList) {
      if (node.getNodeID().equals(nodeID)) {
        System.out.println(
            node.getNodeID()
                + "\t"
                + node.getXCoord()
                + "\t"
                + node.getYCoord()
                + "\t"
                + node.getXCoord()
                + "\t"
                + node.getFloor()
                + "\t"
                + node.getBuilding());
        break;
      }
    }
  }

  static void deleteNode(Connection connection, List<Node> databaseNodeList, String nodeID) {
    int i = 0;
    for (Node node : databaseNodeList) {
      if (node.getNodeID().equals(nodeID)) {
        databaseNodeList.remove(i);
        System.out.println("node deletion successful!");
        syncNodeDB(node, "delete");
        break;
      }
      i++;
    }
  }

  static void deleteEdge(
      Connection connection, List<Edge> databaseEdgeList, String startNodeID, String endNodeID) {
    int i = 0;
    for (Edge edge : databaseEdgeList) {
      if ((edge.getStartNode().getNodeID().equals(startNodeID))
          && (edge.getEndNode().getNodeID().equals(endNodeID))) {
        databaseEdgeList.remove(i);
        System.out.println("edge deletion successful!");
        syncEdgeDB(edge, "delete");
        break;
      }
      i++;
    }
  }

  static void importCSVNode(String csvFile, List<Node> databaseNodeList) {
    // Regular expression to match each row
    String regex = "(.*),(\\d+),(\\d+),(.*),(.*)";
    // Compile regular expression pattern
    Pattern pattern = Pattern.compile(regex);
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      String line;
      br.readLine(); // skip the first line
      while ((line = br.readLine()) != null) {
        // Match the regular expression to the current line
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
          String nodeID = matcher.group(1);
          int xCoord = Integer.parseInt(matcher.group(2));
          int yCoord = Integer.parseInt(matcher.group(3));
          String floor = matcher.group(4);
          String building = matcher.group(5);
          Node node = new Node(nodeID, xCoord, yCoord, floor, building);
          databaseNodeList.add(node);
          syncNodeDB(node, "insert");
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static void importCSVEdge(
      String csvFile, List<Edge> databaseEdgeList, List<Node> databaseNodeList) {
    // Regular expression to match each row
    String regex = "(.*),(.*)";
    // Compile regular expression pattern
    Pattern pattern = Pattern.compile(regex);
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      String line;
      br.readLine();
      while ((line = br.readLine()) != null) {
        // Match the regular expression to the current line
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
          String startNodeID = matcher.group(1);
          String endNodeID = matcher.group(2);
          Node startNode = null;
          Node endNode = null;
          for (Node node : databaseNodeList) {
            if (node.getNodeID().equals(startNodeID)) {
              startNode = node;
            }
            if (node.getNodeID().equals(endNodeID)) {
              endNode = node;
            }
          }
          Edge edge = new Edge(startNode, endNode);
          databaseEdgeList.add(edge);
          syncEdgeDB(edge, "insert");
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static void importCSVLocationName(String csvFile, List<LocationName> databaseLocationNameList) {
    // Regular expression to match each row
    String regex = "(.*),(.*),(.*)";
    // Compile regular expression pattern
    Pattern pattern = Pattern.compile(regex);
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      String line;
      br.readLine();
      while ((line = br.readLine()) != null) {
        // Match the regular expression to the current line
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
          String longName = matcher.group(1);
          String shortName = matcher.group(2);
          String nodeType = matcher.group(3);
          LocationName locationName = new LocationName(longName, shortName, nodeType);
          databaseLocationNameList.add(locationName);
          syncLocationNameDB(locationName, "insert");
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static void importCSVMove(String csvFile, List<Move> databaseMoveList) {
    // Regular expression to match each row
    String regex = "(.*),(.*),(.*)";
    // Compile regular expression pattern
    Pattern pattern = Pattern.compile(regex);
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      String line;
      br.readLine();
      while ((line = br.readLine()) != null) {
        // Match the regular expression to the current line
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
          String nodeID = matcher.group(1);
          String longName = matcher.group(2);
          String dateString = matcher.group(3);
          Date moveDate = returnDate(dateString);
          Move move = new Move(nodeID, longName, moveDate);
          databaseMoveList.add(move);
          syncMoveDB(move, "insert");
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static Date returnDate(String dateString) {
    // function to convert to yyyy-mm-dd
    SimpleDateFormat[] formats =
        new SimpleDateFormat[] {
          new SimpleDateFormat("d/M/yyyy"),
          new SimpleDateFormat("dd/M/yyyy"),
          new SimpleDateFormat("dd/MM/yyyy"),
          new SimpleDateFormat("d/MM/yyyy")
        };
    for (SimpleDateFormat format : formats) {
      try {
        java.util.Date utilDate = format.parse(dateString);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
      } catch (ParseException e) {
        // ignore and try next format
      }
    }
    return null;
  }

  static void createFile(String fileName) throws IOException {
    File file = new File(fileName);
    if (file.createNewFile()) {
      System.out.println("File created: " + file.getName());
    } else {
      System.out.println("File already exists.");
    }
  }

  static void exportNodesToCSV(String csvFile, List<Node> databaseNodeList) throws IOException {
    createFile(csvFile);
    BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
    // Write the header row to the CSV file
    writer.write("nodeID,xCoord,yCoord,floor,building\n");
    // Write each Node into the CSV file
    for (Node node : databaseNodeList) {
      writer.write(
          node.getNodeID()
              + ","
              + node.getXCoord()
              + ","
              + node.getYCoord()
              + ","
              + node.getFloor()
              + ","
              + node.getBuilding()
              + "\n");
    }
    writer.close();
  }

  static void exportEdgesToCSV(String csvFile, List<Edge> databaseEdgeList) throws IOException {
    createFile(csvFile);
    BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
    // Write the header row to the CSV file
    writer.write("startNodeID,endNodeID\n");
    for (Edge edge : databaseEdgeList) {
      writer.write(edge.getStartNode().getNodeID() + "," + edge.getEndNode().getNodeID() + "\n");
    }
    writer.close();
  }

  static void exportLocationNamesToCSV(String csvFile, List<LocationName> databaseLocationNameList)
      throws IOException {
    createFile(csvFile);
    BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
    // Write the header row to the CSV file
    writer.write("longName,shortName,nodeType\n");
    for (LocationName locationName : databaseLocationNameList) {
      writer.write(
          locationName.getLongName()
              + ","
              + locationName.getShortName()
              + ","
              + locationName.getNodeType()
              + "\n");
    }
    writer.close();
  }

  static void exportMovesToCSV(String csvFile, List<Move> databaseMoveList) throws IOException {
    createFile(csvFile);
    BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
    // Write the header row to the CSV file
    writer.write("nodeID,longName,moveDate\n");
    for (Move move : databaseMoveList) {
      writer.write(move.getNodeID() + "," + move.getLongName() + "," + move.getDate() + "\n");
    }
    writer.close();
  }
}
