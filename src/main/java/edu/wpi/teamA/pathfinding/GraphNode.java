package edu.wpi.teamA.pathfinding;

import edu.wpi.teamA.database.ORMclasses.Edge;
import java.util.ArrayList;

public class GraphNode {
  private int nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;
  private ArrayList<Edge> edgeList; // change to Edge
  private boolean visited;
  private GraphNode prev;
  private int gCost;
  private int hCost;

  public GraphNode(int nodeID, int xcoord, int ycoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.edgeList = new ArrayList<Edge>();
    this.visited = false;
    this.prev = this;
    this.gCost = -1;
    this.hCost = -1;
  }

  public void setNodeID(int nodeID) {
    this.nodeID = nodeID;
  }

  public int getXcoord() {
    return xcoord;
  }

  public void setXcoord(int xcoord) {
    this.xcoord = xcoord;
  }

  public int getYcoord() {
    return ycoord;
  }

  public void setYcoord(int ycoord) {
    this.ycoord = ycoord;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public void setEdgeList(ArrayList<Edge> edgeList) {
    this.edgeList = edgeList;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public GraphNode getPrev() {
    return prev;
  }

  public void setPrev(GraphNode prev) {
    this.prev = prev;
  }

  public int getgCost() {
    return gCost;
  }

  public void setgCost(int gCost) {
    this.gCost = gCost;
  }

  public int gethCost() {
    return hCost;
  }

  public void sethCost(int hCost) {
    this.hCost = hCost;
  }

  public int getfCost() {
    return gCost + hCost;
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

  public void reset() { // implement
    this.visited = false;
    this.prev = this;
    this.gCost = -1;
    this.hCost = -1;
  }

  // getters and setters for all attributes
  // get fCost: gCost+hCost
}
