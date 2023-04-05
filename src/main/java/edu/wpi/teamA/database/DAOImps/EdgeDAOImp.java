package edu.wpi.teamA.database.DAOImps;

import edu.wpi.teamA.database.Connection.DBConnectionProvider;
import edu.wpi.teamA.database.Interfaces.IEdgeDAO;
import edu.wpi.teamA.database.ORMclasses.Edge;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EdgeDAOImp implements IDataBase, IEdgeDAO {
  ArrayList<Edge> EdgeArray;
  static DBConnectionProvider edgeProvider = new DBConnectionProvider();

  public EdgeDAOImp(ArrayList<Edge> EdgeArray) {
    this.EdgeArray = EdgeArray;
    // check if the table exist
    // if it exist, populate the array list
    // use select * to get all info from the table
    // create objects based off of the results
  }

  public static void createSchema() {
    try {
      Statement stmtSchema = edgeProvider.createConnection().createStatement();
      String sqlCreateSchema = "CREATE SCHEMA IF NOT EXISTS \"Prototype2_schema\"";
      stmtSchema.execute(sqlCreateSchema);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection createConnection() {
    String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamadb";
    String user = "teama";
    String password = "teama10";

    try {
      return DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static ArrayList<Edge> loadEdgesFromCSV(String filePath) {
    ArrayList<Edge> edges = new ArrayList<>();

    try {
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      csvReader.readLine(); // Skip the header line
      String row;

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        int startNode = Integer.parseInt(data[0]);
        int endNode = Integer.parseInt(data[1]);

        Edge edge = new Edge(startNode, endNode);
        edges.add(edge);
      }

      csvReader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return edges;
  }

  public EdgeDAOImp() {
    this.EdgeArray = new ArrayList<Edge>();
  }

  public static ArrayList<Edge> Import(String filePath) {
    EdgeDAOImp.createSchema();
    ArrayList<Edge> EdgeArray = loadEdgesFromCSV(filePath);

    try {
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      csvReader.readLine();
      String row;

      String sqlCreateEdge =
          "Create Table if not exists \"Prototype2_schema\".\"Edge\""
              + "(startNode   int,"
              + "endNode    int,"
              + "CONSTRAINT fk_startnode "
              + "FOREIGN KEY(startNode) "
              + "REFERENCES Prototype2_schema.Node(nodeid), "
              + "CONSTRAINT fk_endnode "
              + "FOREIGN KEY(endNode)"
              + "REFERENCES Prototype2_schema.Node(nodeid))";
      Statement stmtEdge = edgeProvider.createConnection().createStatement();
      stmtEdge.execute(sqlCreateEdge);

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        PreparedStatement ps =
            edgeProvider
                .createConnection()
                .prepareStatement("INSERT INTO \"Prototype2_schema\".\"Edge\" VALUES (?, ?)");
        ps.setInt(1, Integer.parseInt(data[0]));
        ps.setInt(2, Integer.parseInt(data[1]));
        ps.executeUpdate();
      }
      csvReader.close();
    } catch (SQLException | IOException e) {

      throw new RuntimeException(e);
    }

    return EdgeArray;
  }

  public static void Export(String folderExportPath) {
    try {
      String newFile = folderExportPath + "/Edfge.csv";
      Statement st = edgeProvider.createConnection().createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM \"Prototype2_schema\".\"Edge\"");

      FileWriter csvWriter = new FileWriter(newFile);
      csvWriter.append("startNode,endNode\n");

      while (rs.next()) {
        csvWriter.append((rs.getInt("startNode")) + (","));
        csvWriter.append((rs.getInt("endNode")) + ("\n"));
      }

      csvWriter.flush();
      csvWriter.close();

      System.out.println("Edge table exported to Edge.csv");

    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public ArrayList<Edge> loadEdgesFromDatabase() {
    ArrayList<Edge> edges = new ArrayList<>();

    try {
      Statement st = edgeProvider.createConnection().createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM \"Prototype2_schema\".\"Edge\"");

      while (rs.next()) {
        int startNode = rs.getInt("startNode");
        int endNode = rs.getInt("endNode");

        Edge edge = new Edge(startNode, endNode);
        edges.add(edge);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return edges;
  }

  @Override
  public void Add() {
    /** Insert new edge object to the existing edge table and the arraylist */
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter startNode and endNode:");
      int startNode = input.nextInt();
      int endNode = input.nextInt();

      PreparedStatement ps =
          edgeProvider
              .createConnection()
              .prepareStatement("INSERT INTO \"Prototype2_schema\".\"Edge\" VALUES (?, ?)");
      ps.setInt(1, startNode);
      ps.setInt(2, endNode);
      ps.executeUpdate();

      EdgeArray.add(new Edge(startNode, endNode));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void Delete() {
    /**
     * delete the edge when specified with a composite key (startNode+endNode) and in the arrayList
     */
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter the startNode and endNode to delete:");
      int startNode = input.nextInt();
      int endNode = input.nextInt();

      PreparedStatement ps =
          edgeProvider
              .createConnection()
              .prepareStatement(
                  "DELETE FROM \"Prototype2_schema\".\"Edge\" WHERE startNode = ? AND endNode = ?");
      ps.setInt(1, startNode);
      ps.setInt(2, endNode);
      ps.executeUpdate();

      EdgeArray.removeIf(Edge -> Edge.startNode == startNode && Edge.endNode == endNode);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void Update() {
    /**
     * update the edge startNode and endNode when specified with a composite key (startNode +
     * ednNode) and In the arrayList
     */
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter old startNode, old endNode, new startNode, and new endNode:");
      int oldStartNode = input.nextInt();
      int oldEndNode = input.nextInt();
      int newStartNode = input.nextInt();
      int newEndNode = input.nextInt();

      PreparedStatement ps =
          edgeProvider
              .createConnection()
              .prepareStatement(
                  "UPDATE Prototype2_schema.\"Edge\" SET startNode = ?, endNode = ? WHERE startNode = ? AND endNode = ?");
      ps.setInt(1, newStartNode);
      ps.setInt(2, newEndNode);
      ps.setInt(3, oldStartNode);
      ps.setInt(4, oldEndNode);
      ps.executeUpdate();

      EdgeArray.forEach(
          edge -> {
            if (edge.startNode == oldStartNode && edge.endNode == oldEndNode) {
              edge.startNode = newStartNode;
              edge.endNode = newEndNode;
            }
          });

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
