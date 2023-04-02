package edu.wpi.teamR.database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EdgeDAO {
    private static EdgeDAO instance;
    private ArrayList<Edge> edges;
    private String username, password, tableName, schemaName, connectionURL;
    private EdgeDAO(String username, String password, String tableName, String schemaName, String connectionURL){
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.connectionURL = connectionURL;
    }
    public EdgeDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL){
        if (EdgeDAO.instance == null)
            EdgeDAO.instance = new EdgeDAO(username, password, tableName, schemaName, connectionURL);
        return EdgeDAO.instance;
    }
    public EdgeDAO getInstance() {
        return EdgeDAO.instance;
    }
    public ArrayList<Edge> getEdges() {
        return edges;
    }
    public Edge addEdge(Integer startNode, Integer endNode) throws SQLException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlInsert = "INSERT INTO "+schemaName+"."+tableName+"(startNode, endNode) ";
        sqlInsert+= "VALUES("+startNode+","+endNode+");";
        statement.executeUpdate(sqlInsert);
        Edge aEdge = new Edge(startNode, endNode);
        edges.add(aEdge);
        closeConnection(connection);
        return aEdge;
    }
    public ArrayList<String> getAdjacentNodeIDs(String nodeID) {
        //TODO: Finish Implementation
    }
    public void deleteConnectingEdge(String nodeID1, String nodeIDA){
        //TODO: Finish Implementation
    }
    public void deleteAllEdges(String nodeID){
        //TODO: Finish Implementation
    }
    public void writeCSV(String outputFile){
        //TODO: Finish Implementation
    }
    public void readCSV(String readCSV){
        //TODO: Finish Implementation
    }
    private Connection createConnection() {
        //TODO: Finish Implementation
    }
    private void closeConnection(Connection connection) {
        //TODO: Finish Implementation
    }
}
