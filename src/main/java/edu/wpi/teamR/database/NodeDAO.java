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
    private NodeDAO(String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.connectionURL = connectionURL;

        nodes = new ArrayList<Node>();
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM "+schemaName+"."+tableName+";");
        while (resultSet.next()) {
            Integer nodeID = resultSet.getInt("nodeID");
            Integer xCoord = resultSet.getInt("xCoord");
            Integer yCoord = resultSet.getInt("yCoord");
            String floornum = resultSet.getString("floornum");
            String building = resultSet.getString("building");
            Node aNode = new Node(nodeID, xCoord, yCoord, floornum, building);
            nodes.add(aNode);
        }
    }

    public static NodeDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
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
        PreparedStatement sqlInsert = connection.prepareStatement("INSERT INTO "+schemaName+"."+tableName+"(nodeID,xCoord,yCoord,floorNum,building) VALUES(?,?,?,?,?);");
        sqlInsert.setInt(1, nodeID);
        sqlInsert.setInt(2, xCoord);
        sqlInsert.setInt(3, yCoord);
        sqlInsert.setString(4, floorNum);
        sqlInsert.setString(5, building);
        statement.executeUpdate(sqlInsert.toString());
        Node aNode = new Node(nodeID, xCoord, yCoord, floorNum, building);
        nodes.add(aNode);
        closeConnection(connection);
        return aNode;
    }

    /*
    Any number of these arguments can be null. This function will delete any row in the
    Node table that matches all non-null arguments.
    Ex: if all arguments are null then it will delete all nodes
    Ex2: if building is "45 Francis", floorNum is "L2", and everything else is null
         then it will delete all nodes in the L2 floor of the 45 Francis building
     */
    public void deleteNodes(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlDelete;
        if(nodeID == null && xCoord == null && yCoord == null && floorNum == null && building == null){
            sqlDelete = "DELETE FROM " + schemaName + "." + tableName + ";";
        } else{
            sqlDelete = "DELETE FROM " + schemaName + "." + tableName + " WHERE ";
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
                sqlDelete += "floorNum = " + "'" + floorNum+ "'";
            }
            if(building != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                sqlDelete += "building = " + "'" + building + "'";
            }
            sqlDelete += ";";
        }
        statement.executeUpdate(sqlDelete);
        closeConnection(connection);
        for(int i = 0; i<nodes.size(); i++){
            Boolean nodeIDCheck = nodeID == null || nodeID.equals(nodes.get(i).getNodeID());
            Boolean xCoordCheck = xCoord == null || xCoord.equals(nodes.get(i).getxCoord());
            Boolean yCoordCheck = yCoord == null || yCoord.equals(nodes.get(i).getyCoord());
            Boolean floorNumCheck = floorNum == null || floorNum.equals(nodes.get(i).getFloorNum());
            Boolean buildingCheck = building == null || building.equals(nodes.get(i).getBuilding());
            if(nodeIDCheck && xCoordCheck && yCoordCheck && floorNumCheck && buildingCheck){
                nodes.remove(i);
                i--;
            }
        }
    }

    /*
    This function will take the node with the selected nodeID and replace all
    attributes with the passed parameters
     */
    public void modifyNodeByID(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlUpdate = "UPDATE " + schemaName + "." + tableName + " SET xCoord = " + xCoord;
        sqlUpdate += ", yCoord = " + yCoord + ", floorNum = '" + floorNum+ "' , building = '" + building + "' WHERE nodeID = " + nodeID + ";";
        statement.executeUpdate(sqlUpdate);
        closeConnection(connection);
        Node aNode = selectNodes(nodeID, null, null, null, null).get(0);
        aNode.setBuilding(building);
        aNode.setFloorNum(floorNum);
        aNode.setxCoord(xCoord);
        aNode.setyCoord(yCoord);
    }


    /*
    Any number of these arguments can be null. This function will select any row in the
    Node table that matches all non-null arguments.
    Ex: if all arguments are null then it return a list of all nodes
    Ex2: if building is "45 Francis", floorNum is "L2", and everything else is null
         then it will return a list of all nodes in the L2 floor of the 45 Francis building
    */
    public ArrayList<Node> selectNodes(Integer nodeID, Integer xCoord, Integer yCoord, String floorNum, String building){
        ArrayList<Node> aList = new ArrayList<Node>();
        for (Node node : nodes) {
            Boolean nodeIDCheck = nodeID == null || nodeID.intValue() == node.getNodeID();
            Boolean xCoordCheck = xCoord == null || xCoord.intValue() == node.getxCoord();
            Boolean yCoordCheck = yCoord == null || yCoord.intValue() == node.getyCoord();
            Boolean floorNumCheck = floorNum == null || floorNum.equals(node.getFloorNum());
            Boolean buildingCheck = building == null || building.equals(node.getBuilding());
            if (nodeIDCheck && xCoordCheck && yCoordCheck && floorNumCheck && buildingCheck) {
                aList.add(node);
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
    public void readCSV(String filePath) throws FileNotFoundException, SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        PreparedStatement sqlInsert = connection.prepareStatement("");
        String sqlFullCommand = "";
        ArrayList<Node> newNodes = new ArrayList<Node>();

        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter(",|\n|\r");
        sc.nextLine();
        while (sc.hasNextLine() && sc.hasNext()) {
            int nodeID = sc.nextInt();
            int xCoord = sc.nextInt();
            int yCoord = sc.nextInt();
            String floorName = sc.next();
            String building = sc.next();

            sqlInsert = connection.prepareStatement("INSERT INTO "+schemaName+"."+tableName+"(nodeID,xCoord,yCoord,floorNum,building) VALUES(?,?,?,?,?);");
            sqlInsert.setInt(1, nodeID);
            sqlInsert.setInt(2, xCoord);
            sqlInsert.setInt(3, yCoord);
            sqlInsert.setString(4, floorName);
            sqlInsert.setString(5, building);
            sqlFullCommand += sqlInsert+";";
            Node aNode = new Node(nodeID, xCoord, yCoord, floorName, building);
            newNodes.add(aNode);
            sc.nextLine(); //ensure that the scanner moves to the next line before trying to parse
        }
        sqlFullCommand = "DELETE FROM "+schemaName+"."+tableName+";" + sqlFullCommand;
        statement.executeUpdate(sqlFullCommand);
        this.nodes = newNodes;

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
