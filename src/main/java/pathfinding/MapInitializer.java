package pathfinding;

public class MapInitializer {
    public String nodeID;
    public String longName;
    public String date;

    public MapInitializer(String nodeID, String longName) {
        this.nodeID = nodeID;
        this.longName = longName;
    }

    public void setLocation(){
        HospitalNode node = HospitalNode.allNodes.get(nodeID);
        node.setName(LocationName.allLocations.get(longName));
    }
}
