package edu.wpi.teamR.database;
import oracle.sql.DATE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

//TODO: string to date conversion, read and write CSV

public class MoveDAO {
    private static MoveDAO instance;
    private ArrayList<Move> moves;
    private String longName, username, password, tableName, schemaName, connectionURL;
    private MoveDAO(String username, String password, String tableName, String schemaName, String connectionURL){
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.connectionURL = connectionURL;
    }
    public static MoveDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL) {
        if (MoveDAO.instance == null)
            MoveDAO.instance = new MoveDAO(username, password, tableName, schemaName, connectionURL);
        return MoveDAO.instance;
    }
    public static MoveDAO getInstance(){return MoveDAO.instance;}
    public ArrayList<Move> getMoves(){return moves;};
    public Move addMove(int nodeID, String longName, Date moveDate) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlInsert = "INSERT INTO "+schemaName+"."+tableName+"(nodeID,longName,moveDate)";
        sqlInsert+= "VALUES("+nodeID+",\'"+longName+"\',"+moveDate+");";
        statement.executeUpdate(sqlInsert);
        Move aMove = new Move(nodeID, longName, moveDate);
        moves.add(aMove);
        closeConnection(connection);
        return aMove;
    }

    public void deleteMove(Integer nodeID, String longName, Date moveDate) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        if(nodeID == null && longName == null && moveDate == null){
            String sqlDeleteALL = "DELETE FROM " + schemaName + "." + tableName + ";";
        } else {
            String sqlDelete = "DELETE FROM " + schemaName + "." + tableName + "WHERE ";
            int count = 0;
            if(nodeID != null){
                count++;
                sqlDelete += "nodeID = " + nodeID;
            }
            if(longName != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                count++;
                sqlDelete += "longName = " + "\'" + longName+ "\'";
            }
            if(moveDate != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                count++;
                sqlDelete += "moveDate =" + moveDate; //TODO: moveDate could error here!
            }
            sqlDelete += ";";
            statement.executeUpdate(sqlDelete);
            closeConnection(connection);
        }
        for(int i = 0; i<moves.size(); i++){
            Boolean nodeIDCheck = nodeID == null || nodeID == moves.get(i).getNodeID();
            Boolean longNameCheck = longName == null || longName == moves.get(i).getLongName();
            Boolean moveDateCheck = moveDate == null || moveDate == moves.get(i).getMoveDate();
            if(nodeIDCheck && longNameCheck && moveDateCheck){
                moves.remove(i);
            }
        }
    }
    public void modifyMoveByID(Integer nodeID, String longName, Date moveDate) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlUpdate = "UPDATE " + schemaName + "." + tableName + " SET longName = \'" + longName + "\', moveDate = " + moveDate + "WHERE nodeID = " + nodeID;
        statement.executeUpdate(sqlUpdate);
        closeConnection(connection);
        Move aMove = selectMoves(nodeID, null, null).get(0);
        aMove.setMoveDate(moveDate);
        aMove.setLongName(longName);
    }

    public ArrayList<Move> selectMoves(Integer nodeID, String longName, Date moveDate){
        ArrayList<Move> aList = new ArrayList<Move>();
        for(int i = 0; i<moves.size(); i++){
            Boolean nodeIDCheck = nodeID == null || nodeID == moves.get(i).getNodeID();
            Boolean longNameCheck = longName == null || longName == moves.get(i).getLongName();
            Boolean moveDateCheck = moveDate == null || moveDate == moves.get(i).getMoveDate(); //TODO: moveDate could error here!
            if(nodeIDCheck && longNameCheck && moveDateCheck){
                aList.add(moves.get(i));
            }
        }
        return aList;
    }
    public void writeCSV(String outputFile) throws SQLException, IOException {
        File csvFile = new File(outputFile);
        FileWriter outputFileWriter = new FileWriter(csvFile);
        outputFileWriter.write("nodeID,longName,moveDate");
        for(Move aMove : moves){ //TODO: moveDate could error here!
            String line = "\n";
            line+= aMove.getNodeID() + ",";
            line+= aMove.getLongName() + ",";
            line+= aMove.getMoveDate();
            outputFileWriter.write(line);
        }
        outputFileWriter.flush();
        outputFileWriter.close();
    }
    public void readCSV(String inputFile) throws FileNotFoundException, SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(new File(inputFile));
        sc.useDelimiter(",|\n");
        sc.nextLine();
        while (sc.hasNextLine() && sc.hasNext()) {
            int nodeID = sc.nextInt();
            String longName = sc.next();
            Date moveDate = Date.valueOf(sc.next()); //TODO: check if this parsing works
            addMove(nodeID, longName, moveDate);
        }

        sc.close();
    }

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(connectionURL, username, password);
    }
    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
