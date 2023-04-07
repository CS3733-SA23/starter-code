package Database;

import edu.wpi.teame.entities.ServiceRequestData;
import java.util.List;

public class ServiceDAO<E> implements DAO<ServiceRequestData> {
  List<ServiceRequestData> serviceRequestDataList;

  @Override
  public List<ServiceRequestData> get() {
    return null;
  }

  @Override
  public void update() {}

  @Override
  public void delete(ServiceRequestData obj) {}

  @Override
  public void add(ServiceRequestData obj) {}

  @Override
  public void importFromCSV(String filePath, String tableName) {}

  @Override
  public void exportFromCSV(String name, String filePath, String fileName) {}
}
