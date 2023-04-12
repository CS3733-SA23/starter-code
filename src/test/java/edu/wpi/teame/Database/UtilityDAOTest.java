package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class UtilityDAOTest {

  @Test
  public void getShortName() throws SQLException {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    String shortNameFromNodeID = SQLRepo.INSTANCE.getShortNameFromNodeID("1335");

    assertTrue(shortNameFromNodeID.equals("Conf B0102"));
  }
}
