package edu.wpi.teamA.database.DAOImps;

import edu.wpi.teamA.database.Connection.DBConnectionProvider;
import edu.wpi.teamA.database.Interfaces.IMoveDAO;
import edu.wpi.teamA.database.ORMclasses.Move;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class MoveDAOImp implements IDataBase, IMoveDAO {

  @Getter @Setter private static ArrayList<Move> MoveArray = new ArrayList<Move>();

  static DBConnectionProvider moveProvider = new DBConnectionProvider();

  public MoveDAOImp(ArrayList<Move> MoveArray) {
    this.MoveArray = MoveArray;
  }

  public static void createSchema() {
    try {
      Statement stmtSchema = moveProvider.createConnection().createStatement();
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

  public static ArrayList<Move> loadMovesFromCSV(String filePath) {
    ArrayList<Move> moves = new ArrayList<>();

    try {
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      csvReader.readLine(); // Skip the header line
      String row;

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        int nodeID = Integer.parseInt(data[0]);
        String longName = data[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate localDate = LocalDate.parse(data[2], formatter);

        Move move = new Move(nodeID, longName, localDate);
        moves.add(move);
      }

      csvReader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return moves;
  }

  public static ArrayList<Move> Import(String filePath) {
    MoveDAOImp.createSchema();
    ArrayList<Move> MoveArray = loadMovesFromCSV(filePath);

    try {
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      csvReader.readLine();
      String row;

      String sqlCreateEdge =
          "Create Table if not exists \"Prototype2_schema\".\"Move\""
              + "(nodeID   int PRIMARY KEY,"
              + "longName  Varchar(600),"
              + "localDate      date)";
      Statement stmtMove = moveProvider.createConnection().createStatement();
      stmtMove.execute(sqlCreateEdge);

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        PreparedStatement ps =
            moveProvider
                .createConnection()
                .prepareStatement("INSERT INTO \"Prototype2_schema\".\"Move\" VALUES (?, ?, ?)");
        ps.setInt(1, Integer.parseInt(data[0]));
        ps.setString(2, data[1]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate localDate = LocalDate.parse(data[2], formatter);
        ps.setDate(3, java.sql.Date.valueOf(localDate));
        ps.executeUpdate();
      }
      csvReader.close();
    } catch (SQLException | IOException e) {

      throw new RuntimeException(e);
    }
    return MoveArray;
  }

  public static void Export(String filePath) {
    try {
      Statement st = moveProvider.createConnection().createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM \"Prototype2_schema\".\"Move\"");

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
