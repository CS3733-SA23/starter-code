package Database;

import edu.wpi.teame.map.LocationName;
import java.util.List;

public class LocationDAO<E> implements DAO<LocationName> {
  List<LocationName> locationNames;

  @Override
  public List<LocationName> get() {
    return null;
  }

  @Override
  public void update() {}

  @Override
  public void delete(LocationName obj) {}

  @Override
  public void add(LocationName obj) {}

  @Override
  public void importFromCSV(String filePath, String tableName) {}

  @Override
  public void exportFromCSV(String name, String filePath, String fileName) {}
}
