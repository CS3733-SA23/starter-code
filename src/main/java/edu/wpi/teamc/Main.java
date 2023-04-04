package edu.wpi.teamc;

import edu.wpi.teamc.map.Graph;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Graph graph = new Graph();
    /*try {
      graph.init();
    } catch (IOException e) {
      System.out.println("Exception!");
    }*/
    graph.syncWithDB();

    String startNode = "305";
    String endNode = "105";

    graph.printDirectionsAStar(startNode, endNode);

    // CApp.launch(CApp.class, args);
    // graph.printDirections("CCONF001L1", "CHALL010L1");
  }
}
