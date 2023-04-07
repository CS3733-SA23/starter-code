package Database;

import edu.wpi.teame.map.HospitalNode;

import java.sql.Connection;
import java.util.List;

public class NodeDAO<E> implements DAO<HospitalNode> {
  List<HospitalNode> nodeList;

  Connection c;

  public NodeDAO(Connection c) {
    this.c = c;
  }

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

  @Override
  public void exportFromCSV(String name, String filePath, String fileName) {}
}
