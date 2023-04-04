package edu.wpi.teamR.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.*;

class LocationNameDAOTest {

    static LocationNameDAO dao;

    @BeforeAll
    static void setUp() throws Exception {
        dao = LocationNameDAO.createInstance("teamr", "teamr150", "locationName",
                "test", "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        dao.deleteLocationNames(null, null, null);
        dao.addLocationName("lengthy", "short","STAI");

    }

    @AfterAll
    static void tearDown() throws Exception {
        dao.deleteLocationNames(null, null, null);
    }

    @Test
    void createInstance() throws SQLException, ClassNotFoundException {
        LocationNameDAO dao2 = LocationNameDAO.createInstance("teamr", "teamr150", "locationName",
                "test", "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        assertSame(dao, dao2);
    }

    @Test
    void getInstance() {
        LocationNameDAO dao2 = LocationNameDAO.getInstance();
        assertSame(dao, dao2);
    }

    @Test
    void getLocationNames() {
    }

    @Test
    void addAndDeleteLocationName() throws SQLException, ClassNotFoundException {
        LocationName locationName = new LocationName("big", "small", "HALL");
        dao.addLocationName(locationName.getLongName(), locationName.getShortName(), locationName.getNodeType());
        LocationName locationName2 = dao.selectLocationNames("big", null, null).get(0);
        boolean sameLongName = locationName.getLongName().equals(locationName2.getLongName());
        boolean sameShortName = locationName.getShortName().equals(locationName2.getShortName());
        boolean sameNodeType = locationName.getNodeType().equals(locationName2.getNodeType());
        assertTrue(sameLongName && sameShortName && sameNodeType);
        dao.deleteLocationNames("big", null, null);
        assertEquals(dao.selectLocationNames("big", null, null).size(), 0);
    }

    @Test
    void modifyLocationNameByLongName() throws SQLException, ClassNotFoundException, NotFoundException {
        dao.addLocationName("Large", "Not Large", "ELEV");
        dao.modifyLocationNameByLongName("Large", "Very Not Large", "ELEV");
        ArrayList<LocationName> locationNames = dao.selectLocationNames(null, "Very Not Large", null);
        assertEquals(locationNames.size(), 1);
        LocationName locationName = locationNames.get(0);
        LocationName locationName2 = new LocationName("Large", "Very Not Large", "ELEV");
        boolean sameLongName = locationName.getLongName().equals(locationName2.getLongName());
        boolean sameShortName = locationName.getShortName().equals(locationName2.getShortName());
        boolean sameNodeType = locationName.getNodeType().equals(locationName2.getNodeType());
        assertTrue(sameLongName && sameShortName && sameNodeType);
        dao.deleteLocationNames("Large", null, null);
    }

    @Test
    void selectLocationNames() throws SQLException, ClassNotFoundException {
        dao.deleteLocationNames(null, null, null);
        dao.addLocationName("large", "small", "REST");
        dao.addLocationName("very large", "very small", "REST");
        dao.addLocationName("largee", "very small", "REST");
        dao.addLocationName("very largee", "small", "REST");
        dao.addLocationName("largeee", "petite", "REST");

        ArrayList<LocationName> locationNames = dao.selectLocationNames(null, null, null);
        assertEquals(locationNames.size(), 5);

        locationNames = dao.selectLocationNames(null, null, "REST");
        assertEquals(locationNames.size(), 5);

        locationNames = dao.selectLocationNames(null, "small", null);
        assertEquals(locationNames.size(), 2);

        locationNames = dao.selectLocationNames("large", null, null);
        assertEquals(locationNames.size(), 1);

        locationNames = dao.selectLocationNames("not large", null, null);
        assertEquals(locationNames.size(), 0);
    }
    @Test
    void writeCSV() throws IOException, SQLException, ClassNotFoundException {
        dao.deleteLocationNames(null, null, null);
        dao.addLocationName("Hall 1 Level 2","Hall","HALL");
        dao.addLocationName("Elevator S 01","Elevator S 1","ELEV");
        dao.addLocationName("Hallway Intersection 22 Level 2","Hallway B2202","HALL");
        String[][] inputData = {
                {"longName,shortName,nodeType"},
                {"Hall 1 Level 2,Hall,HALL"},
                {"Elevator S 01,Elevator S 1,ELEV"},
                {"Hallway Intersection 22 Level 2,Hallway B2202,HALL"}
        };
        File tempFile = File.createTempFile("test", ".csv");
        dao.writeCSV(tempFile.getAbsolutePath());
        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        String line;
        StringBuilder contents = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            contents.append(line).append("\n");
        }
        reader.close();
        String expectedOutput = "longName,shortName,nodeType\nHall 1 Level 2,Hall,HALL\nElevator S 01,Elevator S 1,ELEV\nHallway Intersection 22 Level 2,Hallway B2202,HALL\n";
        assertEquals(expectedOutput, contents.toString());
        tempFile.delete();
        dao.deleteLocationNames(null, null, null);
    }

    @Test
    void readCSV() throws IOException, SQLException, ClassNotFoundException {
        dao.deleteLocationNames(null, null, null);
        File tempFile = File.createTempFile("test", ".csv");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("longName,shortName,nodeType\nHall 1 Level 2,Hall,HALL\nElevator S 01,Elevator S 1,ELEV\nHallway Intersection 22 Level 2,Hallway B2202,HALL\n");
        writer.close();
        dao.readCSV(tempFile.getAbsolutePath());
        ArrayList<LocationName> aList = new ArrayList<LocationName>();
        aList = dao.selectLocationNames("Hall 1 Level 2", "Hall", "HALL");
        assertEquals("Hall 1 Level 2", aList.get(0).getLongName());
        assertEquals("Hall", aList.get(0).getShortName());
        assertEquals("HALL", aList.get(0).getNodeType());
        aList = dao.selectLocationNames("Elevator S 01","Elevator S 1","ELEV");
        assertEquals("Elevator S 01", aList.get(0).getLongName());
        assertEquals("Elevator S 1", aList.get(0).getShortName());
        assertEquals("ELEV", aList.get(0).getNodeType());
        aList = dao.selectLocationNames("Hallway Intersection 22 Level 2", "Hallway B2202", "HALL");
        assertEquals("Hallway Intersection 22 Level 2", aList.get(0).getLongName());
        assertEquals("Hallway B2202", aList.get(0).getShortName());
        assertEquals("HALL", aList.get(0).getNodeType());
    }
}
