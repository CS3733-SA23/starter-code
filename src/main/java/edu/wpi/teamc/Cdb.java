package edu.wpi.teamc;

import edu.wpi.teamc.databaseClasses.Edge;
import edu.wpi.teamc.databaseClasses.Node;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cdb {

  public static void main(String[] args) {
    Connection connection = null;

    try {
      // Load the PostgreSQL JDBC driver
      Class.forName("org.postgresql.Driver");

      // Establish the connection
      String url = "jdbc:postgresql://database.cs.wpi.edu/teamcdb";
      String user = "teamc";
      String password = "teamc30";
      connection = DriverManager.getConnection(url, user, password);

      Scanner scanner = new Scanner(System.in);
      //database tables turned into two arrayLists
      List<Node> databaseNodeList = new ArrayList<Node>();
      List<Edge> databaseEdgeList = new ArrayList<Edge>();
      // load database into lists
      loadDatabaseTables(connection, databaseNodeList, databaseEdgeList);
      // variables
      String nodeID;
      int xCoordinate;
      int yCoordinate;
      String locationNameLong;
      String locationNameShort;
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
            updateCoordinates(databaseNodeList, xCoordinate, yCoordinate, nodeID);
            break;
          case "update name of location node":
            System.out.println("please type the nodeID of the node you would like to update:");
            nodeID = scanner.nextLine();
            System.out.println("Enter new location name in full:");
            locationNameLong = scanner.nextLine();
            System.out.println("Enter the shortened version of the new location name:");
            locationNameShort = scanner.nextLine();
            // update the location name of the given nodeID
            updateLocationName(connection, locationNameLong, locationNameShort, nodeID);
            break;
          case "export node table into a csv file":
            //
            break;
          case "import from a csv file into the node table\n":
            //
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

  static void loadDatabaseTables(
      Connection connection, List<Node> databaseNodeList, List<Edge> databaseEdgeList) {
    try {
      Statement stmtNode = connection.createStatement();
      Statement stmtEdge = connection.createStatement();
      // table names
      String node = "\"hospitalNode\".node";
      String edge = "\"hospitalNode\".edge";
      // queries
      String queryDisplayNodes = "SELECT * FROM " + node;
      String queryDisplayEdges = "SELECT * FROM " + edge;

      ResultSet rsNodes = stmtNode.executeQuery(queryDisplayNodes);
      ResultSet rsEdges = stmtEdge.executeQuery(queryDisplayEdges);
      while (rsNodes.next()) {
        String nodeID = rsNodes.getString("nodeID");
        int xCoord = rsNodes.getInt("xcoord");
        int yCoord = rsNodes.getInt("ycoord");
        String floor = rsNodes.getString("floorNum");
        String building = rsNodes.getString("building");
        String nodeType = rsNodes.getString("nodeType");
        String longName = rsNodes.getString("longName");
        String shortName = rsNodes.getString("shortName");

        databaseNodeList.add(
            new Node(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName, null));
      }
      while (rsEdges.next()) {
        String edgeID = rsEdges.getString("edgeID");
        String startNode = rsEdges.getString("startNode");
        String endNode = rsEdges.getString("endNode");
        databaseEdgeList.add(new Edge(edgeID, startNode, endNode));
      }
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
            + "Export node table into a CSV file\n"
            + "Import from a CSV file into the node table\n"
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
              + node.getXCoord()
              + "\t"
              + node.getFloor()
              + "\t"
              + node.getBuilding()
              + "\t"
              + node.getNodeType()
              + "\t"
              + node.getLongName()
              + "\t"
              + node.getShortName());
    }
    System.out.println(
        "----------------------------------------------------------------------------------------------------------------------------");
    System.out.println("Edge information: \n");
    for (Edge edge : databaseEdgeList) {
      System.out.println(edge.getId() + "\t" + edge.getStartNode() + "\t" + edge.getEndNode());
    }
  }

  static void updateCoordinates(
      List<Node> databaseNodeList, int xCoordinate, int yCoordinate, String nodeID) {
    for (Node node : databaseNodeList) {
      if (node.getNodeID().equals(nodeID)) {
        // update coordinates in node
        node.setXCoord(xCoordinate);
        node.setYCoord(yCoordinate);
        break;
      }
    }
  }

  static void updateLocationName(
      Connection connection, String locationNameLong, String locationNameShort, String nodeID) {
    try {
      PreparedStatement stmtUpdateNodeName =
          connection.prepareStatement(
              "UPDATE \"hospitalNode\".node\n"
                  + "SET \"longName\" = ?, \"shortName\" = ?\n"
                  + "WHERE \"nodeID\" = ?;");
      // set parameters for prepared statement
      stmtUpdateNodeName.setString(1, locationNameLong);
      stmtUpdateNodeName.setString(2, locationNameShort);
      stmtUpdateNodeName.setString(3, nodeID);

      int rowsUpdated = stmtUpdateNodeName.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("update successful!");
      } else {
        System.out.println("Node not found: Invalid nodeID");
      }
    } catch (SQLException e) {
      System.out.println("SQL exception occurred: " + e.getMessage());
    }
  }
}
