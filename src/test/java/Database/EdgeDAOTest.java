package Database;

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
}
