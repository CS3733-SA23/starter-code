package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.map.MoveAttribute;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MoveDAOTest {
  @Test
  public void getMove() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<MoveAttribute> moveAttributeList = SQLRepo.INSTANCE.getMoveList();
    assertFalse(moveAttributeList.isEmpty());
  }

  @Test
  public void testUpdateList() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.getMoveList();

    // add update
    SQLRepo.INSTANCE.updateMove(
        new MoveAttribute("1200", "Hall 3 Level 1", "1/1/2023"), "date", "Test");

    // reset update
    SQLRepo.INSTANCE.updateMove(
        new MoveAttribute("1200", "Hall 3 Level 1", "Test"), "date", "1/1/2023");
  }

  @Test
  public void testAddAndDeleteMove() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<MoveAttribute> moveAttributes = SQLRepo.INSTANCE.getMoveList();

    int lengthList = moveAttributes.size();

    SQLRepo.INSTANCE.addMove(new MoveAttribute("2535", "HallNode", "Test"));

    moveAttributes = SQLRepo.INSTANCE.getMoveList();

    assertTrue(moveAttributes.size() == lengthList + 1);

    SQLRepo.INSTANCE.deleteMove(new MoveAttribute("2535", "HallNode", "Test"));

    moveAttributes = SQLRepo.INSTANCE.getMoveList();

    assertTrue(moveAttributes.size() == lengthList);
  }

  @Test
  public void importMove() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.MOVE,
        "C:\\Users\\thesm\\OneDrive\\Documents\\GitHub\\Iteration-One\\Data\\NewData\\Move.csv");
  }

  @Test
  public void exportMove() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.MOVE, "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733", "MoveExport");
  }
}
