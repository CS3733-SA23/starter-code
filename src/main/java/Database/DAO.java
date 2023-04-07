package Database;

import java.util.List;

public interface DAO<E> {

  List<E> get();

  void update();

  void delete(E obj);

  void add(E obj);

  void importFromCSV(String filePath, String tableName);

  void exportFromCSV(String name, String filePath, String fileName);
}
