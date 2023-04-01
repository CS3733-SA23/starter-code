package edu.wpi.teamA.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Node {
  Integer nodeID;

  Integer xccord;

  Integer ycoord;

  String floor;

  String building;

  public Node(Integer nodeID, Integer xccord, Integer ycoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xccord = xccord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }

  static void ImportNode(Connection connection) {
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Please input the full qualified path of the file you want to import");
      String filePath = input.nextLine();
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
      Statement stmtNode = connection.createStatement();
      stmtNode.execute(sqlCreateEdge);

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        PreparedStatement ps =
            connection.prepareStatement(
                "INSERT INTO Prototype2_schema.\"Node\" VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, Integer.parseInt(data[0]));
        ps.setInt(2, Integer.parseInt(data[1]));
        ps.setInt(3, Integer.parseInt(data[2]));
        ps.setString(4, data[3]);
        ps.setString(5, data[4]);
        ps.executeUpdate();
      }
      csvReader.close();
      System.out.println("CSV file imported successfully");
    } catch (SQLException | IOException e) {

      throw new RuntimeException(e);
    }
  }
}
