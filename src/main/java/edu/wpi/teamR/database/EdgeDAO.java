package edu.wpi.teamR.database;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

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
    public static EdgeDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL){
        if (EdgeDAO.instance == null)
            EdgeDAO.instance = new EdgeDAO(username, password, tableName, schemaName, connectionURL);
        return EdgeDAO.instance;
    }
    public static EdgeDAO getInstance() {
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
    public ArrayList<Integer> getAdjacentNodeIDs(Integer nodeID) {
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        for (Edge edge : edges){
            if (edge.getStartNode() == nodeID)
                returnList.add(edge.getEndNode());
            else if (edge.getEndNode() == nodeID)
                returnList.add(edge.getStartNode());
        }
        return returnList;
    }
    public void deleteConnectingEdge(Integer nodeID1, Integer nodeIDA) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlDelete = "DELETE FROM "+schemaName+"."+tableName+" WHERE startNode="+nodeID1+" AND endNode="+nodeIDA;
        sqlDelete+=" OR startNode="+nodeIDA+" AND endNode="+nodeID1+";";
        for (int j = 0; j < edges.size(); j++){
            Edge edge = edges.get(j);
            if (edge.getStartNode()==nodeID1 && edge.getEndNode()==nodeIDA)
                edges.remove(j);
            else if (edge.getStartNode()==nodeIDA && edge.getEndNode()==nodeID1)
                edges.remove(j);
        }
    }
    public void deleteAllEdges(Integer nodeID) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        for (int j = 0; j < edges.size(); j++){
            Edge edge = edges.get(j);
            if (edge.getStartNode() == nodeID){
                String sqlDelete = "DELETE FROM "+schemaName+"."+tableName+" WHERE startNode="+nodeID+" AND endNode="+edge.getEndNode()+";";
                edges.remove(j);
            } else if (edge.getEndNode() == nodeID){
                String sqlDelete = "DELETE FROM "+schemaName+"."+tableName+" WHERE startNode="+edge.getStartNode()+" AND endNode="+nodeID+";";
                edges.remove(j);
            }
        }
    }
    public void writeCSV(String outputFile) throws IOException {
        File csvFile = new File(outputFile);
        FileWriter outputFileWriter = new FileWriter(csvFile);
        outputFileWriter.write("startNode,endNode");
        for(Edge aEdge : edges){
            String line = "\n";
            line+= aEdge.getStartNode() + ",";
            line+= aEdge.getEndNode();
            outputFileWriter.write(line);
        }
        outputFileWriter.flush();
        outputFileWriter.close();
    }
    public void readCSV(String readCSV){
        //TODO: Finish Implementation
    }
    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(connectionURL, username, password);
    }
    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
