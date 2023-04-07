package Database;

import edu.wpi.teame.map.HospitalEdge;
import java.util.List;

public class EdgeDAO<E> implements DAO<HospitalEdge> {
  List<HospitalEdge> hospitalEdgeList;

  @Override
  public List<HospitalEdge> get() {
    return null;
  }

  @Override
  public void update() {}

  @Override
  public void delete(HospitalEdge obj) {}

  @Override
  public void add(HospitalEdge obj) {}

  @Override
  public void importFromCSV(String filePath, String tableName) {}

  @Override
  public void exportFromCSV(String name, String filePath, String fileName) {}
}
