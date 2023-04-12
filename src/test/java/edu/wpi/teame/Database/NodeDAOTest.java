package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.NodeInitializer;
import java.util.List;
import org.junit.jupiter.api.Test;

public class NodeDAOTest {

  @Test
  public void testGetAddandDelete() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<HospitalNode> originalNodes = SQLRepo.INSTANCE.getNodeList();
    assertEquals(581, originalNodes.size());

    SQLRepo.INSTANCE.addNode(new HospitalNode(new NodeInitializer("0", 0, 0, "L2", "Test")));
    List<HospitalNode> addedNodes = SQLRepo.INSTANCE.getNodeList();
    assertEquals(582, addedNodes.size());

    SQLRepo.INSTANCE.deletenode(new HospitalNode(new NodeInitializer("0", 0, 0, "L2", "Test")));
    List<HospitalNode> deletedNodes = SQLRepo.INSTANCE.getNodeList();
    assertEquals(581, deletedNodes.size());

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testUpdate() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    HospitalNode hn = new HospitalNode(new NodeInitializer("1200", 1608, 2737, "1", "BTM"));
    SQLRepo.INSTANCE.updateNode(hn, "floor", "1");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testImportExport() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.NODE,
        "C:\\Users\\jamie\\OneDrive - Worcester Polytechnic Institute (wpi.edu)\\Desktop",
        "Node");
    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.NODE,
        "C:\\Users\\jamie\\OneDrive - Worcester Polytechnic Institute (wpi.edu)\\Desktop\\CS 3733\\Iteration-One\\Data\\NewData\\Node.csv");

    // Import back Edge and Move Table
    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.MOVE,
        "C:\\Users\\jamie\\OneDrive - Worcester Polytechnic Institute (wpi.edu)\\Desktop\\CS 3733\\Iteration-One\\Data\\NewData\\Move.csv");
    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.EDGE,
        "C:\\Users\\jamie\\OneDrive - Worcester Polytechnic Institute (wpi.edu)\\Desktop\\CS 3733\\Iteration-One\\Data\\NewData\\Edge.csv");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
