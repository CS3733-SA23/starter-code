package pathfinding;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class PathfindingTests {

  /***
   * Helper function to instantiate a basic graph
   */
  public void createTestGraph() {
    HospitalNode node1 = new HospitalNode("1",0,0);
    HospitalNode node2 = new HospitalNode("2",2,4);
    HospitalNode node3 = new HospitalNode("3",5,5);
    HospitalNode node4 = new HospitalNode("4",7,9);
    HospitalNode node5 = new HospitalNode("5",10,10);
    HospitalNode node6 = new HospitalNode("6",12,12);
    HospitalNode.addEdge(node1, node2, 1);
    HospitalNode.addEdge(node2, node4,3);
    HospitalNode.addEdge(node4, node5,1);
    HospitalNode.addEdge(node1, node3,5);
    HospitalNode.addEdge(node3, node5,2);
  }

  /**
   * Generic pathfinder tests
   */

  @Test
  public void testNodeIDNotFound() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("0"), HospitalNode.allNodes.get("4"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("4"));
  }

  /**
   * Tests Below this point are for BFS
   */

  @Test
  public void testBFSMiddleCase1() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("4"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("4"));
  }

  @Test
  public void testBFSMiddleCase2() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("2"), HospitalNode.allNodes.get("3"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("3"));
  }

  @Test
  public void testBFSFailCase() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("6"));
    assertNull(path);
  }

  /**
   * Tests below this point are for the A* algorithm
   */

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
  public void testAstarMiddleCase1() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("5"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("4"));
    assertEquals(path.get(3), HospitalNode.allNodes.get("5"));
  }

  @Test
  public void testAstarMiddleCase2() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("4"), HospitalNode.allNodes.get("3"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("5"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("3"));
  }
  @Test
  public void testAstarMiddleCase3() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("2"), HospitalNode.allNodes.get("5"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("4"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("5"));
  }
  @Test
  public void testAstarMiddleCase4() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("3"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("3"));
  }
  @Test
  public void testAstarMiddleCase5() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("2"), HospitalNode.allNodes.get("3"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("3"));

  @Test
  public void testAstarFailCase() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("6"));
    assertNull(path);
  }

}
