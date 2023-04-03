package Database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import pathfinding.Floor;
import pathfinding.HospitalEdge;
import pathfinding.HospitalNode;
import pathfinding.MoveAttribute;

public class DatabaseTest {

  /**
   * Creates DatabaseGraphController to use for tests Will catch a runtime error if you cannot
   * connect to Database
   *
   * @return DatabaseGraphController
   */
  public DatabaseGraphController setup() {
    try {
      DatabaseController DBC1 = new DatabaseController();
      return new DatabaseGraphController(DBC1);
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  /** Tests to see if you can get the nodeID from a given longName in the Move table */
  @Test
  public void testGetNodeIDFromName() {
    DatabaseGraphController DBMC = this.setup();
    try {
      int expected = DBMC.getNodeIDFromName("Hall 3 Level 1");

      assertEquals(expected, 1200);
    } catch (RuntimeException e) {
      System.out.println(
          "SQL Exception: "
              + "\nThere is no node linked to that longName in the Move table so the SQL query returned nothing");
    }
  }

  /** Tests to see if you can get a list of MoveAttributes from a given floor */
  @Test
  public void testGetMoveAttributeFromFloor() {
    DatabaseGraphController DBMC = setup();

    List<MoveAttribute> moveAttributeList = DBMC.getMoveAttributeFromFloor(Floor.LOWER_ONE);

    assertEquals(moveAttributeList.size(), 45);
  }

  /** Tests the new retrieveFromTable method and produces list of nodes and strings */
  @Test
  public void testNewRetrieveFromTable() {
    DatabaseGraphController DBMC = setup();
    DBMC.retrieveFromTable();

    List<HospitalNode> nlist = DBMC.getHospitalNodes();
    List<HospitalEdge> elist = DBMC.getHospitalEdges();

    //    for (HospitalNode hn : nlist) {
    //      System.out.println(hn);
    //    }

    assertEquals(581, nlist.size());
    assertEquals(684, elist.size());
  }
}
