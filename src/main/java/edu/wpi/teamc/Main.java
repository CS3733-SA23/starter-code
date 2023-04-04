package edu.wpi.teamc;

import edu.wpi.teamc.map.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  static Connection connection = null;

  public static void main(String[] args) throws ClassNotFoundException, SQLException {

    Scanner scanner = new Scanner(System.in);

    Graph graph = new Graph();
    // Load the PostgreSQL JDBC driver
    //    Class.forName("org.postgresql.Driver");
    //
    //    // Establish the connection
    //    String url = "jdbc:postgresql://database.cs.wpi.edu/teamcdb";
    //    String user = "teamc";
    //    String password = "teamc30";
    //    connection = DriverManager.getConnection(url, user, password);
    List<Node> databaseNodeList = new ArrayList<Node>();
    List<Edge> databaseEdgeList = new ArrayList<Edge>();
    List<LocationName> databaseLocationNameList = new ArrayList<LocationName>();
    List<Move> databaseMoveList = new ArrayList<Move>();
    try {
      graph.init();
    } catch (IOException e) {
      System.out.println("Exception!");
    }
    // graph.syncWithDB();

    String startNode = "CCONF001L1";
    String endNode = "CHALL010L1";

    graph.printDirectionsAStar(startNode, endNode);
//    Cdb.main(args);
    System.out.println("Cdb.main done");
    Cdb.loadDatabaseTables(
        databaseNodeList, databaseEdgeList, databaseLocationNameList, databaseMoveList);
    Cdb.displayMoveInfo(databaseMoveList);

    CApp.launch(CApp.class, args);
    // graph.printDirections("CCONF001L1", "CHALL010L1");
  }
}
