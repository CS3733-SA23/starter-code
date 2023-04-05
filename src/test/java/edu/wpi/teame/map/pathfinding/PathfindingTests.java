package edu.wpi.teame.map.pathfinding;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.map.HospitalNode;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PathfindingTests {

  /** * Helper function to instantiate a basic graph */
  public void createTestGraph() {
    HospitalNode node1 = new HospitalNode("1", 0, 0);
    HospitalNode node2 = new HospitalNode("2", 2, 4);
    HospitalNode node3 = new HospitalNode("3", 5, 5);
    HospitalNode node4 = new HospitalNode("4", 7, 9);
    HospitalNode node5 = new HospitalNode("5", 10, 10);
    // Disconnected node
    HospitalNode node6 = new HospitalNode("6", 12, 12);
    HospitalNode.addEdge(node1, node2, 1);
    HospitalNode.addEdge(node2, node4, 3);
    HospitalNode.addEdge(node4, node5, 1);
    HospitalNode.addEdge(node1, node3, 5);
    HospitalNode.addEdge(node3, node5, 2);
  }

  /** Generic pathfinder tests */
  @Test
  public void testNodeIDNotFound() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path = pathfinder.findPath("0", "4");
    assertNull(path);
  }

  /** Tests Below this point are for BFS */
  @Test
  public void testBFSMiddleCase1() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path =
        pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("4"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("4"));
  }

  @Test
  public void testBFSMiddleCase2() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path =
        pathfinder.findPath(HospitalNode.allNodes.get("2"), HospitalNode.allNodes.get("3"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("3"));
  }

  @Test
  public void testBFSFailCase() {
    createTestGraph();
    AbstractPathfinder pathfinder = new BFSPathfinder();
    List<HospitalNode> path =
        pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("6"));
    assertNull(path);
  }

  /** Tests below this point are for the A* algorithm */
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
  public void testAStarMiddleCase1() {
    // Travels the path with more segments bc it is lighter weight
    createTestGraph();
    AbstractPathfinder pathfinder = new AStarPathfinder();
    List<HospitalNode> path =
        pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("5"));
    assertEquals(HospitalNode.allNodes.get("1"), path.get(0));
    assertEquals(HospitalNode.allNodes.get("2"), path.get(1));
    assertEquals(HospitalNode.allNodes.get("4"), path.get(2));
    assertEquals(HospitalNode.allNodes.get("5"), path.get(3));
  }

  @Test
  public void testAStarMiddleCase2() {
    // Travels shorter path bc lighter weights
    createTestGraph();
    AbstractPathfinder pathfinder = new AStarPathfinder();
    List<HospitalNode> path =
        pathfinder.findPath(HospitalNode.allNodes.get("4"), HospitalNode.allNodes.get("3"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("4"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("5"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("3"));
  }

  @Test
  public void testAStarEdgeCase() {
    // This is an edge case because both directions have an equal path weighting
    // Goes the path that is shorter via heuristic distance
    createTestGraph();
    AbstractPathfinder pathfinder = new AStarPathfinder();
    List<HospitalNode> path =
        pathfinder.findPath(HospitalNode.allNodes.get("2"), HospitalNode.allNodes.get("3"));
    assertEquals(path.get(0), HospitalNode.allNodes.get("2"));
    assertEquals(path.get(1), HospitalNode.allNodes.get("1"));
    assertEquals(path.get(2), HospitalNode.allNodes.get("3"));
  }

  @Test
  public void testAStarFailCase() {
    createTestGraph();
    AbstractPathfinder pathfinder = new AStarPathfinder();
    List<HospitalNode> path =
        pathfinder.findPath(HospitalNode.allNodes.get("1"), HospitalNode.allNodes.get("6"));
    assertNull(path);
  }
}
