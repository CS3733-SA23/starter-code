package edu.wpi.teamR.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveDAO {
  private static MoveDAO instance;
  private ArrayList<Move> moves;
  private String longName, username, password, tableName, schemaName, connectionURL;

  private MoveDAO(
      String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
    this.username = username;
    this.password = password;
    this.tableName = tableName;
    this.schemaName = schemaName;
    this.connectionURL = connectionURL;

    moves = new ArrayList<Move>();
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM "+schemaName+"."+tableName+";");
    while (resultSet.next()) {
      Integer nodeID = resultSet.getInt("nodeID");
      String longName = resultSet.getString("longname");
      Date moveDate = resultSet.getDate("moveDate");
      Move aMove = new Move(nodeID, longName, moveDate);
      moves.add(aMove);
    }
  }

  public static MoveDAO createInstance(
      String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
    if (MoveDAO.instance == null)
      MoveDAO.instance = new MoveDAO(username, password, tableName, schemaName, connectionURL);
    return MoveDAO.instance;
  }

  public static MoveDAO getInstance() {
    return MoveDAO.instance;
  }

  public ArrayList<Move> getMoves() {
    return moves;
  };

  public Move addMove(Integer nodeID, String longName, Date moveDate) throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    PreparedStatement sqlInsert = connection.prepareStatement("INSERT INTO " + schemaName + "." + tableName + "(nodeid,longName,moveDate) VALUES(?, ?, ?);");
    sqlInsert.setInt(1, nodeID);
    sqlInsert.setString(2, longName);
    java.sql.Date sqlDate = new java.sql.Date(moveDate.getTime());
    sqlInsert.setDate(3, sqlDate);
    Move aMove = new Move(nodeID, longName, moveDate);
    moves.add(aMove);
    closeConnection(connection);
    return aMove;
  }

  public void deleteMove(Integer nodeID, String longName, Date moveDate)
      throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    if (nodeID == null && longName == null && moveDate == null) {
      String sqlDeleteALL = "DELETE FROM " + schemaName + "." + tableName + ";";
      statement.executeUpdate(sqlDeleteALL);
    } else {
      String sqlDelete = "DELETE FROM " + schemaName + "." + tableName + " WHERE ";
      int count = 0;
      if (nodeID != null) {
        count++;
        sqlDelete += "nodeID = " + nodeID;
      }
      if (longName != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        count++;
        sqlDelete += "longName = " + "\'" + longName + "\'";
      }
      if (moveDate != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        sqlDelete += "moveDate =" + moveDate;
      }
      sqlDelete += ";";
      statement.executeUpdate(sqlDelete);
      closeConnection(connection);
    }
    for (int i = 0; i < moves.size(); i++) {
      Boolean nodeIDCheck = nodeID == null || nodeID.intValue() == moves.get(i).getNodeID();
      Boolean longNameCheck = longName == null || longName.equals(moves.get(i).getLongName());
      Boolean moveDateCheck = moveDate == null || moveDate == moves.get(i).getMoveDate();
      if (nodeIDCheck && longNameCheck && moveDateCheck) {
        moves.remove(i);
        i--;
      }
    }
    closeConnection(connection);
  }

  public void modifyMoveByID(Integer nodeID, String longName, Date moveDate) throws SQLException, ClassNotFoundException, NotFoundException {
    int count = 0;
    for(Move theMove : moves){
      if(theMove.getNodeID().intValue() == (nodeID)){
        count++;
      }
    }
    if(count == 0){
      throw new NotFoundException();
    }
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    PreparedStatement sqlInsert = connection.prepareStatement("UPDATE " + schemaName + "." + tableName + " SET longName = ?, moveDate = ? WHERE nodeID = " + nodeID);
    sqlInsert.setString(1, longName);
    java.sql.Date sqlDate = new java.sql.Date(moveDate.getTime());
    sqlInsert.setDate(2, sqlDate);
    closeConnection(connection);
    Move aMove = selectMoves(nodeID, null, null).get(0);
    aMove.setMoveDate(moveDate);
    aMove.setLongName(longName);
  }

  public ArrayList<Move> selectMoves(Integer nodeID, String longName, Date moveDate) {
    ArrayList<Move> aList = new ArrayList<Move>();
    for (Move move : moves) {
      Boolean nodeIDCheck = nodeID == null || nodeID.intValue() == move.getNodeID();
      Boolean longNameCheck = longName == null || longName.equals(move.getLongName());
      Boolean moveDateCheck = moveDate == null || moveDate.toString().equals(move.getMoveDate().toString());
      if (nodeIDCheck && longNameCheck && moveDateCheck) {
        aList.add(move);
      }
    }
    return aList;
  }

  public void writeCSV(String outputFile) throws SQLException, IOException {
    File csvFile = new File(outputFile);
    FileWriter outputFileWriter = new FileWriter(csvFile);
    outputFileWriter.write("nodeID,longName,moveDate");
    for (Move aMove : moves) {
      String line = "\n";
      line += aMove.getNodeID() + ",";
      line += aMove.getLongName() + ",";
      line += aMove.getMoveDate();
      outputFileWriter.write(line);
    }
    outputFileWriter.flush();
    outputFileWriter.close();
  }
  public void readCSV(String inputFile)
          throws FileNotFoundException, SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    PreparedStatement sqlInsert = connection.prepareStatement("");
    String sqlFullCommand = "";
    ArrayList<Move> newMoves = new ArrayList<Move>();

    Scanner sc = new Scanner(new File(inputFile));
    sc.useDelimiter(",|\n|\r");
    sc.nextLine();
    while (sc.hasNextLine() && sc.hasNext()) {
      int nodeID = sc.nextInt();
      String longName = sc.next();
      String[] dateString = sc.next().split("/");
      int month = Integer.parseInt(dateString[0]);
      int day = Integer.parseInt(dateString[1]);
      int year = Integer.parseInt(dateString[2]);
      Date moveDate = new Date(year, month, day);

      sqlInsert = connection.prepareStatement("INSERT INTO "+schemaName+"."+tableName+"(nodeid, longname, movedate) VALUES(?,?,?);");
      sqlInsert.setInt(1, nodeID);
      sqlInsert.setString(2, longName);
      sqlInsert.setDate(3, moveDate);
      sqlFullCommand += sqlInsert+";";
      Move move = new Move(nodeID, longName, moveDate);
      newMoves.add(move);
      sc.nextLine(); //ensure that the scanner moves to the next line before trying to parse
    }
    sqlFullCommand = "DELETE FROM "+schemaName+"."+tableName+";" + sqlFullCommand;
    statement.executeUpdate(sqlFullCommand);
    this.moves = newMoves;
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
