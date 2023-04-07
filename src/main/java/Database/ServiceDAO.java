package Database;

import edu.wpi.teame.entities.ServiceRequestData;
import java.util.List;

public class ServiceDAO<E> implements DAO<ServiceRequestData> {
  List<ServiceRequestData> serviceRequestDataList;

  @Override
  List<ServiceRequestData> get() {
    return null;
  }

  @Override
  void update() {}

  @Override
  void delete(ServiceRequestData obj) {}

  @Override
  void add(ServiceRequestData obj) {}

  @Override
  void importFromCSV(String filePath, String tableName) {}

  @Override
  void exportFromCSV(String name, String filePath, String fileName) {}
}
