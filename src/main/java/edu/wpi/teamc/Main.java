package edu.wpi.teamc;

import edu.wpi.teamc.map.Graph;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Graph graph = new Graph();
    graph.syncWithDB();

    String startNode = "150";
    String endNode = "100";

    graph.printDirectionsAStar(startNode, endNode);

    // CApp.launch(CApp.class, args);
    // graph.printDirections("CCONF001L1", "CHALL010L1");
  }
}
