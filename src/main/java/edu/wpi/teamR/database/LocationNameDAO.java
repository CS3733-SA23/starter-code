package edu.wpi.teamR.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LocationNameDAO {
  private static LocationNameDAO instance;
  private ArrayList<LocationName> locationNames;
  private String username, password, tableName, schemaName, connectionURL;

  private LocationNameDAO(String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
    this.username = username;
    this.password = password;
    this.tableName = tableName;
    this.schemaName = schemaName;
    this.connectionURL = connectionURL;
    locationNames = new ArrayList<LocationName>();
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM "+schemaName+"."+tableName+";");
    while(resultSet.next()){
      String longName = resultSet.getString("longName");
      String shortName = resultSet.getString("shortName");
      String nodeType = resultSet.getString("nodeType");
      LocationName aLocationName = new LocationName(longName, shortName, nodeType);
      locationNames.add(aLocationName);
    }
    closeConnection(connection);
  }

  public static LocationNameDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
    if (LocationNameDAO.instance == null)
      LocationNameDAO.instance =
          new LocationNameDAO(username, password, tableName, schemaName, connectionURL);
    return LocationNameDAO.instance;
  }

  public static LocationNameDAO getInstance() {
    return LocationNameDAO.instance;
  }

  public ArrayList<LocationName> getLocationNames() {
    return locationNames;
  }

  public LocationName addLocationName(String longName, String shortName, String nodeType) throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    String sqlInsert = "INSERT INTO " + schemaName + "." + tableName + "(longName, shortName, nodeType) ";
    sqlInsert += "VALUES('" + longName + "','" + shortName + "','" + nodeType + "');";
    statement.executeUpdate(sqlInsert);
    LocationName aLocationName = new LocationName(longName, shortName, nodeType);
    locationNames.add(aLocationName);
    closeConnection(connection);
    return aLocationName;
  }

  public void deleteLocationNames(String longName, String shortName, String nodeType)
      throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    String sqlDelete;
    if (longName == null && shortName == null && nodeType == null) {
      sqlDelete = "DELETE FROM " + schemaName + "." + tableName + ";";
    } else {
      sqlDelete = "DELETE FROM " + schemaName + "."+tableName + " WHERE ";
      int count = 0;
      if (longName != null) {
        count++;
        sqlDelete += "longName ="+"'"+longName+"'";
      }
      if (shortName != null) {
        if(count == 0){
          sqlDelete+=" AND ";
        }
        count++;
        sqlDelete += "shortName = " + "'" + shortName + "'";
      }
      if (nodeType != null) {
        if(count == 0){
          sqlDelete+=" AND ";
        }
        sqlDelete += "nodeType = " + "'" + nodeType + "'";
      }
      sqlDelete += ";";
    }
    statement.executeUpdate(sqlDelete);
    closeConnection(connection);
    for (int i = 0; i < locationNames.size(); i++) {
      Boolean longNameCheck = longName == null || longName.equals(locationNames.get(i).getLongName());
      Boolean shortNameCheck = shortName == null || shortName.equals(locationNames.get(i).getShortName());
      Boolean nodeTypeCheck = nodeType == null || nodeType.equals(locationNames.get(i).getNodeType());
      if (longNameCheck && shortNameCheck && nodeTypeCheck) {
        locationNames.remove(i);
        i--;
      }
    }
  }

  public void modifyLocationNameByLongName(String longName, String shortName, String nodeType) throws SQLException, ClassNotFoundException, NotFoundException {
    int count = 0;
    for(LocationName theLocation : locationNames){
      if(theLocation.getLongName().equals(longName)){
        count++;
      }
    }
    if(count == 0){
      throw new NotFoundException();
    }
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    String sqlUpdate = "UPDATE " + schemaName + "." + tableName + " SET shortName = '" + shortName + "', nodeType = '" + nodeType + "' WHERE longName = '" + longName + "';";
    statement.executeUpdate(sqlUpdate);
    closeConnection(connection);
    LocationName aLocationName = selectLocationNames(longName, null, null).get(0);
    aLocationName.setShortName(shortName);
    aLocationName.setNodeType(nodeType);
  }
  public ArrayList<LocationName> selectLocationNames(String longName, String shortName, String nodeType) {
    ArrayList<LocationName> aList = new ArrayList<LocationName>();
    for (LocationName locationName : locationNames) {
      Boolean longNameCheck = longName == null || longName.equals(locationName.getLongName());
      Boolean shortNameCheck = shortName == null || shortName.equals(locationName.getShortName());
      Boolean nodeTypeCheck = nodeType == null || nodeType.equals(locationName.getNodeType());
      if (longNameCheck && shortNameCheck && nodeTypeCheck) {
        aList.add(locationName);
      }
    }
    return aList;
  }

  public void writeCSV(String outputFile) throws SQLException, IOException {
    FileWriter outputFileWriter = new FileWriter(outputFile);
    outputFileWriter.write("longName,shortName,nodeType\n");
    for (LocationName aLocationName : locationNames) {
      String aLongName = aLocationName.getLongName();
      String aShortName = aLocationName.getShortName();
      String aNodeType = aLocationName.getNodeType();
      outputFileWriter.write(String.format("%s,%s,%s\n",aLongName,aShortName,aNodeType));
    }
    outputFileWriter.flush();
    outputFileWriter.close();
  }

  public void readCSV(String inputFile) throws SQLException, ClassNotFoundException, FileNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    PreparedStatement sqlInsert = connection.prepareStatement("");
    String sqlFullCommand = "";
    ArrayList<LocationName> newLocationNames = new ArrayList<LocationName>();

    Scanner sc = new Scanner(new File(inputFile));
    sc.useDelimiter(",|\n|\r");
    sc.nextLine();
    while (sc.hasNextLine() && sc.hasNext()) {
      String longName = sc.next();
      String shortName = sc.next();
      String nodeType = sc.next();

      sqlInsert = connection.prepareStatement("INSERT INTO "+schemaName+"."+tableName+"(longname, shortname, nodetype) VALUES(?,?,?);");
      sqlInsert.setString(1, longName);
      sqlInsert.setString(2, shortName);
      sqlInsert.setString(3, nodeType);
      sqlFullCommand += sqlInsert+";";
      LocationName locationName = new LocationName(longName, shortName, nodeType);
      newLocationNames.add(locationName);
      sc.nextLine(); //ensure that the scanner moves to the next line before trying to parse
    }
    sqlFullCommand = "DELETE FROM "+schemaName+"."+tableName+";" + sqlFullCommand;
    statement.executeUpdate(sqlFullCommand);
    this.locationNames = newLocationNames;

    sc.close();
    closeConnection(connection);
  }

  private Connection createConnection() throws SQLException, ClassNotFoundException {
    Class.forName("org.postgresql.Driver");
    return DriverManager.getConnection(connectionURL, username, password);
  }

  private void closeConnection(Connection connection) throws SQLException {
    connection.close();
  }
}
