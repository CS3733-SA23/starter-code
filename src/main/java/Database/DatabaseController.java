package Database;

import java.sql.*;
import java.util.Scanner;
import pathfinding.HospitalNode;

public class DatabaseController {
  public static void main(String[] args) {
    DatabaseController DBC1 = new DatabaseController();
    Connection c = DBC1.connectToDatabase("teame", "teame50");
    //DBC1.retrieveFromTable(c);
    //DBC1.deleteFromTable(c);
    //DBC1.updateTable(c);
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
      System.out.println("Which table would you like to delete from (Nodes, Edges): ");
      String tabletoEdit = s1.nextLine().toLowerCase();

      if (tabletoEdit.equals("nodes")) {
        System.out.println("Please type the Node ID you would like to delete: ");
        String nodetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM teame.l1nodes WHERE nodeid = '" + nodetoDelete + "';";
        stmt.execute(sql);
        stmt.close();

      } else {
        System.out.println("Please type the Edge ID you would like to delete: ");
        String edgetoDelete = s1.nextLine();

        stmt = c.createStatement();
        String sql = "DELETE FROM teame.l1edges WHERE edgeid = '" + edgetoDelete + "'";
        stmt.execute(sql);
        stmt.close();
      }

      System.out.println("Row Deleted successfully from " + tabletoEdit);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  private void retrieveFromTable(Connection c) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Which table would you like to retrieve from (Nodes or Edges): ");
    String table = scanner.nextLine().toLowerCase().trim();

    if (table.equalsIgnoreCase("Nodes")) {
      System.out.print("Please type the Node ID you would like to retrieve: ");
      String nodeId = scanner.nextLine().toLowerCase().trim();

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
          System.out.println("Edge retrieved successfully: " + rs.getString("edgeid"));
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

  private void updateTable(Connection c) {

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

        } else {
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

  private void help() {}
}
