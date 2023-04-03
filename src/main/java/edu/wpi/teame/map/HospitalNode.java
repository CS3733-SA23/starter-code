package edu.wpi.teame.map;

import static java.util.Objects.hash;

import java.util.*;
import lombok.Getter;

public class HospitalNode {
  public static HashMap<String, HospitalNode> allNodes = new HashMap<>();

  @Getter List<HospitalNode> neighbors;
  @Getter HashMap<HospitalNode, Integer> edgeCosts;

  @Getter String nodeID;

  @Getter int xCoord;
  @Getter int yCoord;
  @Getter Floor floor;
  @Getter String building;

  public HospitalNode(String id, int xCoord, int yCoord, Floor floor, String building) {
    this.neighbors = new LinkedList<HospitalNode>();
    edgeCosts = new HashMap<HospitalNode, Integer>();
    nodeID = id;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    // Add this node to the collection of all nodes
    allNodes.put(nodeID, this);
  }

  public HospitalNode() {
    this(allNodes.size() + "", 0, 0, Floor.LOWER_ONE, "Unknown");
  }

  public HospitalNode(String id) {
    this(id, 0, 0, Floor.LOWER_ONE, "Unknown");
  }

  public HospitalNode(String id, int xCoord, int yCoord) {
    this(id, xCoord, yCoord, Floor.LOWER_ONE, "Unknown");
  }

  public HospitalNode(NodeInitializer nodeInitializer) {
    this(
        nodeInitializer.nodeID,
        nodeInitializer.xCoord,
        nodeInitializer.yCoord,
        nodeInitializer.floor,
        nodeInitializer.building);
  }

  @Override
  public String toString() {
    return "Node " + nodeID;
  }

  @Override
  public int hashCode() {
    return hash(nodeID);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof HospitalNode) {
      return nodeID.equals(((HospitalNode) other).nodeID);
    }
    return false;
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

  public static void addEdge(HospitalNode node1, HospitalNode node2) {
    node1.neighbors.add(node2);
    node2.neighbors.add(node1);
  }

  public static void addEdge(String nodeId1, String nodeId2) {
    addEdge(allNodes.get(nodeId1), allNodes.get(nodeId2));
  }

  public static void addEdge(HospitalNode node1, HospitalNode node2, int edgeWeight) {
    node1.addNeighbor(node2, edgeWeight);
    node2.addNeighbor(node1, edgeWeight);
  }

  public static void addEdge(String nodeId1, String nodeId2, int edgeWeight) {
    addEdge(allNodes.get(nodeId1), allNodes.get(nodeId2), edgeWeight);
  }

  public static void processEdgeList(List<HospitalEdge> edgeList) {
    for (HospitalEdge edge : edgeList) {
      addEdge(allNodes.get(edge.nodeOneID), allNodes.get(edge.nodeTwoID), edge.edgeWeight);
    }
  }
}
