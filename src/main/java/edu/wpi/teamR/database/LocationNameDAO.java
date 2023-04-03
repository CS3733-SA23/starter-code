package edu.wpi.teamR.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LocationNameDAO {
    private static LocationNameDAO instance;
    private ArrayList<LocationName> locationNames;
    private String username, password, tableName, schemaName, connectionURL;
    private LocationNameDAO(String username, String password, String tableName, String schemaName, String connectionURL){
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.connectionURL = connectionURL;
    }
    public static LocationNameDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL) {
        if (LocationNameDAO.instance == null)
            LocationNameDAO.instance = new LocationNameDAO(username, password, tableName, schemaName, connectionURL);
        return LocationNameDAO.instance;
    }
    public static LocationNameDAO getInstance(){
        return LocationNameDAO.instance;
    }
    public ArrayList<LocationName> getLocationNames(){
        return locationNames;
    }

    public LocationName addLocationName(String longName, String shortName, String nodeType) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlInsert = "INSERT INTO "+schemaName+"."+tableName+"(longName, shortName, nodeType) ";
        sqlInsert+= "VALUES(\'"+longName+"\',\'"+shortName+"\',\'"+nodeType+"\');";
        statement.executeUpdate(sqlInsert);
        LocationName aLocationName = new LocationName(longName, shortName, nodeType);
        locationNames.add(aLocationName);
        closeConnection(connection);
        return aLocationName;
    }
    public void deleteLocationNames(String longName, String shortName, String nodeType) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        if(longName == null && shortName == null && nodeType == null){
            String sqlDeleteALL = "DELETE FROM " + schemaName + "." + tableName + ";";
        } else{
            String sqlDelete = "DELETE FROM " + schemaName + "." + tableName + "WHERE longName = " + "\'" + longName+ "\'";
            int count = 0;
            if(longName != null){
                count++;
                sqlDelete += "longName = " + "\'" + longName+ "\'";
            }
            if(shortName != null){
                count++;
                sqlDelete += "shortName = " + "\'" + shortName+ "\'";
            }
            if(nodeType != null){
                count++;
                sqlDelete += "nodeType = " + "\'" + nodeType + "\'";
            }
            sqlDelete += ";";
            statement.executeUpdate(sqlDelete);
            closeConnection(connection);
        }
        for(int i = 0; i<locationNames.size(); i++){
            Boolean longNameCheck = longName == null || longName == locationNames.get(i).getLongName();
            Boolean shortNameCheck = shortName == null || shortName == locationNames.get(i).getShortName();
            Boolean nodeTypeCheck = nodeType == null || nodeType == locationNames.get(i).getNodeType();
            if(longNameCheck && shortNameCheck && nodeTypeCheck){
                locationNames.remove(i);
            }
        }
    }
    public void modifyLocationNameByID(String longName, String shortName, String nodeType) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlUpdate = "UPDATE " + schemaName + "." + tableName + " SET longName = \'" + longName +"\', \'"+ shortName+"\', \'"+nodeType+"\' WHERE longName = " + longName;
        statement.executeUpdate(sqlUpdate);
        closeConnection(connection);
        LocationName aLocationName = selectLocationNames(longName, null, null).get(0);
        aLocationName.setShortName(shortName);
        aLocationName.setNodeType(nodeType);
    }
    public ArrayList<LocationName> selectLocationNames(String longName, String shortName, String nodeType){
        ArrayList<LocationName> aList = new ArrayList<LocationName>();
        for(int i = 0; i<locationNames.size(); i++){
            Boolean longNameCheck = longName == null || longName == locationNames.get(i).getLongName();
            Boolean shortNameCheck = shortName == null || shortName == locationNames.get(i).getShortName();
            Boolean nodeTypeCheck = nodeType == null || nodeType == locationNames.get(i).getNodeType();
            if(longNameCheck && shortNameCheck && nodeTypeCheck){
                aList.add(locationNames.get(i));
            }
        }
        return aList;
    }
    public void writeCSV(String outputFile) throws SQLException, IOException {
        File csvFile = new File(outputFile);
        FileWriter outputFileWriter = new FileWriter(csvFile);
        outputFileWriter.write("longName,shortName,nodeType");
        for(LocationName aLocationName : locationNames){
            String line = "\n";
            line+= aLocationName.getLongName() + ",";
            line+= aLocationName.getShortName() + ",";
            line+= aLocationName.getNodeType();
            outputFileWriter.write(line);
        }
        outputFileWriter.flush();
        outputFileWriter.close();
    }
    public void readCSV(String inputFile){
    }
    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(connectionURL, username, password);
    }
    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
