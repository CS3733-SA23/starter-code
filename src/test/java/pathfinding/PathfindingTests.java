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
    HospitalNode node5 = new HospitalNode("5");
    HospitalNode.addEdge(node1, node2);
    HospitalNode.addEdge(node2, node3);
    HospitalNode.addEdge(node3, node4);
  }

  @Test
  public void sampleTest1() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("4"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("4"));
  }
    @Test
    public void sampleTest2() {
      createTestGraph();
      AbstractPathfinder pathfinder = new BFSPathfinder();
      List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("3"), HospitalNode.allNodes.get("4"));
      assertEquals(path.get(3), HospitalNode.allNodes.get("1"));
      assertEquals(path.get(1), HospitalNode.allNodes.get("2"));
      assertEquals(path.get(2), HospitalNode.allNodes.get("4"));
  }
  @Test
  public void sampleTest3() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("2"), HospitalNode.allNodes.get("3"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("3"));
  }
  @Test
  public void sampleTest4() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("5"));
    assertNull(path);
  }
}
