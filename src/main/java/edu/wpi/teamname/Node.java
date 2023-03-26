package edu.wpi.teamname;

import javax.print.DocFlavor;

public class Node {
    private String nodeID;
    private int xCoord;
    private int yCoord;
    private String floor;
    private String building;
    private String nodeType;
    private String shortName;
    private String longName;
    private int locID;

    Node(String nodeID, int xCoord, int yCoord, String floor, String building, String nodeType, String shortName, String longName){
        this.nodeID = nodeID;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.shortName = shortName;
        this.longName = longName;

        String x = Integer.toString(xCoord);
        String y = Integer.toString(yCoord);
        String xy = x + y;
        this.locID = Integer.parseInt(xy);
    }

    public String getNodeID() {
        return nodeID;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public String getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public int getLocID(){
        return locID;
    }
}
