package Database;

import java.util.List;

public interface DAO<E> {

  public List<E> get();

  public void update();

  public void delete(E obj);

  public void add(E obj);

  public void importFromCSV(String filePath, String tableName);

  public void exportFromCSV(String name, String filePath, String fileName);
}
