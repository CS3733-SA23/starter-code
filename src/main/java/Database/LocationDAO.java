package Database;

import edu.wpi.teame.map.LocationName;
import java.util.List;

public class LocationDAO<E> implements DAO<LocationName> {
  List<LocationName> locationNames;

  @Override
  List<LocationName> get() {
    return null;
  }

  @Override
  void update() {}

  @Override
  void delete(LocationName obj) {}

  @Override
  void add(LocationName obj) {}

  @Override
  void importFromCSV(String filePath, String tableName) {}

  @Override
  void exportFromCSV(String name, String filePath, String fileName) {}
}
