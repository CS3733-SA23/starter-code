package edu.wpi.teamname.navigation;

import java.util.HashMap;

public class Map {
    HashMap<String, Node> nodes = new HashMap<>();

    /**
     * Empty Constructor for Map
     */
     Map(){}

    /**
     * Constructor for Map
     * @param nodes - HashMap of nodes
     */
     Map(HashMap<String, Node> nodes){
        this.nodes = nodes;
    }

    /**
     * Adds a node to the map
     * @param node - node to be added
     */
    public void addNode(Node node) {
        // check if node already exists
        if (nodes.containsKey(node.getNodeID())) {
            System.out.println("Node already exists");
            return;
        } else {
            nodes.put(node.getNodeID(), node);
        }
    }

    /**
     * Removes a node from the map
     * @param nodeID - ID of the node to be removed
     */
    public void removeNode(String nodeID) {
        // check if node exists
        if (!nodes.containsKey(nodeID)) {
            System.out.println("Node does not exist");
            return;
        } else {
            nodes.remove(nodeID);
        }
        for (Node node : nodes.values()) {
            node.removeConnectedNode(nodeID);
        }
    }




}
