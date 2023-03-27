package edu.wpi.teamc;

import edu.wpi.teamc.map.Graph;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    Graph graph = new Graph();
    try {
      graph.init();
    } catch (IOException e) {
      System.out.println("Exception!");
    }

    graph.printDirections("CCONF001L1", "CHALL010L1");
  }
}
