package edu.wpi.teamR.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeDAO {
    private static NodeDAO instance;
    private ArrayList<Node> nodes;
    private String username, password, tableName, schemaName, connectionURL;
    private NodeDAO(String username, String password, String tableName, String schemaName, String connectionURL){
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.connectionURL = connectionURL;
    }

    public NodeDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL) {
        if (NodeDAO.instance == null)
            NodeDAO.instance = new NodeDAO(username, password, tableName, schemaName, connectionURL);
        return NodeDAO.instance;
    }
    public NodeDAO getInstance(){
        return NodeDAO.instance;
    }
    public ArrayList<Node> getNodes(){
        return nodes;
    }
    public Node addNode(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building) throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlInsert = "INSERT INTO "+schemaName+"."+tableName+"(nodeID,xCoord,yCoord,floorNum,building) ";
        sqlInsert+= "VALUES("+nodeID+","+xCoord+","+yCoord+",\'"+floorNum+"\',\'"+building+"\');";
        statement.executeUpdate(sqlInsert);
    }
    public void deleteNodes(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building){ //TODO

    }
    public void modifyNodeByID(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building){ //TODO:

    }
    public void writeCSV(String outputFile){ //TODO:

    }
    public void readCSV(String inputFile){ //TODO:
        List<String> requiredColumns = Arrays.asList();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String headerRow = reader.readLine();
            List<String> columns = Arrays.asList(headerRow.split(","));

            if (!columns.containsAll(requiredColumns)) {
                // Prompt the user to enter the correct column order
            }
                String dataRow;
                while ((dataRow = reader.readLine()) != null) {
                    String[] dataValues = dataRow.split(",");
                    // Map the columns to the correct order
                    String[] mappedRow = new String[columnOrder.length];
                    for (int i = 0; i < columnOrder.length; i++) {
                        int index = columnOrder[i];
                        mappedRow[i] = dataValues[index];
                    }
                    // Insert the data into the database
                    // ...
                }
            }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL, username, password);
    }

    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
