package edu.wpi.teamc.map;

import lombok.Getter;

@Getter
public class Edge {
    private String id;
    private String startNode;
    private String endNode;

    public Edge(String id, String startNode, String endNode) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}
