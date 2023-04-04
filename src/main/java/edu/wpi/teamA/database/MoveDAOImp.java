package edu.wpi.teamA.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveDAOImp implements IDataBase, IMoveDAO{

    ArrayList<Move> MoveArray = new ArrayList<Move>();

    Connection moveConnection;

    public MoveDAOImp(Connection moveConnection, ArrayList<Move> MoveArray){
        this.moveConnection = moveConnection;
        this.MoveArray = MoveArray;
    }

    @Override
    public void Import(String filePath) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Please input the full qualified path of the file you want to import");
            filePath = input.nextLine();
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            csvReader.readLine();
            String row;

            String sqlCreateEdge =
                    "Create Table if not exists Prototype2_schema.Node"
                            + "(nodeID   Int,"
                            + "LongName  Varchar(600),"
                            + "date      date)";
            Statement stmtMove = moveConnection.createStatement();
            stmtMove.execute(sqlCreateEdge);

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");

                PreparedStatement ps =
                        moveConnection.prepareStatement(
                                "INSERT INTO Prototype2_schema.\"Move\" VALUES (?, ?, ?)");
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setString(2, data[1]);
                ps.setString(3, data[2]);
                ps.executeUpdate();
            }
            csvReader.close();
        } catch (SQLException | IOException e) {

            throw new RuntimeException(e);
        }

    }

    @Override
    public void Export(String filePath) {
        try {
            Statement st = moveConnection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Prototype2_schema.\"Move\"");

            FileWriter csvWriter = new FileWriter("Move.csv");
            csvWriter.append("nodeID,longName,date\n");

            while (rs.next()) {
                csvWriter.append(rs.getInt("nodeID")+ ",");
                csvWriter.append(rs.getString("longName")).append(",");
                csvWriter.append(rs.getString("date")).append("\n");
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("Move table exported to Move.csv");

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * create a new instance of Move and Insert the new object into database
     */
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
