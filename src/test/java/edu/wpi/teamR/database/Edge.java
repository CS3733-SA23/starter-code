package edu.wpi.teamR.database;

public class Edge {

    private Integer startNode, endNode;
    public Edge(Integer startNode, Integer endNode){
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Integer getStartNode() {
        return startNode;
    }

    public void setStartNode(Integer startNode) {
        this.startNode = startNode;
    }

    public Integer getEndNode() {
        return endNode;
    }

    public void setEndNode(Integer endNode) {
        this.endNode = endNode;
    }
}
