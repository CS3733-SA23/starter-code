package edu.wpi.teamc;

import edu.wpi.teamc.map.Graph;
import java.io.IOException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Graph graph = new Graph();
    try {
      graph.init();
    } catch (IOException e) {
      System.out.println("Exception!");
    }
    // graph.syncWithDB();

    String startNode = "CCONF001L1";
    String endNode = "CHALL010L1";

    graph.printDirectionsAStar(startNode, endNode);

    CApp.launch(CApp.class, args);
    // graph.printDirections("CCONF001L1", "CHALL010L1");
  }
}
