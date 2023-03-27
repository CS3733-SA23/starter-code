package pathfinding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HospitalNode {

    private List<HospitalNode> neighbors;
    private HashMap<HospitalNode, Integer> edgeCosts;

    private String nodeID;

    int xCoord;
    int yCoord;
    private String floor;
    private String building;

    private String nodeType;

    private String longName;
    private String shortName;


    public HospitalNode() {
        this.neighbors = new LinkedList<HospitalNode>();
        edgeCosts = new HashMap<HospitalNode, Integer>();
    }

    public HospitalNode addNeighbor(HospitalNode neighbor) {
        neighbors.add(neighbor);
        edgeCosts.put(neighbor, 1);
        return this;
    }

    public HospitalNode addNeighbor(HospitalNode neighbor, int cost) {
        neighbors.add(neighbor);
        edgeCosts.put(neighbor, cost);
        return this;
    }

    public List<HospitalNode> getNeighbors() {
        return neighbors;
    }

    public static void addEdge(HospitalNode node1, HospitalNode node2) {
        node1.neighbors.add(node2);
        node2.neighbors.add(node1);
    }




}
