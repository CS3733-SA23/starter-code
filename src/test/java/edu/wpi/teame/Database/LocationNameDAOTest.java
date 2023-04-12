package edu.wpi.teame.Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.map.LocationName;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LocationNameDAOTest {
  @Test
  public void getLocationName() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<LocationName> locationNameList = SQLRepo.INSTANCE.getLocationList();
    assertFalse(locationNameList.isEmpty());
  }

  @Test
  public void testUpdateList() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.getLocationList();

    // update
    SQLRepo.INSTANCE.updateLocation(
        new LocationName("Hall 1 Level 2", "Hall", LocationName.NodeType.HALL),
        "shortName",
        "Test");

    // reset update
    SQLRepo.INSTANCE.updateLocation(
        new LocationName("Hall 1 Level 2", "Test", LocationName.NodeType.HALL),
        "shortName",
        "Hall");
  }

  @Test
  public void testAddAndDeleteLocationNameList() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    List<LocationName> locationNames = SQLRepo.INSTANCE.getLocationList();

    int lengthList = locationNames.size();

    SQLRepo.INSTANCE.addLocation(new LocationName("Cafe 1", "Test", LocationName.NodeType.REST));

    locationNames = SQLRepo.INSTANCE.getLocationList();

    assertTrue(locationNames.size() == lengthList + 1);

    SQLRepo.INSTANCE.deleteLocation(new LocationName("Cafe 1", "Test", LocationName.NodeType.REST));

    locationNames = SQLRepo.INSTANCE.getLocationList();

    assertTrue(locationNames.size() == lengthList);
  }

  @Test
  public void importLocationName() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.LOCATION_NAME,
        "C:\\Users\\thesm\\OneDrive\\Documents\\GitHub\\Iteration-One\\Data\\NewData\\LocationName.csv");
  }

  @Test
  public void exportLocationName() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.LOCATION_NAME,
        "C:\\Users\\thesm\\OneDrive\\Desktop\\CS 3733",
        "LocationNameExport");
  }
}
