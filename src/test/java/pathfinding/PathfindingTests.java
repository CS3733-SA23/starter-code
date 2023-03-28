package pathfinding;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class PathfindingTests {

  public void createTestGraph() {

    HospitalNode node1 = new HospitalNode("1");
    HospitalNode node2 = new HospitalNode("2");
    HospitalNode node3 = new HospitalNode("3");
    HospitalNode node4 = new HospitalNode("4");
    HospitalNode.addEdge(node1, node2);
    HospitalNode.addEdge(node2, node3);
    HospitalNode.addEdge(node3, node4);
  }

  @Test
  public void heuristicTests() {
    HospitalNode node1 = new HospitalNode("1", 0, 0);
    HospitalNode node2 = new HospitalNode("2", 10, 0);
    HospitalNode node3 = new HospitalNode("3", 5, 5);
    AStarPathfinder pathfinder = new AStarPathfinder();
    assertEquals(10, pathfinder.heuristicDistance(node1, node2));
    assertEquals(7, pathfinder.heuristicDistance(node1, node3));
    assertEquals(0, pathfinder.heuristicDistance(node1, node1));
  }

  @Test
  public void sampleTest() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path =
        pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("4"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("4"));
  }
}
