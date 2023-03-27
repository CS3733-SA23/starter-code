package pathfinding;

public class HospitalEdge {
    String edgeID;
    String nodeOneID;
    String nodeTwoID;
    int edgeWeight;

    public HospitalEdge(String edgeID, String n1ID, String n2ID){
        this.edgeID     = edgeID;
        this.nodeOneID  = n1ID;
        this.nodeTwoID  = n2ID;
        this.edgeWeight = 1; // Default edge weight of 1
    }

    public HospitalEdge(String edgeID, String n1ID, String n2ID, int weight){
        this.edgeID     = edgeID;
        this.nodeOneID  = n1ID;
        this.nodeTwoID  = n2ID;
        this.edgeWeight = weight;
    }
}
