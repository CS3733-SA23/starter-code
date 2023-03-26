package edu.wpi.teamname.controllers;

import edu.wpi.teamname.entity.Node;
import java.util.ArrayList;

public class AlgorithmController {

  private ArrayList<Node> nodes;

  public AlgorithmController() {
    // Add all information from CSV files into the nodes ArrayList, including all edge relationships
    // TODO: parse CSV and stuff
  }

  /**
   * Takes in a starting node and ending node uses breadth first search to find the shortest path
   * between them.
   *
   * @param startNodeLongName node to start the search
   * @param endNodeLongName node to search for
   * @return shortest path from the start to the end node
   */
  public ArrayList<Node> breadthFirstSearch(String startNodeLongName, String endNodeLongName) {
    return null;
    // TODO: Write search algorithm
  }

  /**
   * Checks the nodes in this class for a node with a given longName, returning true if one exists.
   *
   * @param longName name to be checked
   * @return whether the node exists in the map or not
   */
  public boolean nodeExists(String longName) {
    for (Node n : nodes) {
      if (longName.equals(n.getLongName())) return true;
    }
    return false;
  }
}
