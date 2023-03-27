package pathfinding;

import java.util.*;

public class AStarPathfinder implements IPathfinder {
  /**
   * * Finds the least cost path from the starting HospitalNode to the target HospitalNode using the A-star
   * algorithm
   *
   * @param from the starting HospitalNode
   * @param to the target HospitalNode
   * @return a list of coordinates representing the least cost path from the starting HospitalNode to the
   *     target HospitalNode
   */
  public List<HospitalNode> findPath(HospitalNode from, HospitalNode to) {
    PriorityQueue<HospitalNode> queue =
        new PriorityQueue<HospitalNode>(
            new Comparator<HospitalNode>() {
              @Override
              public int compare(HospitalNode o1, HospitalNode o2) {
                // Based on heuristic distance to target
                return 1;
              }
            });
    HashSet<HospitalNode> visited = new HashSet<HospitalNode>();
    HashMap<HospitalNode, HospitalNode> parentMap = new HashMap<HospitalNode, HospitalNode>();
    queue.add(from);
    parentMap.put(from, null);

    while (!queue.isEmpty()) {
      HospitalNode current = queue.remove();
      if (visited.contains(current)) {
        continue;
      } else {
        visited.add(current);
      }
      if (current.equals(to)) {
        return reconstructPath(parentMap, current);
      }
      for (HospitalNode neighbor : current.getNeighbors()) {
        if (!parentMap.containsKey(neighbor)) {
          queue.add(neighbor);
          parentMap.put(neighbor, current);
        }
      }
    }

    return null;
  }

  private int heuristicDistance(HospitalNode from, HospitalNode to) {
    return 0;
  }

  /**
   * * Reconstructs the path from the starting coordinate to the target coordinate
   *
   * @param parentMap a map of coordinates to their parent coordinates
   * @param current the target coordinate
   * @return a list of coordinates representing the shortest path from the starting coordinate to
   *     the target coordinate
   */
  private List<HospitalNode> reconstructPath(HashMap<HospitalNode, HospitalNode> parentMap, HospitalNode current) {
    LinkedList<HospitalNode> path = new LinkedList<HospitalNode>();
    while (current != null) {
      path.addFirst(current);
      current = parentMap.get(current);
    }
    return path;
  }
}
