package edu.wpi.teamc.map;

import java.util.LinkedList;
import java.util.List;

public class GraphNode extends Node {
    private List<GraphEdge> graphEdges;

    /**
     * Constructor for Node
     *
     * @param nodeID - ID of the node ex: CCONF001L1
     * @param xCoord - x coordinate of the node ex: 2255
     * @param yCoord - y coordinate of the node ex: 849
     * @param floor - floor of the node ex: L1
     * @param building - building of the node ex: CCONF
     */
    public GraphNode(
            String nodeID,
            int xCoord,
            int yCoord,
            String floor,
            String building) {
        super(nodeID, xCoord, yCoord, floor, building);
    }

    public void addEdge(String edgeID, GraphNode startNode, GraphNode endNode) {
        GraphEdge temp = new GraphEdge(edgeID, startNode, endNode);
        graphEdges.add(temp);
    }
}
