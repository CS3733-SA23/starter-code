package Database;

import edu.wpi.teame.map.MoveAttribute;
import java.util.List;

public class MoveDAO<E> extends DAO<MoveAttribute> {
  List<MoveAttribute> moveAttributes;

  public List<MoveAttribute> get() {

    return moveAttributes;
  }

  public void update() {}

  public void delete(MoveAttribute obj) {}

  public void add(MoveAttribute ma) {}

  public void importFromCSV(String filePath, String tableName) {}

}
