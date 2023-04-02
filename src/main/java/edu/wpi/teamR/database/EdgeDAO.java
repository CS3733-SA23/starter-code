package edu.wpi.teamR.database;
import java.sql.Connection;
import java.util.ArrayList;

public class EdgeDAO {
    private EdgeDAO instance;
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
        // TODO: Finish Implementation
        return null;
    }
    public EdgeDAO getInstance(String username, String password, String tableName, String schemaName, String connectionURL) {
        return instance;
    }
    public ArrayList<Edge> getEdges() {
        return edges;
    }
    public Edge addEdge(EDGE ATTRIBUTES){
        // TODO: Finish Implementation
    }
    public void deleteEdges(EDGE ATTRIBUTES) {
        // TODO: Finish Implementation
    }
    public ArrayList<Edge> selectEdges(EDGE ATTRIBUTES) {
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
