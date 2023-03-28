package Database;

import java.sql.*;
import java.util.Scanner;
import pathfinding.HospitalEdge;
import pathfinding.HospitalNode;

public class DatabaseController {
  public static void main(String[] args) {
    DatabaseController DBC1 = new DatabaseController();
    Connection c = DBC1.connectToDatabase("teame", "teame50");

    // DBC1.retrieveFromTable(c);
    // DBC1.deleteFromTable(c);
    // DBC1.updateTable(c);
    // DBC1.help();
    // DBC1.displayCSVInfo(c);
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

  private void deleteFromTable(Connection c) {
    try {
      Statement stmt = null;
      Scanner s1 = new Scanner(System.in);
      System.out.println("Which table would you like to delete from (Nodes, Edges, Help Screen): ");
      String tabletoEdit = s1.nextLine().toLowerCase();

      if (tabletoEdit.equals("nodes")) {
        System.out.println("Please type the Node ID you would like to delete: ");
        String nodetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM teame.l1nodes WHERE nodeid = '" + nodetoDelete + "';";
        stmt.execute(sql);
        stmt.close();

      } else if (tabletoEdit.equals("edges")) {
        System.out.println("Please type the Edge ID you would like to delete: ");
        String edgetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM teame.l1edges WHERE edgeid = '" + edgetoDelete + "'";
        stmt.execute(sql);
        stmt.close();
      } else if (tabletoEdit.equals("Help Screen")) {
        this.help();
      }

      System.out.println("Row Deleted successfully from " + tabletoEdit);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void retrieveFromTable(Connection c) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Which table would you like to retrieve from (Nodes, Edges): ");
    String table = scanner.nextLine().trim();

    if (table.equalsIgnoreCase("Nodes")) {
      System.out.print("Please type the Node ID you would like to retrieve: ");
      String nodeId = scanner.nextLine().trim();

      try (PreparedStatement pstmt =
          c.prepareStatement("SELECT * FROM teame.l1nodes WHERE nodeID = '" + nodeId + "'")) {
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
          HospitalNode node = extractNodeFromResultSet(rs);
          System.out.println("Node retrieved successfully: " + node);
        } else {
          System.out.println("Node not found with ID " + nodeId);
        }
      } catch (SQLException e) {
        System.err.println("Error retrieving node: " + e.getMessage());
      }
    } else if (table.equalsIgnoreCase("Edges")) {
      System.out.print("Please type the Edge ID you would like to retrieve: ");
      String edgeId = scanner.nextLine().trim();

      try (PreparedStatement pstmt =
          c.prepareStatement("SELECT * FROM teame.l1edges WHERE edgeid = '" + edgeId + "'")) {
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
          HospitalEdge edge = extractEdgeFromResultSet(rs);
          System.out.println("Edge retrieved successfully: " + edge);
        } else {
          System.out.println("Edge not found with ID " + edgeId);
        }
      } catch (SQLException e) {
        System.err.println("Error retrieving edge: " + e.getMessage());
      }
    } else {
      System.out.println("Invalid table name: " + table);
    }
  }

  private void displayCSVInfo(Connection c) {
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
    String nodeID = rs.getString("nodeID");
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

  private void updateTable(Connection c) {

    Statement stmt = null;

    boolean doneUpdating = true;
    while (doneUpdating) {

      Scanner s1 = new Scanner(System.in);
      System.out.println("Which table would you like to update (Nodes, Edges, Help Screen): ");
      String tabletoUpdate = s1.nextLine().toLowerCase();

      try {
        if (tabletoUpdate.equals("nodes")) {

          System.out.println("Please type the Node ID you would like to update: ");
          String nodetoUpdate = s1.nextLine();

          System.out.println(
              "Which attribute would you like to update (nodeid, xcoord, ycoord, floor, building, "
                  + "nodetype, longname, shortname)");
          String attributeToUpdate = s1.nextLine().toLowerCase();
          stmt = c.createStatement();

          String newval = "";
          int newInt = 0;
          String sql = "";

          switch (attributeToUpdate) {
            case "nodeid":
              System.out.println("Please enter the new nodeid: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1nodes SET nodeid = '"
                      + newval
                      + "' WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
              stmt.executeUpdate(sql);
              stmt.close();
              break;

            case "xcoord":
              System.out.println("Please enter the new xcoord: ");
              newInt = s1.nextInt();
              sql =
                  "UPDATE l1nodes SET xcoord = "
                      + newInt
                      + " WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
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
              stmt.execute(sql);
              stmt.close();
              break;

            case "floor":
              System.out.println("Please enter the new floor: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1nodes SET floor = '"
                      + newval
                      + "' WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
              stmt.execute(sql);
              stmt.close();
              break;

            case "building":
              System.out.println("Please enter the new building: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1nodes SET building = '"
                      + newval
                      + "' WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
              stmt.execute(sql);
              stmt.close();
              break;

            case "nodetype":
              System.out.println("Please enter the new nodetype: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1nodes SET nodetype = '"
                      + newval
                      + "' WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
              stmt.executeUpdate(sql);
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
              stmt.executeUpdate(sql);
              stmt.close();
              break;

            case "shortname":
              System.out.println("Please enter the new shortname: ");
              newval = s1.nextLine();
              sql =
                  "UPDATE l1nodes SET shortname = '"
                      + newval
                      + "' WHERE nodeID = '"
                      + nodetoUpdate
                      + "';";
              stmt.executeUpdate(sql);
              stmt.close();
              break;

            default:
              System.out.println("Please enter a valid attribute to edit");
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
        } else if (tabletoUpdate.equals("Help Screen")) {
          this.help();
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

    System.out.println("Help Page:\n\n");
    boolean exit = false;
    Scanner s1 = new Scanner(System.in);

    // User Operations:
    System.out.println("\tUser Operations:\n");
    System.out.println("\t\tUser inputs username to database.");
    System.out.println("\t\tUser inputs password to database.");
    System.out.println(
        "\t\tUser inputs which operation they wish to use (delete, retreive, update, help)");

    System.out.println("\t\t\tIf delete:");
    System.out.println("\t\t\t\tUser inputs which tabel they wish to edit (nodes or edges).");
    System.out.println("\t\t\t\t\tIf nodes:");
    System.out.println("\t\t\t\t\t\tUser inputs the node ID they wish to delete.");
    System.out.println("\t\t\t\t\tIf edges:");
    System.out.println("\t\t\t\t\t\tUser inputs the edge ID they wish to delete.");

    System.out.println("\t\t\tIf retreive:");
    System.out.println("\t\t\t\tUser inputs which tabel they wish to edit (nodes or edges).");
    System.out.println("\t\t\t\t\tIf nodes:");
    System.out.println("\t\t\t\t\t\tUser inputs the node ID they wish to retreive.");
    System.out.println("\t\t\t\t\tIf edges:");
    System.out.println("\t\t\t\t\t\tUser inputs the edge ID they wish to retreive.");

    System.out.println("\t\t\tIf update:");
    System.out.println("\t\t\t\tUser inputs which tabel they wish to update (nodes or edges).");
    System.out.println("\t\t\t\t\tIf nodes:");
    System.out.println(
        "\t\t\t\t\t\tUser inputs the attribute they wish to update (nodeid, xcoord, ycoord, floor, building, nodetype, longname, shortname).");
    System.out.println("\t\t\t\t\t\t\tIf nodeid:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new node ID.");
    System.out.println("\t\t\t\t\t\t\tIf xcoord:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new xcoord.");
    System.out.println("\t\t\t\t\t\t\tIf ycoord:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new ycoord.");
    System.out.println("\t\t\t\t\t\t\tIf floor:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new floor.");
    System.out.println("\t\t\t\t\t\t\tIf building:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new building.");
    System.out.println("\t\t\t\t\t\t\tIf nodetype:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new node type.");
    System.out.println("\t\t\t\t\t\t\tIf longname:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new long name.");
    System.out.println("\t\t\t\t\t\t\tIf shortname:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new short name.");
    System.out.println("\t\t\t\t\tIf edges:");
    System.out.println(
        "\t\t\t\t\t\tUser inputs the attribute they wish to update (edgeID, startNode, endNode).");
    System.out.println("\t\t\t\t\t\t\tIf edgeID:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new edge ID.");
    System.out.println("\t\t\t\t\t\t\tIf startNode:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new start node.");
    System.out.println("\t\t\t\t\t\t\tIf endNode:");
    System.out.println("\t\t\t\t\t\t\t\tUser inputs the new end node.");

    System.out.println("\t\t\tIf help:");
    System.out.println("\t\t\t\tUser inputs exit when done looking at help screen (exit)");

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
    System.out.println("\t\tretreiveFromTable(Connection c)");
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
        "\t\t\t\t\tnodeToRetreive: Input the Node ID that the user wishes to retreive");
    System.out.println("\t\t\t\tIf edges:");
    System.out.println(
        "\t\t\t\t\tedgeToRetreive: Input the Edge ID that the user wishes to retreive");
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
        "\t\t\t\t\tattributeToUpdate: Input the attribute the user wishes to update (nodeid, xcoord, ycoord, floor, building, nodetype, longname, shortname)");
    System.out.println("\t\t\t\t\t\tIf nodeid:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new node ID");
    System.out.println("\t\t\t\t\t\tIf xcoord:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new xcoord");
    System.out.println("\t\t\t\t\t\tIf ycoord:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new ycoord");
    System.out.println("\t\t\t\t\t\tIf floor:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new floor");
    System.out.println("\t\t\t\t\t\tIf building:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new building");
    System.out.println("\t\t\t\t\t\tIf nodetype:");
    System.out.println("\t\t\t\t\t\t\tnewval: Input the new nodetype");
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
    while (!exit) {
      String response = s1.nextLine().toLowerCase();
      if (response.equals("exit")) {
        exit = true;
      }
    }
  }
}
