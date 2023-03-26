package edu.wpi.teamc.map;

public class Edge {
private String id;
    private Node endNode;
private int weight;

    public Edge(String id, Node endNode, int weight) {
        this.id = id;
        this.endNode = endNode;
        this.weight = weight;
    }

    //getters
    public String getId() {
        return id;
    }

    public Node getEndNode() {
        return endNode;
    }

    public int getWeight() {
        return weight;
    }

}
