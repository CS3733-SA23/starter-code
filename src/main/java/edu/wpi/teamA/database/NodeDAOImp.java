package edu.wpi.teamA.database;

import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class NodeDAOImp implements IDataBase,INodeDAO {
    ArrayList<Node> NodeArray = new ArrayList<Node>();

    Connection nodeConnection;

    public NodeDAOImp(Connection nodeConnection, ArrayList<Node> NodeArray){
        this.nodeConnection = nodeConnection;
        this.NodeArray = NodeArray;
    }



    @Override
     public void Import() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Please input the full qualified path of the file you want to import");
            String filePath = input.nextLine();
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            csvReader.readLine();
            String row;

            String sqlCreateEdge =
                    "Create Table if not exists Prototype2_schema.Node"
                            + "(nodeID   int,"
                            + "xccord    int,"
                            + "ycoord    int,"
                            + "floor     Varchar(600),"
                            + "building  Varchar(600))";
            Statement stmtNode = nodeConnection.createStatement();
            stmtNode.execute(sqlCreateEdge);

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");

                PreparedStatement ps =
                        nodeConnection.prepareStatement(
                                "INSERT INTO Prototype2_schema.\"Node\" VALUES (?, ?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setInt(2, Integer.parseInt(data[1]));
                ps.setInt(3, Integer.parseInt(data[2]));
                ps.setString(4, data[3]);
                ps.setString(5, data[4]);
                ps.executeUpdate();
            }
            csvReader.close();
        } catch (SQLException | IOException e) {

            throw new RuntimeException(e);
        }

    }

    @Override
    public void Export() {
        try {
            Statement st = nodeConnection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Prototype2_schema.\"Node\"");

            FileWriter csvWriter = new FileWriter("Node.csv");
            csvWriter.append("nodeID,xcoord,ycoord,floor,building\n");

            while (rs.next()) {
                csvWriter.append((rs.getInt("nodeID")) + (","));
                csvWriter.append((rs.getInt("xcoord")) + (","));
                csvWriter.append((rs.getInt("ycoord"))+(","));
                csvWriter.append(rs.getString("floor")).append(",");
                csvWriter.append(rs.getString("building")).append("\n");
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("Node table exported to Node.csv");

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Add() {

    }

    @Override
    public void Delete() {

    }

    @Override
    public void Update() {

    }
}
