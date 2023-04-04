package edu.wpi.teamA.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NodeDAOImp implements IDataBase, INodeDAO {
  ArrayList<Node> NodeArray;

  Connection nodeConnection;

  public NodeDAOImp(Connection nodeConnection, ArrayList<Node> NodeArray) {
    this.nodeConnection = nodeConnection;
    this.NodeArray = NodeArray;
  }
  // ResultSet

  @Override
  public void Import(String filePath) {
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Please input the full qualified path of the file you want to import");
      filePath = input.nextLine();
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      csvReader.readLine();
      String row;

      String sqlCreateEdge =
          "Create Table if not exists Prototype2_schema.Node"
              + "(nodeID   int,"
              + "xccord    int,"
              + "ycoord    int,"
              + "floor     Varchar(600),"
              + "building  Varchar(600))";
      Statement stmtNode = nodeConnection.createStatement();
      stmtNode.execute(sqlCreateEdge);

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        PreparedStatement ps =
            nodeConnection.prepareStatement(
                "INSERT INTO Prototype2_schema.\"Node\" VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, Integer.parseInt(data[0]));
        ps.setInt(2, Integer.parseInt(data[1]));
        ps.setInt(3, Integer.parseInt(data[2]));
        ps.setString(4, data[3]);
        ps.setString(5, data[4]);
        ps.executeUpdate();
      }
      csvReader.close();
    } catch (SQLException | IOException e) {

      throw new RuntimeException(e);
    }
  }

  @Override
  public void Export(String folderExportPath) {
    try {
      String newFile = folderExportPath + "/Node.csv";
      Statement st = nodeConnection.createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM Prototype2_schema.\"Node\"");

      FileWriter csvWriter = new FileWriter(newFile);

      csvWriter.append("nodeID,xcoord,ycoord,floor,building\n");

      while (rs.next()) {
        csvWriter.append((rs.getInt("nodeID")) + (","));
        csvWriter.append((rs.getInt("xcoord")) + (","));
        csvWriter.append((rs.getInt("ycoord")) + (","));
        csvWriter.append(rs.getString("floor")).append(",");
        csvWriter.append(rs.getString("building")).append("\n");
      }

      csvWriter.flush();
      csvWriter.close();

      System.out.println("Node table exported to Node.csv");

    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void Add() {
    /** Insert new node object to the existing node table */
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter nodeID, xcoord, ycoord, floor, and building:");
      int nodeID = input.nextInt();
      int xcoord = input.nextInt();
      int ycoord = input.nextInt();
      String floor = input.next();
      String building = input.next();

      PreparedStatement ps =
          nodeConnection.prepareStatement(
              "INSERT INTO Prototype2_schema.\"Node\" VALUES (?, ?, ?, ?, ?)");
      ps.setInt(1, nodeID);
      ps.setInt(2, xcoord);
      ps.setInt(3, ycoord);
      ps.setString(4, floor);
      ps.setString(5, building);
      ps.executeUpdate();

      NodeArray.add(new Node(nodeID, xcoord, ycoord, floor, building));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void Delete() {
    /** delete one of the node according to the nodeID, also delete the node from the arraylist */
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter the nodeID to delete:");
      int nodeID = input.nextInt();

      PreparedStatement ps =
          nodeConnection.prepareStatement(
              "DELETE FROM Prototype2_schema.\"Node\" WHERE nodeID = ?");
      ps.setInt(1, nodeID);
      ps.executeUpdate();

      NodeArray.removeIf(node -> node.nodeID.equals(nodeID));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void Update() {
    /** update the node fields in the database and arraylist according to the inserts */
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter nodeID, new xcoord, new ycoord, new floor, and new building:");
      int nodeID = input.nextInt();
      int xcoord = input.nextInt();
      int ycoord = input.nextInt();
      String floor = input.next();
      String building = input.next();

      PreparedStatement ps =
          nodeConnection.prepareStatement(
              "UPDATE Prototype2_schema.\"Node\" SET xcoord = ?, ycoord = ?, floor = ?, building = ? WHERE nodeID = ?");
      ps.setInt(1, xcoord);
      ps.setInt(2, ycoord);
      ps.setString(3, floor);
      ps.setString(4, building);
      ps.setInt(5, nodeID);
      ps.executeUpdate();

      NodeArray.forEach(
          node -> {
            if (node.nodeID.equals(nodeID)) {
              node.xccord = xcoord;
              node.ycoord = ycoord;
              node.floor = floor;
              node.building = building;
            }
          });

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
