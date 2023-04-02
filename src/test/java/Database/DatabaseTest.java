package Database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DatabaseTest {

  public DatabaseGraphController setup() {
    DatabaseController DBC1 = new DatabaseController("teame", "teame50");
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
}
