package Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pathfinding.HospitalEdge;
import pathfinding.HospitalNode;

public class DatabaseController {
  private Connection c;
  private static List<HospitalNode> nodeList = new ArrayList<>();
  private static List<HospitalEdge> edgeList = new ArrayList<>();

  public static void main(String[] args) throws SQLException, IOException {
    Scanner s1 = new Scanner(System.in);

    System.out.println("Please enter your username (will default to \"teame\"): ");
    String username = s1.nextLine(); // Unused in this Prototype
    System.out.println("Please enter your password (will default to \"teame50\"): ");
    String password = s1.nextLine(); // Unused in this Prototype

    DatabaseController DBC1 = new DatabaseController("teame", "teame50");

    /*
    //For Testing
    try {
      DBC1.exportToCSV("l1edges", "C:\\Users\\Aviro\\OneDrive\\Desktop\\", "csvtestfile.csv");
    } catch (FileNotFoundException e) {
      System.out.println("The file is being dum");
    }
    */

    boolean exit = true;
    while (exit) {
      System.out.println("\nWhat would you like to do?");
      System.out.println("Choices: update, retrieve, delete, help, exit");
      String function = s1.nextLine().toLowerCase().trim();

      switch (function) {
        case "update":
          DBC1.updateTable();
          break;

        case "delete":
          DBC1.deleteFromTable();
          break;

        case "help":
          DBC1.help();
          break;

        case "exit":
          DBC1.exitDatabaseProgram();
          exit = false;
          break;

        case "retrieve":
          DBC1.retrieveFromTable();
          break;

        default:
          System.out.println("Please enter a valid action");
      }
    }
  }

  public DatabaseController(String username, String password) {
    c = this.connectToDatabase(username, password);
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

  private void deleteFromTable() {
    try {
      Statement stmt = null;
      Scanner s1 = new Scanner(System.in);
      System.out.println("Which table would you like to delete from (Nodes, Edges): ");
      String tabletoEdit = s1.nextLine().toLowerCase().trim();

      if (tabletoEdit.equals("nodes")) {
        System.out.println("Please type the Node ID you would like to delete: ");
        String nodetoDelete = s1.nextLine();

        try {
          stmt = c.createStatement();
          String sql = "DELETE FROM teame.l1nodes WHERE nodeid = '" + nodetoDelete + "';";
          stmt.execute(sql);
          stmt.close();
          System.out.println("Row Deleted successfully from " + tabletoEdit);
        } catch (Exception e) {
          System.out.println("You've entered an invalid nodeid");
        }
      } else if (tabletoEdit.equals("edges")) {
        System.out.println("Please type the Edge ID you would like to delete: ");
        String edgetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM teame.l1edges WHERE edgeid = '" + edgetoDelete + "'";
        stmt.execute(sql);
        stmt.close();
      }
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void retrieveFromTable() {

    nodeList = new ArrayList<>();
    edgeList = new ArrayList<>();

    List<String> eList = new ArrayList<>();
    List<String> nList = new ArrayList<>();

    String queryCountE = "SELECT COUNT(*) FROM teame.l1edges;";
    String queryCountN = "SELECT COUNT(*) FROM teame.l1nodes;";
    String queryCountEID = "SELECT edgeID FROM teame.l1edges;";
    String queryCountNID = "SELECT nodeID FROM teame.l1nodes;";

    try (Statement stmt = c.createStatement()) {
      ResultSet rsn = stmt.executeQuery(queryCountN);
      if (rsn.next()) {
        int nodeCount = rsn.getInt(1);
        ResultSet rsNodes = stmt.executeQuery(queryCountNID);
        for (int i = 1; i <= nodeCount; i++) {
          if (rsNodes.next()) {
            nList.add(rsNodes.getString("nodeID"));
          }
        }
      }
      ResultSet rse = stmt.executeQuery(queryCountE);
      if (rse.next()) {
        int edgeCount = rse.getInt(1);
        ResultSet rsEdges = stmt.executeQuery(queryCountEID);
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

  private void displayCSVInfo() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Which table would you like to see info from (Nodes, Edges): ");
    String table = scanner.nextLine().trim();

    if (table.equalsIgnoreCase("Nodes")) {
      System.out.print("Please type the Node ID you would like to see the information from: ");
      String nodeId = scanner.nextLine().trim();

      try (PreparedStatement pstmt =
          c.prepareStatement("SELECT * FROM teame.l1nodes WHERE nodeID = '" + nodeId + "'")) {
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
          String nodeID = rs.getString("nodeID");
          int xCoord = rs.getInt("xcoord");
          int yCoord = rs.getInt("ycoord");
          String floor = rs.getString("floor");
          String building = rs.getString("building");
          String nodeType = rs.getString("nodeType");
          String longName = rs.getString("longName");
          String shortName = rs.getString("shortName");

          System.out.println(
              "Edge ("
                  + nodeID
                  + ") information (nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName): ");
          System.out.println(
              nodeID + ", " + xCoord + ", " + yCoord + ", " + floor + ", " + building + ", "
                  + nodeType + ", " + longName + ", " + shortName);
        } else {
          System.out.println("Node not found with ID " + nodeId);
        }
      } catch (SQLException e) {
        System.err.println("Error retrieving node: " + e.getMessage());
      }
    } else if (table.equalsIgnoreCase("Edges")) {
      System.out.print("Please type the Edge ID you would like to see the information from: ");
      String edgeId = scanner.nextLine().trim();

      try (PreparedStatement pstmt =
          c.prepareStatement("SELECT * FROM teame.l1edges WHERE edgeid = '" + edgeId + "'")) {
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
          String edgeID = rs.getString("edgeID");
          String startNode = rs.getString("startNode");
          String endNode = rs.getString("endNode");

          System.out.println("Edge (" + edgeId + ") information (edgeID, startNode, endNode): ");
          System.out.println(edgeID + ", " + startNode + ", " + endNode);
        } else {
          System.out.println("Edge not found with ID " + edgeId);
        }
      } catch (SQLException e) {
        System.err.println("Error finding edge: " + e.getMessage());
      }
    } else {
      System.out.println("Invalid table name: " + table);
    }
  }

  private HospitalNode extractNodeFromResultSet(ResultSet rs) throws SQLException {
    String nodeID = rs.getString("nodeid");
    int xCoord = rs.getInt("xcoord");
    int yCoord = rs.getInt("ycoord");
    String floor = rs.getString("floor");
    String building = rs.getString("building");
    String nodeType = rs.getString("nodeType");
    String longName = rs.getString("longName");
    String shortName = rs.getString("shortName");
    return new HospitalNode(nodeID, xCoord, yCoord, floor, building, nodeType, longName, shortName);
  }

  private HospitalEdge extractEdgeFromResultSet(ResultSet rs) throws SQLException {
    String edgeID = rs.getString("edgeID");
    String startNode = rs.getString("startNode");
    String endNode = rs.getString("endNode");

    return new HospitalEdge(edgeID, startNode, endNode);
  }

  private void updateTable() {

    Statement stmt = null;

    boolean doneUpdating = true;
    while (doneUpdating) {

      Scanner s1 = new Scanner(System.in);
      System.out.println("Which table would you like to update (Nodes, Edges): ");
      String tabletoUpdate = s1.nextLine().toLowerCase();

      try {
        if (tabletoUpdate.equals("nodes")) {

          System.out.println("Please type the Node ID you would like to update: ");
          String nodetoUpdate = s1.nextLine();

          System.out.println("Which attribute would you like to update (xcoord, ycoord, longname)");
          String attributeToUpdate = s1.nextLine().toLowerCase();
          stmt = c.createStatement();

          String newval = "";
          int newInt = 0;
          String sql = "";

          switch (attributeToUpdate) {
            case "xcoord":
              System.out.println("Please enter the new xcoord: ");
              newInt = s1.nextInt();
              sql =
                  "UPDATE l1nodes SET xcoord = "
                      + newInt
                      + " WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
              System.out.println(
                  "xcoord of " + nodetoUpdate + " successfully changed to " + newInt);
              stmt.execute(sql);
              stmt.close();
              break;

            case "ycoord":
              System.out.println("Please enter the new ycoord: ");
              newInt = s1.nextInt();
              sql =
                  "UPDATE l1nodes SET ycoord = "
                      + newInt
                      + " WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
              System.out.println(
                  "ycoord of " + nodetoUpdate + " successfully changed to " + newInt);
              stmt.execute(sql);
              stmt.close();
              break;

            case "longname":
              System.out.println("Please enter the new longname: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1nodes SET longname = '"
                      + newval
                      + "' WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
              System.out.println(
                  "longname of " + nodetoUpdate + " successfully changed to " + newval);
              stmt.executeUpdate(sql);
              stmt.close();
              break;

            default:
              System.out.println("You selected an invalid attribute to edit");
          }

        } else if (tabletoUpdate.equals("edges")) {
          System.out.println("Please type the Edge ID you would like to update: ");
          String edgetoUpdate = s1.nextLine();

          System.out.println(
              "Which attribute would you like to update (edgeid, startnode, endnode)");
          String attributeToUpdate = s1.nextLine().toLowerCase();
          stmt = c.createStatement();

          String newval = "";
          int newInt = 0;
          String sql = "";

          switch (attributeToUpdate) {
            case ("edgeid"):
              System.out.println("Please enter the new edgeid: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1edges SET edgeid = '"
                      + newval
                      + "' WHERE edgeid = '"
                      + edgetoUpdate
                      + "';";
              stmt.executeUpdate(sql);
              stmt.close();
              break;

            case ("startnode"):
              System.out.println("Please enter the new startnode: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1edges SET startnode = '"
                      + newval
                      + "' WHERE edgeid = '"
                      + edgetoUpdate
                      + "';";
              stmt.executeUpdate(sql);
              stmt.close();
              break;

            case ("endnode"):
              System.out.println("Please enter the new endnode: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1edges SET endnode = '"
                      + newval
                      + "' WHERE edgeid = '"
                      + edgetoUpdate
                      + "';";
              stmt.executeUpdate(sql);
              stmt.close();
              break;

            default:
              System.out.println("Please enter a valid attribute to edit");
          }
        }
      } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        System.exit(0);
      }

      System.out.println("Are you done updating attributes (y/n)? ");
      s1.nextLine();
      String ans = s1.nextLine();
      if (ans.equals("y")) {
        doneUpdating = false;
      }
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
        "\tThe User inputs which operation they wish to use (delete, retrieve, update, help)");
    System.out.println(
        "\tThe user then inputs the id of what they want to modify in the database.");
    System.out.println(
        "\tThe User inputs all other necessary information for the specified editing operation.");
    System.out.println(
        "\tThe User then inputs whether or not they want to edit the database further.");
    System.out.println("\nType \"exit\" to leave the help screen at any time:");

    while (!exit) {
      String response = s1.nextLine().toLowerCase();
      if (response.equals("exit")) {
        exit = true;
      }
    }

    /*
    // User Operations:
    System.out.println("\tUser Operations:\n");
    System.out.println("\t\tUser inputs username to database.");
    System.out.println("\t\tUser inputs password to database.");
    System.out.println(
        "\t\tUser inputs which operation they wish to use (delete, retrieve, update, help)");

    System.out.println("\t\t\tIf delete:");
    System.out.println("\t\t\t\tUser inputs which table they wish to edit (nodes or edges).");
    System.out.println("\t\t\t\t\tIf nodes:");
    System.out.println("\t\t\t\t\t\tUser inputs the node ID they wish to delete.");
    System.out.println("\t\t\t\t\tIf edges:");
    System.out.println("\t\t\t\t\t\tUser inputs the edge ID they wish to delete.");

    System.out.println("\t\t\tIf retrieve:");
    System.out.println("\t\t\t\tUser inputs which table they wish to edit (nodes or edges).");
    System.out.println("\t\t\t\t\tIf nodes:");
    System.out.println("\t\t\t\t\t\tUser inputs the node ID they wish to retrieve.");
    System.out.println("\t\t\t\t\tIf edges:");
    System.out.println("\t\t\t\t\t\tUser inputs the edge ID they wish to retrieve.");

    System.out.println("\t\t\tIf update:");
    System.out.println("\t\t\t\tUser inputs which table they wish to update (nodes or edges).");
    System.out.println("\t\t\t\t\tIf nodes:");
    System.out.println(
        "\t\t\t\t\t\tUser inputs the attribute they wish to update (xcoord, ycoord, longname).");
    System.out.println("\t\t\t\t\t\t\tIf xcoord:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new xcoord.");
    System.out.println("\t\t\t\t\t\t\tIf ycoord:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new ycoord.");
    System.out.println("\t\t\t\t\t\t\tIf longname:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new long name.");
    System.out.println("\t\t\t\t\tIf edges:");
    System.out.println(
        "\t\t\t\t\t\tUser inputs the attribute they wish to update (edgeID, startNode, endNode).");
    System.out.println("\t\t\t\t\t\t\tIf edgeID:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new edge ID.");
    System.out.println("\t\t\t\t\t\t\tIf startNode:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new start node.");
    System.out.println("\t\t\t\t\t\t\tIf endNode:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new end node.");

    // Functions:
    System.out.println("\n\n\tFunctions:\n");

    // connectToDatabase
    System.out.println("\t\tconnectToDatabase(String username, String password)");
    System.out.println("\t\t***Used to log into the database***");
    System.out.println("\t\tParameters:");
    System.out.println("\t\t\tString username: type in database username");
    System.out.println("\t\t\tString password: type in database password");
    System.out.println("\t\treturn: void\n\n");

    // deleteFromTable
    System.out.println("\t\tdeleteFromTable(Connection c)");
    System.out.println("\t\t***Used to delete a specified row from a table***");
    System.out.println("\t\tParameters:");
    System.out.println(
        "\t\t\tConnection c: connection from connectToDatabase (done automatically by the system)");
    System.out.println("\t\tUser Inputs:");
    System.out.println(
        "\t\t\ttableToEdit: Input which table the user wishes to edit (nodes or edges)");
    System.out.println("\t\t\t\tIf nodes:");
    System.out.println("\t\t\t\t\tnodeToDelete: Input the Node ID that the user wishes to delete");
    System.out.println("\t\t\t\tIf edges:");
    System.out.println("\t\t\t\t\tedgeToDelete: Input the Edge ID that the user wishes to delete");
    System.out.println("\t\treturn: void\n\n");

    // retrieveFromTable
    System.out.println("\t\tretrieveFromTable(Connection c)");
    System.out.println(
        "\t\t***Creates a HospitalNode and assigns the specified data from the table (unless it already exists)***");
    System.out.println("\t\tParameters:");
    System.out.println(
        "\t\t\tConnection c: connection from connectToDatabase (done automatically by the system)");
    System.out.println("\t\tUser Inputs:");
    System.out.println(
        "\t\t\ttableToEdit: Input which table the user wishes to edit (nodes or edges)");
    System.out.println("\t\t\t\tIf nodes:");
    System.out.println(
        "\t\t\t\t\tnodeToretrieve: Input the Node ID that the user wishes to retrieve");
    System.out.println("\t\t\t\tIf edges:");
    System.out.println(
        "\t\t\t\t\tedgeToretrieve: Input the Edge ID that the user wishes to retrieve");
    System.out.println("\t\treturn: void\n\n");

    // updateTable
    System.out.println("\t\tupdateTable(Connection c)");
    System.out.println(
        "\t\t***Updates the specified field of the specified node or edge with the new value***");
    System.out.println("\t\tParameters:");
    System.out.println(
        "\t\t\tConnection c: connection from connectToDatabase (done automatically by the system)");
    System.out.println("\t\tUser Inputs:");
    System.out.println(
        "\t\t\ttableToEdit: Input which table the user wishes to edit (nodes or edges)");
    System.out.println("\t\t\t\tIf nodes:");
    System.out.println(
        "\t\t\t\t\tattributeToUpdate: Input the attribute the user wishes to update (xcoord, ycoord, longname)");
    System.out.println("\t\t\t\t\t\tIf xcoord:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new xcoord");
    System.out.println("\t\t\t\t\t\tIf ycoord:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new ycoord");
    System.out.println("\t\t\t\t\t\tIf longname:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new longname");
    System.out.println("\t\t\t\t\t\tIf shortname:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new shortname");
    System.out.println("\t\t\t\tIf edges:");
    System.out.println(
        "\t\t\t\t\tattributeToUpdate: Input the attribute the user wishes to update (edgeID, startNode, endNode)");
    System.out.println("\t\t\t\t\t\tIf edgeID:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new edge ID");
    System.out.println("\t\t\t\t\t\tIf startNode:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new start node");
    System.out.println("\t\t\t\t\t\tIf endNode:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new end node");
    System.out.println(
        "\t\t\tdoneUpdating: Input if there are more attributes that the user wants to edit (y or n)");
    System.out.println("\t\t\t\tIf y:");
    System.out.println(
        "\t\t\t\t\tContinue while loop (doneUpdating is true) - returns to top of User Input Section and starts over");
    System.out.println("\t\t\t\tIf n:");
    System.out.println(
        "\t\t\t\t\tExit while loop (doneUpdating is false) - continues the rest of the program");
    System.out.println("\t\treturn: void\n\n");

    // help
    System.out.println("\t\thelp()");
    System.out.println(
        "\t\t\t***Displays information about how each function works and what the user should do when using the program***");
    System.out.println("\t\t\tParameters: None");
    System.out.println("\t\t\tUser Inputs:");
    System.out.println("\t\t\t\texit: Input exit when ready to leave help screen (exit)");
    System.out.println("\t\t\treturn: void\n\n");

     */

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

  private void exportToCSV(String name, String filePath, String fileName)
      throws SQLException, IOException {

    Statement stmt = null;
    stmt = c.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM " + name);

    File file = new File(filePath + File.separator + fileName);
    FileWriter fw;
    if (file.exists()) {
      fw = new FileWriter(file, true); // if file exists append to file. Works fine.
    } else {
      file.createNewFile();
      fw = new FileWriter(file);
    }

    FileWriter fileWriter = new FileWriter(file);

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

    fileWriter.close();
    rs.close();
    stmt.close();
  }
}
