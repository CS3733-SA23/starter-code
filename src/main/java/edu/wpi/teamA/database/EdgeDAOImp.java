package edu.wpi.teamA.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EdgeDAOImp implements IDataBase, IEdgeDAO {
  ArrayList<Edge> edgeArray;
  static Connection edgeConnection;

  public EdgeDAOImp(Connection edgeConnection, ArrayList<Edge> EdgeArray) {
    this.edgeConnection = edgeConnection;
    this.edgeArray = EdgeArray;

    // check if the table exist
    // if it exist, populate the array list
    // use select * to get all info from the table
    // create objects based off of the results
    //

  }

  public static void Import(String filePath) {
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Please input the full qualified path of the file you want to import");
      filePath = input.nextLine();
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      csvReader.readLine();
      String row;

      String sqlCreateEdge =
          "Create Table if not exists Prototype2_schema.Edge"
              + "(startNode   int,"
              + "endNode    int)";
      Statement stmtEdge = edgeConnection.createStatement();
      stmtEdge.execute(sqlCreateEdge);

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        PreparedStatement ps =
            edgeConnection.prepareStatement("INSERT INTO Prototype2_schema.\"Edge\" VALUES (?, ?)");
        ps.setInt(1, Integer.parseInt(data[0]));
        ps.setInt(2, Integer.parseInt(data[1]));
        ps.executeUpdate();
      }
      csvReader.close();
    } catch (SQLException | IOException e) {

      throw new RuntimeException(e);
    }
  }

  public static void Export(String folderExportPath) {
    try {
      String newFile = folderExportPath + "/Edfge.csv";
      Statement st = edgeConnection.createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM Prototype2_schema.\"Edge\"");

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

  @Override
  public void Add() {
    /** Insert new edge object to the existing edge table and the arraylist */
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter startNode and endNode:");
      int startNode = input.nextInt();
      int endNode = input.nextInt();

      PreparedStatement ps =
          edgeConnection.prepareStatement("INSERT INTO Prototype2_schema.\"Edge\" VALUES (?, ?)");
      ps.setInt(1, startNode);
      ps.setInt(2, endNode);
      ps.executeUpdate();

      edgeArray.add(new Edge(startNode, endNode));

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
          edgeConnection.prepareStatement(
              "DELETE FROM Prototype2_schema.\"Edge\" WHERE startNode = ? AND endNode = ?");
      ps.setInt(1, startNode);
      ps.setInt(2, endNode);
      ps.executeUpdate();

      edgeArray.removeIf(Edge -> Edge.startNode == startNode && Edge.endNode == endNode);

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
          edgeConnection.prepareStatement(
              "UPDATE Prototype2_schema.\"Edge\" SET startNode = ?, endNode = ? WHERE startNode = ? AND endNode = ?");
      ps.setInt(1, newStartNode);
      ps.setInt(2, newEndNode);
      ps.setInt(3, oldStartNode);
      ps.setInt(4, oldEndNode);
      ps.executeUpdate();

      edgeArray.forEach(
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
