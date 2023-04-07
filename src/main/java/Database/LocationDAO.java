package Database;

import edu.wpi.teame.map.LocationName;
import java.util.List;

public class LocationDAO<E> extends DAO<LocationName> {
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
}
