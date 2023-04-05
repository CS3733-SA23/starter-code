package edu.wpi.teamA.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EdgeDAOImp implements IDataBase, IEdgeDAO {
  ArrayList EdgeArray = new ArrayList<Edge>();
  Connection edgeConnection;

  public EdgeDAOImp(Connection edgeConnection, ArrayList<Edge> EdgeArray) {
    this.edgeConnection = edgeConnection;
    this.EdgeArray = EdgeArray;
  }

  public void Import() {
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

  @Override
  public void Export() {
    try {
      Statement st = edgeConnection.createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM Prototype2_schema.\"Edge\"");

      FileWriter csvWriter = new FileWriter("Edge.csv");
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
  public void Add() {}

  @Override
  public void Delete() {}

  @Override
  public void Update() {}
}
