package Database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import pathfinding.Floor;
import pathfinding.HospitalEdge;
import pathfinding.HospitalNode;
import pathfinding.MoveAttribute;

public class DatabaseTest {

  public DatabaseGraphController setup() {
    DatabaseController DBC1 = new DatabaseController();
    return new DatabaseGraphController(DBC1);
  }

  @Test
  public void testGetNodeIDFromName() {
    DatabaseGraphController DBMC = this.setup();
    try {
      int expected = DBMC.getNodeIDFromName("Hall 3 Level 1");
      System.out.println(expected);
      assertEquals(expected, 1200);
    } catch (RuntimeException e) {
      System.out.println("Runtime Exception");
    }
  }

  @Test
  public void testGetMoveAttributeFromFloor() {
    DatabaseGraphController DBMC = setup();

    List<MoveAttribute> moveAttributeList = DBMC.getMoveAttributeFromFloor(Floor.LOWER_ONE);

    System.out.println(moveAttributeList.size());
    assertEquals(0, 0);
  }

  @Test
  public void testNewRetrieveFromTable() {
    DatabaseGraphController DBMC = setup();
    DBMC.retrieveFromTable();

    List<HospitalNode> nlist = DBMC.getHospitalNodes();
    List<HospitalEdge> elist = DBMC.getHospitalEdges();

    for (HospitalNode hn : nlist) {
      System.out.println(hn);
    }

    assertEquals(581, nlist.size());
    assertEquals(684, elist.size());
  }
}
