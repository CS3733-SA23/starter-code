package Database;

import java.util.List;

public interface DAO {

    public List<?> get();

    public void update();

    public void delete(Object obj);

    public void add(Object obj);

    public void importFromCSV(String filePath, String tableName);
    public void exportFromCSV(String name, String filePath, String fileName);
}
