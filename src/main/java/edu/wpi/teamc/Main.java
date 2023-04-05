package edu.wpi.teamc;

import edu.wpi.teamc.map.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws ClassNotFoundException, SQLException {

    Scanner scanner = new Scanner(System.in);

    Graph graph = new Graph();
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

    // graph.printDirectionsAStar(startNode, endNode);
    Cdb.loadDatabaseTables(
        databaseNodeList, databaseEdgeList, databaseLocationNameList, databaseMoveList);
    Cdb.displayMoveInfo(databaseMoveList);

    CApp.launch(CApp.class, args);
    // graph.printDirections("CCONF001L1", "CHALL010L1");
  }
}
