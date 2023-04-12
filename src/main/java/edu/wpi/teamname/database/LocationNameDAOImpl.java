package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.LocationNameDAO;
import edu.wpi.teamname.navigation.LocationName;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationNameDAOImpl implements LocationNameDAO {
  /** */
  @Override
  public void sync(LocationName locationName) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"LocationName\" SET \"shortName\" = ?, \"nodeType\" = ?, \"longName\" = ?"
              + " WHERE \"longName\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, locationName.getShortName());
      statement.setString(2, locationName.getNodeType());
      statement.setString(3, locationName.getLongName());
      statement.setString(4, locationName.getOriginalLongName());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }


  /** @return */
  @Override
  public ArrayList<LocationName> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<LocationName> list = new ArrayList<LocationName>();
    try {
      String query = "SELECT * FROM \"LocationName\"";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(query);

      while (rs.next()) {
        String longn = rs.getString("longName");
        String shortn = rs.getString("shortName");
        String ntype = rs.getString("nodeType");
        list.add(new LocationName(longn, shortn, ntype));
      }
    } catch (SQLException e) {
      System.out.println("Get all Location Names error.");
    }
    return list;
  }

  /** @param locationName */
  @Override
  public void add(LocationName locationName) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
        "INSERT INTO \"LocationName\" (\"longName\", \"shortName\", \"nodeType\") "
            + "VALUES (?, ?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, locationName.getLongName());
      statement.setString(2, locationName.getShortName());
      statement.setString(3, locationName.getNodeType());
      statement.executeUpdate();
      System.out.println("Location Name information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println(query);
      System.err.println("Error adding Location Name information to database: " + e.getMessage());
    }
  }

  /** @param locationName */
  @Override
  public void delete(LocationName locationName) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "Delete from \"LocationName\" where \"longName\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, locationName.getLongName());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Location Name table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("LocationName information deleted successfully.");
      else System.out.println("LocationName information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }
  /**
   * Uploads CSV data to a PostgreSQL database table "LocationName" also creates table if one does
   * not exist
   *
   * @param csvFilePath a String representing the csv data (must use "//" not "/")
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadLocationNameToPostgreSQL(String csvFilePath) throws SQLException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    DataManager dataImport = new DataManager();
    csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);

    try (connection) {
      String createTableQuery =
          "CREATE TABLE IF NOT EXISTS \"LocationName\" ("
              + "\"id\" SERIAL PRIMARY KEY,"
              + "\"longName\" varchar(255),"
              + "\"shortName\" varchar(255),"
              + "\"nodeType\" varchar(255)"
              + ");";
      PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
      createTableStatement.execute();

      String query =
          "INSERT INTO \"LocationName\" (\"longName\", \"shortName\", \"nodeType\") "
              + "VALUES (?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"LocationName\";");
      statement.executeUpdate();
      statement = connection.prepareStatement(query);

      for (int i = 1; i < csvData.size(); i++) {
        String[] row = csvData.get(i);
        statement.setString(1, row[0]); // longName is a string column
        statement.setString(2, row[1]); // shortName is a string column
        statement.setString(3, row[2]); // nodeType is a string column

        statement.executeUpdate();
      }
      System.out.println("CSV data uploaded to PostgreSQL database");
    } catch (SQLException e) {
      System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
    }
  }
  /**
   * Exports data from a PostgreSQL database table "LocationName" to a CSV file
   *
   * @param csvFilePath a String representing the csv data (must use "//" not "/")
   * @throws SQLException if an error occurs while exporting the data from the database
   * @throws IOException if an error occurs while writing the data to the file
   */
  public static void exportLocationNameToCSV(String csvFilePath) throws SQLException, IOException {
    List<String[]> csvData = new ArrayList<>();
    Connection connection = DataManager.DbConnection();

    try (connection) {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM \"LocationName\"");

      // add column headers
      ResultSetMetaData metaData = resultSet.getMetaData();
      int columnCount = metaData.getColumnCount();
      String[] headers = new String[columnCount];
      for (int i = 1; i <= columnCount; i++) {
        headers[i - 1] = metaData.getColumnName(i);
      }
      csvData.add(headers);

      // add data rows
      while (resultSet.next()) {
        String[] row = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
          Object value = resultSet.getObject(i);
          row[i - 1] = value == null ? "" : value.toString();
        }
        csvData.add(row);
      }

      // write data to CSV file
      FileWriter fileWriter = new FileWriter(csvFilePath);
      for (String[] row : csvData) {
        for (int i = 0; i < row.length; i++) {
          fileWriter.append("\"");
          fileWriter.append(row[i].replace("\"", "\"\""));
          fileWriter.append("\"");
          if (i < row.length - 1) {
            fileWriter.append(",");
          }
        }
        fileWriter.append("\n");
      }
      fileWriter.flush();
      fileWriter.close();

      System.out.println("Data exported from PostgreSQL database to CSV file");
    } catch (SQLException | IOException e) {
      System.err.println("Error exporting data from PostgreSQL database: " + e.getMessage());
      throw e;
    }
  }

  public static LocationName getLocationName(String name) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"LocationName\" WHERE \"longName\" = ?";
    LocationName locationName = null;
    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, name);
      ResultSet rs = statement.executeQuery();

      String longn = rs.getString("longName");
      String shortn = rs.getString("shortName");
      String type = rs.getString("nodeType");
      locationName = (new LocationName(longn, shortn, type));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return locationName;
  }

  /**
   * * Gets
   *
   * @return
   */
  public static ArrayList<String> getAllLongNames() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<String> list = new ArrayList<String>();

    try (connection) {
      String query = "SELECT * FROM \"LocationName\" ORDER BY \"longName\"";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        list.add(rs.getString("longName"));
      }
    }
    connection.close();
    return list;
  }
}
