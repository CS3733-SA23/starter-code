package edu.wpi.teamA.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Edge {
  Integer startNode;
  Integer endNode;

  public Edge(Integer startNode, Integer endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  static void Import(Connection connection) {
    try {
      Scanner input = new Scanner(System.in);
      System.out.println("Please input the full qualified path of the file you want to import");
      String filePath = input.nextLine();
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      csvReader.readLine();
      String row;

      String sqlCreateEdge =
              "Create Table if not exists Prototype2_schema.Edge"
                      + "(startNode   int,"
                      + "endNode    int)";
      Statement stmtEdge = connection.createStatement();
      stmtEdge.execute(sqlCreateEdge);

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        PreparedStatement ps =
                connection.prepareStatement(
                        "INSERT INTO Prototype2_schema.\"Edge\" VALUES (?, ?)");
        ps.setInt(1, Integer.parseInt(data[0]));
        ps.setInt(2, Integer.parseInt(data[1]));
        ps.executeUpdate();
      }
      csvReader.close();
    } catch (SQLException | IOException e) {

      throw new RuntimeException(e);
    }
  }
}
