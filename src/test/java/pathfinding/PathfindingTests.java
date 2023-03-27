package pathfinding;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class PathfindingTests {

    public void createTestGraph(){

    }

    @Test
    public void sampleTest(){
        IPathfinder pathfinder = new BFSPathfinder();
        List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("4"));
        assertEquals(path.get(0),HospitalNode.allNodes.get("1"));
    }
}
