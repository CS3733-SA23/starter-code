package edu.wpi.teamA.pathfinding;
import edu.wpi.teamA.database.Edge;
import edu.wpi.teamA.database.Node;
import java.util.ArrayList;
public class GraphNode {
    int nodeID;
    int xcoord;
    int ycoord;
    String floor;
    String building;
    String longName;
    String shortName;
    String nodeType;
    ArrayList<Edge> edgeList; //change to Edge
    boolean visited;
    GraphNode prev;
    int gCost;
    int hCost;
    int fCost;


    public GraphNode(int nodeID, int xcoord, int ycoord, String floor, String building, String longName, String shortName, String nodeType, ArrayList<Edge> edgeList, boolean visited, GraphNode prev) {
        this.nodeID = nodeID;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
        this.building = building;
        this.longName = longName;
        this.shortName = shortName;
        this.nodeType = nodeType;
        this.edgeList = edgeList;
        this.visited = visited;
        this.prev = prev;

    }

    public int getNodeID() {
        return nodeID;
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    public int edgeCount() {
        return edgeList.size();
    }

    public Edge getEdge(int index) {
        return edgeList.get(index);
    }


    //getters and setters for all attributes
    //get fCost: gCost+hCost
}
