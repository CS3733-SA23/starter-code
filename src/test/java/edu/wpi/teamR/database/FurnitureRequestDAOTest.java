package edu.wpi.teamR.database;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

class FurnitureRequestDAOTest {
    static FurnitureRequestDAO aFurnitureRequestDAO;
    @BeforeAll
    static void initialize() throws Exception{
        aFurnitureRequestDAO = FurnitureRequestDAO.createInstance(
                "teamr",
                "teamr150",
                "furnitureRequestview",
                "test",
                "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        aFurnitureRequestDAO.deleteFurnitureRequests(null, null, null, null, null, null, null, null);
        Timestamp testTime = new Timestamp(2023, 11, 25, 23, 59, 59, 100000000);
        aFurnitureRequestDAO.addFurnitureRequest(
                1,
                "Jim",
                "Fuller Upper",
                "Recliner",
                "Wong",
                "Even though I cant feel my legs, Id like a recliner!",
                testTime,
                RequestStatus.In_Progress);
    }
    @AfterAll
    static void reset() throws Exception{
        aFurnitureRequestDAO.deleteFurnitureRequests(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }
    @Test
    void addAndDeleteFurnitureRequestTest() throws Exception{
        Timestamp first = new Timestamp(2023, 0, 1, 0, 0, 0, 0);
        FurnitureRequest one = new FurnitureRequest(
                99,
                "Reece",
                "Worcester Polytechnic Institute",
                "Table Leg",
                "Milio",
                "We only have three out of four legs",
                first,
                RequestStatus.Unstarted);
        aFurnitureRequestDAO.addFurnitureRequest(one.getRequestID(), one.getRequesterName(), one.getLocation(), one.getFurnitureType(), one.getStaffMember(),one.getAdditionalNotes(), first, RequestStatus.Unstarted);
        FurnitureRequest two = aFurnitureRequestDAO.selectFurnitureRequests(one.getRequestID(), null, null, null, null, null, null, null).get(0);
        Boolean matchingRequestID = one.getRequestID()==two.getRequestID();
        Boolean matchingRequesterName = one.getRequesterName().equals(two.getRequesterName());
        Boolean matchingLocation = one.getLocation().equals(two.getLocation());
        Boolean matchingFurnitureType = one.getFurnitureType().equals(two.getFurnitureType());
        Boolean matchingStaffMember = one.getStaffMember().equals(two.getStaffMember());
        Boolean matchingAdditionalNotes = one.getAdditionalNotes().equals(two.getAdditionalNotes());
        Boolean matchingTime =  one.getRequestDate().toString().equals(two.getRequestDate().toString());
        Boolean matchingRequestStatus = one.getRequestStatus().toString().equals(two.getRequestStatus().toString());
        assertTrue(matchingRequestID && matchingRequesterName && matchingLocation && matchingFurnitureType && matchingStaffMember && matchingAdditionalNotes && matchingTime && matchingRequestStatus);
        aFurnitureRequestDAO.deleteFurnitureRequests(99, null, null, null, null, null, null, null);
        assertEquals(aFurnitureRequestDAO.selectFurnitureRequests(99, null, null, null, null, null, null, null).size(), 0);
    }
    @Test
    void modifyFurnitureRequestByIDTest() throws Exception {
        Timestamp first = new Timestamp(2023, 0, 1, 0, 0, 0, 0);
        FurnitureRequest one = new FurnitureRequest(
                101,
                "Charles",
                "Joshua Tree",
                "Bench",
                "Cameron",
                "I need a place to sit real quick",
                first,
                RequestStatus.In_Progress);
        aFurnitureRequestDAO.addFurnitureRequest(one.getRequestID(), one.getRequesterName(), one.getLocation(), one.getFurnitureType(), one.getStaffMember(), one.getAdditionalNotes(), first, RequestStatus.Unstarted);
        aFurnitureRequestDAO.modifyFurnitureRequestByID(
                101,
                "Charles",
                "Joshua Tree",
                "Bed",
                "Cameron",
                "Actually, Id rather go to sleep",
                first,
                RequestStatus.In_Progress);
        ArrayList<FurnitureRequest> filteredFurnitureRequests = aFurnitureRequestDAO.selectFurnitureRequests(
                null,
                "Charles",
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(filteredFurnitureRequests.size(), 1);
        FurnitureRequest updatedOne = filteredFurnitureRequests.get(0);
        FurnitureRequest correctOne = new FurnitureRequest(
                101,
                "Charles",
                "Joshua Tree",
                "Bed",
                "Cameron",
                "Actually, Id rather go to sleep",
                first,
                RequestStatus.Unstarted);
        assertEquals(updatedOne.getRequestID(), correctOne.getRequestID());
        Boolean matchingRequesterName = updatedOne.getRequesterName().equals(correctOne.getRequesterName());
        Boolean matchingLocation = updatedOne.getLocation().equals(correctOne.getLocation());
        Boolean matchingFurnitureType = updatedOne.getFurnitureType().equals(correctOne.getFurnitureType());
        Boolean matchingStaffMember = updatedOne.getStaffMember().equals(correctOne.getStaffMember());
        Boolean matchingAdditionalNotes = updatedOne.getAdditionalNotes().equals(correctOne.getAdditionalNotes());
        Boolean matchingTime =  updatedOne.getRequestDate().toString().equals(correctOne.getRequestDate().toString());
        Boolean matchingRequestStatus = updatedOne.getRequestStatus().toString().equals(correctOne.getRequestStatus().toString());
        aFurnitureRequestDAO.deleteFurnitureRequests(101, null, null, null, null, null, null, null);
    }
    @Test
    void selectFurnitureRequestsTest() throws SQLException, ClassNotFoundException {
        Timestamp testStamp1 = new Timestamp(2023, 5, 5, 5, 5, 5, 5);
        Timestamp testStamp2 = new Timestamp(2023, 1, 2, 3, 4, 5, 6);
        Timestamp testStamp3 = new Timestamp(2023, 3, 6, 9, 12, 15, 18);
        Timestamp testStamp4 = new Timestamp(2023, 2, 4, 6, 8, 10, 12);
        aFurnitureRequestDAO.addFurnitureRequest(
                30,
                "Madness",
                "In the middle of our street",
                "Our house",
                "Middle",
                "of our street, Our house in the",
                testStamp1,
                RequestStatus.Complete

        );
        aFurnitureRequestDAO.addFurnitureRequest(
                31,
                "Queen",
                "The real life",
                "Our escape from reality",
                "Poor boy",
                "Because Im easy come, easy go, Little high, little low",
                testStamp2,
                RequestStatus.In_Progress
        );
        aFurnitureRequestDAO.addFurnitureRequest(
                32,
                "Red Hot",
                "The edge of the world",
                "The sun that rises in the east",
                "Chili Peppers",
                "Its understood that Hollywood sells Californication",
                testStamp3,
                RequestStatus.In_Progress
        );
        aFurnitureRequestDAO.addFurnitureRequest(
                33,
                "Elton",
                "Lonely out in space",
                "I miss my wife",
                "John",
                "Timeless flight",
                testStamp4,
                RequestStatus.Complete
        );
        ArrayList<FurnitureRequest> tempFurnitureRequestList = aFurnitureRequestDAO.selectFurnitureRequests(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(tempFurnitureRequestList.size(), 5);
        tempFurnitureRequestList = aFurnitureRequestDAO.selectFurnitureRequests(
                null,
                null,
                "The edge of the world",
                null,
                null,
                null,
                null,
                null);
        assertEquals(tempFurnitureRequestList.size(), 1);
        tempFurnitureRequestList = aFurnitureRequestDAO.selectFurnitureRequests(
                null,
                null,
                "The edge of the world",
                null,
                null,
                "Tomato",
                null,
                null);
        assertEquals(tempFurnitureRequestList.size(), 0);
        tempFurnitureRequestList = aFurnitureRequestDAO.selectFurnitureRequests(
                null,
                null,
                "",
                null,
                null,
                null,
                null,
                null);
        assertEquals(tempFurnitureRequestList.size(), 0);
        tempFurnitureRequestList = aFurnitureRequestDAO.selectFurnitureRequests(
                33,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(tempFurnitureRequestList.size(), 1);
    }
}