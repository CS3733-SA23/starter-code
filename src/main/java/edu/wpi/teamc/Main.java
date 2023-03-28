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

    System.out.println("What node would you like to start from?: ");
    String startNode = scanner.nextLine();
    System.out.println("What node would you like to end at?: ");
    String endNode = scanner.nextLine();

    graph.printDirections(startNode, endNode);

    // graph.printDirections("CCONF001L1", "CHALL010L1");
  }
}
