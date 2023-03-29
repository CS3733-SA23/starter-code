package pathfinding;

public class HospitalEdge {
  String edgeID;
  String nodeOneID;
  String nodeTwoID;
  int edgeWeight;

  public HospitalEdge(String edgeID, String nodeOneID, String nodeTwoID) {
    this.edgeID = edgeID;
    this.nodeOneID = nodeOneID;
    this.nodeTwoID = nodeTwoID;
    this.edgeWeight = 1; // Default edge weight of 1
  }

  public HospitalEdge(String edgeID, String nodeOneID, String nodeTwoID, int weight) {
    this.edgeID = edgeID;
    this.nodeOneID = nodeOneID;
    this.nodeTwoID = nodeTwoID;
    this.edgeWeight = weight;
  }
}
