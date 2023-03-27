package pathfinding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HospitalNode {

    public static HashMap<String, HospitalNode> allNodes = new HashMap<>();
    private List<HospitalNode> neighbors;
    private HashMap<HospitalNode, Integer> edgeCosts;

    private String nodeID;

    int xCoord;
    int yCoord;
    String floor;
    String building;

    String nodeType;

    String longName;
    String shortName;


    public HospitalNode() {
        this.neighbors = new LinkedList<HospitalNode>();
        edgeCosts = new HashMap<HospitalNode, Integer>();
    }

    public HospitalNode(String id) {
        this.neighbors = new LinkedList<HospitalNode>();
        edgeCosts = new HashMap<HospitalNode, Integer>();
        nodeID = id;
        allNodes.put(id,this);
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
