package edu.wpi.teamR.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EdgeDAO {
  private static EdgeDAO instance;
  private ArrayList<Edge> edges;
  private String username, password, tableName, schemaName, connectionURL;

  private EdgeDAO(
          String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
    this.username = username;
    this.password = password;
    this.tableName = tableName;
    this.schemaName = schemaName;
    this.connectionURL = connectionURL;

    edges = new ArrayList<Edge>();
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM "+schemaName+"."+tableName+";");
    while(resultSet.next()){
        Integer startNode = resultSet.getInt("startNode");
        Integer endNode = resultSet.getInt("endNode");
        Edge aEdge = new Edge(startNode, endNode);
        edges.add(aEdge);
    }
    closeConnection(connection);
  }

  public static EdgeDAO createInstance(
      String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
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

  public Edge addEdge(Integer startNode, Integer endNode) throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    String sqlInsert = "INSERT INTO " + schemaName + "." + tableName + "(startNode, endNode) ";
    sqlInsert += "VALUES(" + startNode + "," + endNode + ");";
    statement.executeUpdate(sqlInsert);
    Edge aEdge = new Edge(startNode, endNode);
    edges.add(aEdge);
    closeConnection(connection);
      return aEdge;
  }

  public ArrayList<Integer> getAdjacentNodeIDs(Integer nodeID) {
    ArrayList<Integer> returnList = new ArrayList<Integer>();
    for (Edge edge : edges) {
      if (edge.getStartNode().intValue() == nodeID) {
          returnList.add(edge.getEndNode());
      }
      else if (edge.getEndNode().intValue() == nodeID) returnList.add(edge.getStartNode());
    }
    return returnList;
  }

  public void deleteConnectingEdge(Integer nodeID1, Integer nodeIDA)
      throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    String sqlDelete = "DELETE FROM " + schemaName + "." + tableName + " WHERE startNode=" + nodeID1 + " AND endNode=" + nodeIDA;
    sqlDelete += " OR startNode=" + nodeIDA + " AND endNode=" + nodeID1 + ";";
    statement.executeUpdate(sqlDelete);
    closeConnection(connection);
    for (int j = 0; j < edges.size(); j++) {
      Edge edge = edges.get(j);
      if (edge.getStartNode().intValue() == nodeID1 && edge.getEndNode().intValue() == nodeIDA){
          edges.remove(j);
          j--;
      }
      else if (edge.getStartNode().intValue() == nodeIDA && edge.getEndNode().intValue() == nodeID1) {
          edges.remove(j);
          j--;
      }
    }
  }

  public void deleteAllEdges(Integer nodeID) throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    for (int j = 0; j < edges.size(); j++) {
      Edge edge = edges.get(j);
      if (edge.getStartNode().intValue() == nodeID) {
        String sqlDelete =
            "DELETE FROM " + schemaName + "." + tableName + " WHERE startNode=" + nodeID + " AND endNode=" + edge.getEndNode() + ";";
        edges.remove(j);
        j--;
        statement.executeUpdate(sqlDelete);
      } else if (edge.getEndNode().intValue() == nodeID) {
        String sqlDelete =
            "DELETE FROM " + schemaName + "." + tableName + " WHERE startNode=" + edge.getStartNode() + " AND endNode=" + nodeID + ";";
        edges.remove(j);
        j--;
        statement.executeUpdate(sqlDelete);
      }
    }
    closeConnection(connection);
  }
  public void writeCSV(String outputFile) throws IOException {
    File csvFile = new File(outputFile);
    FileWriter outputFileWriter = new FileWriter(csvFile);
    outputFileWriter.write("startNode,endNode");
    for (Edge aEdge : edges) {
      String line = "\n";
      line += aEdge.getStartNode() + ",";
      line += aEdge.getEndNode();
      outputFileWriter.write(line);
    }
    outputFileWriter.flush();
    outputFileWriter.close();
  }
  public void readCSV(String filePath) throws FileNotFoundException, SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    PreparedStatement sqlInsert = connection.prepareStatement("");
    String sqlFullCommand = "";
    ArrayList<Edge> newEdges = new ArrayList<Edge>();

    Scanner sc = new Scanner(new File(filePath));
    sc.useDelimiter(",|\n|\r");
    sc.nextLine();
    while(sc.hasNextLine() && sc.hasNext()){
      int startNode = sc.nextInt();
      int endNode = sc.nextInt();

      sqlInsert = connection.prepareStatement("INSERT INTO "+schemaName+"."+tableName+"(startnode, endnode) VALUES(?,?);");
      sqlInsert.setInt(1, startNode);
      sqlInsert.setInt(2, endNode);
      sqlFullCommand += sqlInsert+";";
      Edge edge = new Edge(startNode, endNode);
      newEdges.add(edge);
      sc.nextLine(); //ensure that the scanner moves to the next line before trying to parse
    }
    sqlFullCommand = "DELETE FROM "+schemaName+"."+tableName+";" + sqlFullCommand;
    statement.executeUpdate(sqlFullCommand);
    this.edges = edges;
    sc.close();
    closeConnection(connection);
  }
  private Connection createConnection() throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    return DriverManager.getConnection(connectionURL, username, password);
  }
  private void closeConnection(Connection connection) throws SQLException {
    connection.close();
  }
}
