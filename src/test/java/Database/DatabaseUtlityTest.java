package Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.Database.SQLRepo;
import org.junit.jupiter.api.Test;

public class DatabaseUtlityTest {

  @Test
  public void testGetNodeTypeFromNodeID() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    String nodetype = SQLRepo.INSTANCE.getNodeTypeFromNodeID(1200);
    assertEquals("HALL", nodetype);

    String nodetype2 = SQLRepo.INSTANCE.getNodeTypeFromNodeID(1320);
    assertEquals("RETL", nodetype2);

    String nodetype3 = SQLRepo.INSTANCE.getNodeTypeFromNodeID(1360);
    assertEquals("DEPT", nodetype3);
  }
}
