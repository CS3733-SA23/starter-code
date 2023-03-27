package edu.wpi.teamc;

import edu.wpi.teamc.map.Graph;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    // CApp.launch(CApp.class, args);
    Graph graph = new Graph();
    try {
      graph.init();
    } catch (IOException e) {

    }
  }
}
