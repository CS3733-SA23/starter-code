package edu.wpi.teamname;

public class Edge {
    private String edgeID;
    private Node startNode;
    private Node endNode;

    Edge(String edgeID, Node startNode, Node endNode){
        this.edgeID = edgeID;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public String getEdgeID() {
        return edgeID;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }
}
