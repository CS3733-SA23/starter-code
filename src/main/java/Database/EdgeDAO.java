package Database;

import edu.wpi.teame.map.HospitalEdge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class EdgeDAO<E> implements DAO<HospitalEdge> {
  List<HospitalEdge> hospitalEdgeList;

  @Override
  public List<HospitalEdge> get() {
    hospitalEdgeList =  new LinkedList<>();

    try{
      Statement stmt =  DatabaseController.INSTANCE.getC().createStatement();

      String sql = "SELECT \"startNode\", \"endNode\" FROM teame.\"Edge\" ;";
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){
        hospitalEdgeList.add(new HospitalEdge(rs.getString("startNode"), rs.getString("endNode")));
      }
      return hospitalEdgeList;
    } catch (SQLException e) {
      throw new RuntimeException("Something went wrong");
    }
  }

  @Override
  public void update() {}

  @Override
  public void delete(HospitalEdge obj) {}

  @Override
  public void add(HospitalEdge obj) {}

  @Override
  public void importFromCSV(String filePath, String tableName) {}

  @Override
  public void exportFromCSV(String name, String filePath, String fileName) {}
}
