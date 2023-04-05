package edu.wpi.teamA.database.DAOImps;

import edu.wpi.teamA.database.Connection.DBConnectionProvider;
import edu.wpi.teamA.database.Interfaces.ILocNameDAO;
import edu.wpi.teamA.database.ORMclasses.LocationName;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class LocNameDAOImp implements IDataBase, ILocNameDAO {

  ArrayList<LocationName> LocNameArray = new ArrayList<LocationName>();

  static DBConnectionProvider LocNameProvider = new DBConnectionProvider();

  public LocNameDAOImp(Connection nodeConnection) {
    this.LocNameArray = LocNameArray;
  }

  public static void Import(String filePath) {
    try {
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      csvReader.readLine();
      String row;

      String sqlCreateEdge =
          "Create Table if not exists Prototype2_schema.Node"
              + "(LongName   Varchar(600),"
              + "ShortName     Varchar(600),"
              + "NodeType  Varchar(600))";
      Statement stmtLocName = LocNameProvider.createConnection().createStatement();
      stmtLocName.execute(sqlCreateEdge);

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");

        PreparedStatement ps =
            LocNameProvider.createConnection()
                .prepareStatement(
                    "INSERT INTO Prototype2_schema.\"LocationName\" VALUES (?, ?, ?)");
        ps.setString(1, data[0]);
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
      Statement st = LocNameProvider.createConnection().createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM Prototype2_schema.\"LocationName\"");

      FileWriter csvWriter = new FileWriter("LocationName.csv");
      csvWriter.append("longName,shortName,nodeType\n");

      while (rs.next()) {
        csvWriter.append(rs.getString("longName")).append(",");
        csvWriter.append(rs.getString("shortName")).append(",");
        csvWriter.append(rs.getString("nodeType")).append("\n");
      }

      csvWriter.flush();
      csvWriter.close();

      System.out.println("Location Name table exported to LocationName.csv");

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
