package pathfinding;

import lombok.Getter;

public class LocationName {
    public enum NodeType{
        HALL,
        CONF,
        DEPT,
        INFO,
        SERV,
        LABS,
        RETL,
        STAI,
        ELEV,
        REST,
        BATH,
        EXIT
    }

    @Getter String longName;
    @Getter String shortName;
    @Getter NodeType nodeType;

    public LocationName(String longName, String shortName, NodeType nodeType) {
        this.longName = longName;
        this.shortName = shortName;
        this.nodeType = nodeType;
    }
}
