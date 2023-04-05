package edu.wpi.teamA.database.DAOImps;

import edu.wpi.teamA.database.Connection.DBConnectionProvider;
import edu.wpi.teamA.database.Interfaces.IMoveDAO;
import edu.wpi.teamA.database.ORMclasses.Move;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;

public class MoveDAOImp implements IDataBase, IMoveDAO {

  @Getter @Setter private static ArrayList<Move> MoveArray = new ArrayList<Move>();

  static DBConnectionProvider moveProvider = new DBConnectionProvider();

  public MoveDAOImp(ArrayList<Move> MoveArray) {
    this.MoveArray = MoveArray;
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
          "Create Table if not exists \"Prototype2_schema.Node\""
              + "(nodeID   int PRIMARY KEY,"
              + "LongName  Varchar(600),"
              + "date      date)";
      Statement stmtMove = moveProvider.createConnection().createStatement();
      stmtMove.execute(sqlCreateEdge);

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        PreparedStatement ps =
            moveProvider
                .createConnection()
                .prepareStatement("INSERT INTO Prototype2_schema.\"Move\" VALUES (?, ?, ?)");
        ps.setInt(1, Integer.parseInt(data[0]));
        ps.setString(2, data[1]);
        ps.setString(3, data[2]);
        ps.executeUpdate();
      }
      csvReader.close();
    } catch (SQLException | IOException e) {

      throw new RuntimeException(e);
    }
  }

  public static void Export(String filePath) {
    try {
      Statement st = moveProvider.createConnection().createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM Prototype2_schema.\"Move\"");

      FileWriter csvWriter = new FileWriter("Move.csv");
      csvWriter.append("nodeID,longName,date\n");

      while (rs.next()) {
        csvWriter.append(rs.getInt("nodeID") + ",");
        csvWriter.append(rs.getString("longName")).append(",");
        csvWriter.append(rs.getString("date")).append("\n");
      }

      csvWriter.flush();
      csvWriter.close();

      System.out.println("Move table exported to Move.csv");

    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** create a new instance of Move and Insert the new object into database */
  @Override
  public void Add() {}

  @Override
  public void Delete() {}

  @Override
  public void Update() {}
}
