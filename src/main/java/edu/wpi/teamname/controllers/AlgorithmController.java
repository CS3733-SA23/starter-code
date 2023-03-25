package edu.wpi.teamname.controllers;

import edu.wpi.teamname.entity.Node;
import java.util.ArrayList;

public class AlgorithmController {

    private ArrayList<Node> nodes;

    public AlgorithmController() {
        // Add all information from CSV files into the nodes ArrayList, including all edge relationships
        //TODO: parse CSV and stuff
    }

    /**
     * Takes in a starting node and ending node uses breadth first search to find the shortest path between them.
     *
     * @param startNodeID node to start the search
     * @param endNodeID node to search for
     * @return shortest path from the start to the end node
     */
    public ArrayList<Node> breadthfirstSearch(String startNodeID, String endNodeID) {
        return null;
        //TODO: Write search algorithm
    }
}
