package Database;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseController {
  private Connection c;

  public static void main(String[] args) {
    Scanner s1 = new Scanner(System.in);

    System.out.print("Please enter your username (will default to \"teame\"): ");
    String username = s1.nextLine(); // Unused in this Prototype
    System.out.print("Please enter your password (will default to \"teame50\"): ");
    String password = s1.nextLine(); // Unused in this Prototype
    System.out.println();

    DatabaseController DBC1 = new DatabaseController("teame", "teame50");

    // DBC1.importFromCSV("C:\\Users\\thesm\\OneDrive\\Desktop\\Test.csv", "l1nodes");

    boolean exit = true;
    while (exit) {
      System.out.println("\nWhat would you like to do?");
      System.out.println(
          "Choices: update, retrieve, delete, display info, export table, import table, HELP, EXIT)");
      String function = s1.nextLine().toLowerCase().trim();

      switch (function) {
        case "update":
          // DBC1.updateTable();
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
          // DBC1.retrieveFromTable();
          break;

        case "display info":
          DBC1.displayCSVInfo();
          break;

        case "export table":
          DBC1.userExportToCSV();
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
    // this.retrieveFromTable();
  }

  public DatabaseController() {
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
    return c;
  }

  private void deleteFromTable() {
    Statement stmt = null;
    Scanner s1 = new Scanner(System.in);

    boolean donedeleting = true;
    while (donedeleting) {

      System.out.println("Which table would you like to delete from (Nodes, Edges): ");
      String tabletoEdit = s1.nextLine().toLowerCase().trim();

      if (tabletoEdit.equals("nodes")) {
        System.out.println("Please type the Node ID you would like to delete: ");
        String nodetoDelete = s1.nextLine();

        try {
          stmt = c.createStatement();
          String sql = "DELETE FROM teame.l1nodes WHERE nodeid = '" + nodetoDelete + "';";
          int rs = stmt.executeUpdate(sql);
          stmt.close();
          if (rs > 0) {
            System.out.println("Row Deleted successfully from " + tabletoEdit);

            System.out.println("Are you done deleting (y/n)?");
            String ans = s1.nextLine().toLowerCase().trim();
            if (ans.equals("y")) {
              donedeleting = false;
            }
          } else {
            System.out.println("Please enter a valid node id\n\n");
          }
        } catch (SQLException e) {
          System.out.println();
        }
      } else if (tabletoEdit.equals("edges")) {
        System.out.println("Please type the Edge ID you would like to delete: ");
        String edgetoDelete = s1.nextLine();
        try {
          stmt = c.createStatement();
          String sql = "DELETE FROM teame.l1edges WHERE edgeid = '" + edgetoDelete + "';";
          int rs = stmt.executeUpdate(sql);
          stmt.close();
          if (rs > 0) {
            System.out.println("Row Deleted successfully from " + tabletoEdit);

            System.out.println("Are you done deleting (y/n)?");
            String ans = s1.nextLine().toLowerCase().trim();
            if (ans.equals("y")) {
              donedeleting = false;
            }
          } else {
            System.out.println("Please enter a valid edge id\n\n");
          }
        } catch (SQLException e) {
          System.out.println();
        }

      } else {
        System.out.println("Please enter a valid table name (nodes, edges)");
      }
    }
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
              "\nNode: ("
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

          System.out.println("\nEdge (" + edgeId + ") information (edgeID, startNode, endNode): ");
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

  private void exitDatabaseProgram() {
    try {
      c.close();
      System.out.println("Database Connection Closed");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  // nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName
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

  private void userExportToCSV() {
    Scanner s1 = new Scanner(System.in);

    System.out.println("What table do you want to export?");
    String table = s1.nextLine();
    System.out.println("What is the filepath you wish to store this file?");
    String filepath = s1.nextLine();
    System.out.println("What is the name of the file you wish to create?");
    String fileName = s1.nextLine();

    try {
      this.exportToCSV(table, filepath, fileName);
      System.out.println("File Successfully Exported to Desired Location");
    } catch (SQLException e) {
      System.out.println("Sorry your table name isn't valid");
    } catch (IOException e) {
      System.out.println("Sorry Something Went Wrong. Try Checking your file path and retrying");
    }
  }

  private void exportToCSV(String name, String filePath, String fileName)
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

  public Connection getC() {
    return c;
  }
}
