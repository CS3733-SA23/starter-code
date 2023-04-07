package Database;

import edu.wpi.teame.map.HospitalNode;
import java.util.List;

public class NodeDAO<E> extends DAO<HospitalNode> {
  List<HospitalNode> nodeList;

  @Override
  public List<HospitalNode> get() {
    return null;
  }

  @Override
  public void update() {}

  @Override
  public void delete(HospitalNode obj) {}

  @Override
  public void add(HospitalNode obj) {}

  @Override
  public void importFromCSV(String filePath, String tableName) {}

}
