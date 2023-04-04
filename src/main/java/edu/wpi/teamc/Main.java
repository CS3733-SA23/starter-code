package edu.wpi.teamc;

import edu.wpi.teamc.map.Graph;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Graph graph = new Graph();
    graph.syncWithDB();

    String startNode = "100";
    String endNode = "150";

    graph.printDirectionsAStar(startNode, endNode);

    // CApp.launch(CApp.class, args);
    // graph.printDirections("CCONF001L1", "CHALL010L1");
  }
}
