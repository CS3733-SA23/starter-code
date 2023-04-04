package edu.wpi.teamR.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.ArrayList;

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

    /*
    @Test
    void writeCSV() {
    }

    @Test
    void readCSV() {
    }
     */
}
