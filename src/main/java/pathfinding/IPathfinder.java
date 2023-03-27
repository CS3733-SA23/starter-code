package pathfinding;

import java.util.List;

public interface IPathfinder {
  public List<HospitalNode> findPath(HospitalNode from, HospitalNode to);
}
