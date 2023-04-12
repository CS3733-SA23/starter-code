package edu.wpi.teame.map.pathfinding;

import edu.wpi.teame.map.HospitalNode;
import java.util.List;

public abstract class AbstractPathfinder {
  /**
   * * Finds the path from the starting HospitalNode to the target HospitalNode * What algorithm is
   * used depends on which subclass is instantiated
   *
   * @param fromId the Id of the starting HospitalNode
   * @param toId the Id of the target HospitalNode
   * @return a list of coordinates representing the shortest path from the starting HospitalNode to
   *     the target HospitalNode
   */
  public List<HospitalNode> findPath(String fromId, String toId) {
    HospitalNode from = HospitalNode.allNodes.get(fromId);
    HospitalNode to = HospitalNode.allNodes.get(toId);
    if (from == null || to == null) {
      System.out.println("Pathfinder Error: One or both nodes are null");
      return null;
    }
    return findPath(from, to);
  }

  public abstract List<HospitalNode> findPath(HospitalNode from, HospitalNode to);
}
