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
    HashMap<HospitalNode, Integer> costToEndMap = new HashMap<HospitalNode, Integer>();
    costToEndMap = calculateFullCostMap(to, from);
    PriorityQueue<HospitalNode> queue =
        new PriorityQueue<HospitalNode>(
            new Comparator<HospitalNode>() {
              @Override
              public int compare(HospitalNode o1, HospitalNode o2) {
                // The value of a HospitalNode is based on it's cost from start + cost from end
                // (costToEndMap.get(o2) + o2.cost) - (costToEndMap.get(o1) + o1.cost)
                return o1.edgeCosts.get(o2) - o2.edgeCosts.get(o1);
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

  private HashMap<HospitalNode, Integer> calculateFullCostMap(HospitalNode from, HospitalNode to) {
    HashMap<HospitalNode, Integer> costMap = new HashMap<HospitalNode, Integer>();
    LinkedList<HospitalNode> queue = new LinkedList<HospitalNode>();
    HashSet<HospitalNode> visited = new HashSet<HospitalNode>();
    queue.add(from);
    costMap.put(from, 0);

    while (!queue.isEmpty()) {
      HospitalNode current = queue.remove();
      if (visited.contains(current)) {
        continue;
      } else {
        visited.add(current);
      }
      for (HospitalNode neighbor : current.getNeighbors()) {
        int neighborCost = costMap.get(current) + current.edgeCosts.get(neighbor);
        if (!costMap.containsKey(neighbor) || neighborCost < costMap.get(neighbor)) {
          queue.add(neighbor);
          costMap.put(neighbor, costMap.get(current) + current.edgeCosts.get(neighbor));
        }
      }
    }
    return costMap;
  }
}
