package edu.wpi.teamR.database;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    public static NodeDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL) {
        if (NodeDAO.instance == null)
            NodeDAO.instance = new NodeDAO(username, password, tableName, schemaName, connectionURL);
        return NodeDAO.instance;
    }
    public static NodeDAO getInstance(){
        return NodeDAO.instance;
    }
    public ArrayList<Node> getNodes(){
        return nodes;
    }
    public Node addNode(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlInsert = "INSERT INTO "+schemaName+"."+tableName+"(nodeID,xCoord,yCoord,floorNum,building) ";
        sqlInsert+= "VALUES("+nodeID+","+xCoord+","+yCoord+",\'"+floorNum+"\',\'"+building+"\');";
        statement.executeUpdate(sqlInsert);
        Node aNode = new Node(nodeID, xCoord, yCoord, floorNum, building);
        nodes.add(aNode);
        closeConnection(connection);
        return aNode;
    }
    public void deleteNodes(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        if(nodeID == null && xCoord == null && yCoord == null && floorNum == null && building == null){
            String sqlDeleteALL = "DELETE FROM " + schemaName + "." + tableName + ";";
        } else{
            String sqlDelete = "DELETE FROM " + schemaName + "." + tableName + "WHERE ";
            int count = 0;
            if(nodeID != null){
                count++;
                sqlDelete += "nodeID = " + nodeID;
            }
            if(xCoord != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                count++;
                    sqlDelete += "xCoord = " + xCoord;
            }
            if(yCoord != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                count++;
                sqlDelete += "yCoord = " + yCoord;
            }
            if(floorNum != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                count++;
                sqlDelete += "floorNum = " + "\'" + floorNum+ "\'";
            }
            if(building != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                sqlDelete += "building = " + "\'" + building+ "\'";
            }
            sqlDelete += ";";
            statement.executeUpdate(sqlDelete);
            closeConnection(connection);
        }
        for(int i = 0; i<nodes.size(); i++){
            Boolean nodeIDCheck = nodeID == null || nodeID == nodes.get(i).getNodeID();
            Boolean xCoordCheck = xCoord == null || xCoord == nodes.get(i).getxCoord();
            Boolean yCoordCheck = yCoord == null || yCoord == nodes.get(i).getyCoord();
            Boolean floorNumCheck = floorNum == null || floorNum == nodes.get(i).getFloorNum();
            Boolean buildingCheck = building == null || building == nodes.get(i).getBuilding();
            if(nodeIDCheck && xCoordCheck && yCoordCheck && floorNumCheck && buildingCheck){
                nodes.remove(i);
            }
        }
    }
    public void modifyNodeByID(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlUpdate = "UPDATE " + schemaName + "." + tableName + " SET xCoord = " + xCoord;
        sqlUpdate += ", yCoord = " + yCoord + ", floorNum = \'" + floorNum+ "\' , building = \'" + building + "\' WHERE nodeID = " + nodeID;
        statement.executeUpdate(sqlUpdate);
        closeConnection(connection);
        Node aNode = selectNodes(nodeID, null, null, null, null).get(0);
        aNode.setBuilding(building);
        aNode.setFloorNum(floorNum);
        aNode.setxCoord(xCoord);
        aNode.setyCoord(yCoord);
    }

    public ArrayList<Node> selectNodes(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building){
        ArrayList<Node> aList = new ArrayList<Node>();
        for(int i = 0; i<nodes.size(); i++){
            Boolean nodeIDCheck = nodeID == null || nodeID == nodes.get(i).getNodeID();
            Boolean xCoordCheck = xCoord == null || xCoord == nodes.get(i).getxCoord();
            Boolean yCoordCheck = yCoord == null || yCoord == nodes.get(i).getyCoord();
            Boolean floorNumCheck = floorNum == null || floorNum == nodes.get(i).getFloorNum();
            Boolean buildingCheck = building == null || building == nodes.get(i).getBuilding();
            if(nodeIDCheck && xCoordCheck && yCoordCheck && floorNumCheck && buildingCheck){
                aList.add(nodes.get(i));
            }
        }
        return aList;
    }

    public Node selectNodeByID(int nodeID) throws NotFoundException {
        for (Node node : nodes){
            if (node.getNodeID() == nodeID)
                return node;
        }
        throw new NotFoundException();
    }

    public void writeCSV(String outputFile) throws SQLException, IOException {
        File csvFile = new File(outputFile);
        FileWriter outputFileWriter = new FileWriter(csvFile);
        outputFileWriter.write("nodeID,xcoord,ycoord,floornum,building");
        for(Node aNode : nodes){
            String line = "\n";
            line+= aNode.getNodeID() + ",";
            line+= aNode.getxCoord() + ",";
            line+= aNode.getyCoord() + ",";
            line+= aNode.getFloorNum() + ",";
            line+= aNode.getBuilding();
            outputFileWriter.write(line);
        }
        outputFileWriter.flush();
        outputFileWriter.close();
    }
    public void readCSV(String filePath) throws FileNotFoundException, SQLException, ClassNotFoundException { //TODO:
        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter(",|\n");
        sc.nextLine();
        while (sc.hasNextLine() && sc.hasNext()) {
            int nodeID = sc.nextInt();
            int xCoord = sc.nextInt();
            int yCoord = sc.nextInt();
            String floorName = sc.next();
            String building = sc.next();
            addNode(nodeID, xCoord, yCoord, floorName, building);
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
