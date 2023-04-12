package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.map.HospitalEdge;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EdgeDAOTest {

  @Test
  public void testGetEdgeList() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<HospitalEdge> edgeList = SQLRepo.INSTANCE.getEdgeList();

    assertFalse(edgeList.isEmpty());
  }

  @Test
  public void testUpdateList() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.getEdgeList();

    // update
    SQLRepo.INSTANCE.updateEdge(new HospitalEdge("2315", "1875"), "endNode", "1140");

    // reset update
    SQLRepo.INSTANCE.updateEdge(new HospitalEdge("2315", "1140"), "endNode", "1875");
  }

  @Test
  public void testAddAndDeleteEdgeList() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<HospitalEdge> edgeList = SQLRepo.INSTANCE.getEdgeList();

    int lengthList = edgeList.size();

    SQLRepo.INSTANCE.addEdge(new HospitalEdge("2315", "1140"));

    edgeList = SQLRepo.INSTANCE.getEdgeList();

    assertTrue(edgeList.size() == lengthList + 1);

    SQLRepo.INSTANCE.deleteEdge(new HospitalEdge("2315", "1140"));

    edgeList = SQLRepo.INSTANCE.getEdgeList();

    assertTrue(edgeList.size() == lengthList);
  }

  @Test
  public void importEdge() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.MOVE,
        "C:\\Users\\thesm\\OneDrive\\Documents\\GitHub\\Iteration-One\\Data\\NewData\\Move.csv");
  }

  @Test
  public void exportEdge() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.EDGE, "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733", "EdgeExport");
  }
}
