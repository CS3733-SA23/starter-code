package edu.wpi.teamR.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FoodRequestDAOTest {
    static FoodRequestDAO aFoodRequestDAO;
    @BeforeAll
    static void initialize() throws Exception{
        aFoodRequestDAO = FoodRequestDAO.createInstance(
                "teamr",
                "teamr150",
                "furnitureRequestview",
                "test",
                "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        Timestamp testLocalDateTime = Timestamp.valueOf(LocalDateTime.of(
                2023,
                Month.JANUARY,
                15,
                22,
                59,
                59));
        aFoodRequestDAO.addFoodRequest(
                1,
                "James",
                "Fuller Lower",
                "whole turkey",
                "Wong",
                "I'm just a bit hungry",
                testLocalDateTime,
                RequestStatus.Unstarted);
    }

    @AfterAll
    static void reset() throws Exception{
        aFoodRequestDAO.deleteFoodRequests(
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
    void addAndDeleteFoodRequestTest() throws Exception{
        Timestamp testLocalDateTime = Timestamp.valueOf(LocalDateTime.of(
                2023,
                Month.FEBRUARY,
                12,
                1,
                2,
                1));
        FoodRequest one = new FoodRequest(
                10,
                "Ryan",
                "100 institute road",
                "gravy",
                "Qiyana",
                "I need gravy for my meal",
                testLocalDateTime,
                RequestStatus.In_Progress);
        aFoodRequestDAO.addFoodRequest(
                one.getRequestID(),
                one.getRequesterName(),
                one.getLocation(),
                one.getMealType(),
                one.getStaffMember(),
                one.getAdditionalNotes(),
                testLocalDateTime,
                RequestStatus.Unstarted);
        FoodRequest two = aFoodRequestDAO.selectFoodRequests(one.getRequestID(), null, null, null, null, null, null, null).get(0);
        Boolean matchingRequestID = one.getRequestID().equals(two.getRequestID());
        Boolean matchingRequesterName = one.getRequesterName().equals(two.getRequesterName());
        Boolean matchingLocation = one.getLocation().equals(two.getLocation());
        Boolean matchingFurnitureType = one.getMealType().equals(two.getMealType());
        Boolean matchingStaffMember = one.getStaffMember().equals(two.getStaffMember());
        Boolean matchingAdditionalNotes = one.getAdditionalNotes().equals(two.getAdditionalNotes());
        Boolean matchingTime =  one.getRequestDate().toString().equals(two.getRequestDate().toString());
        Boolean matchingRequestStatus = one.getRequestStatus().toString().equals(two.getRequestStatus().toString());
        assertTrue(matchingRequestID && matchingRequesterName && matchingLocation && matchingFurnitureType && matchingStaffMember && matchingAdditionalNotes && matchingTime && matchingRequestStatus);
        aFoodRequestDAO.deleteFoodRequests(99, null, null, null, null, null, null, null);
        assertEquals(aFoodRequestDAO.selectFoodRequests(99, null, null, null, null, null, null, null).size(), 0);
    }
    @Test
    void modifyFoodRequestByIDTest() throws Exception {
        Timestamp testLocalDateTime = Timestamp.valueOf(LocalDateTime.of(
                2023,
                Month.MARCH,
                9,
                9,
                9,
                9));
        FoodRequest one = new FoodRequest(
                900,
                "Charlie",
                "Waikiki",
                "Coconut",
                "Connor",
                null,
                testLocalDateTime,
                RequestStatus.Complete);
        aFoodRequestDAO.addFoodRequest(one.getRequestID(), one.getRequesterName(), one.getLocation(), one.getMealType(), one.getStaffMember(), one.getAdditionalNotes(), testLocalDateTime, RequestStatus.Complete);
        aFoodRequestDAO.modifyFoodRequestByID(
                900,
                null,
                null,
                "Pineapple",
                null,
                "Pineapple juice is great",
                null,
                null);
        ArrayList<FoodRequest> filteredFoodRequests = aFoodRequestDAO.selectFoodRequests(
                null,
                "Charlie",
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(filteredFoodRequests.size(), 1);
        FoodRequest updatedOne = filteredFoodRequests.get(0);
        FoodRequest correctOne = new FoodRequest(
                900,
                "Charlie",
                "Waikiki",
                "Pineapple",
                "Connor",
                "Pineapple juice is great",
                testLocalDateTime,
                RequestStatus.Complete);
        assertEquals(updatedOne.getRequestID(), correctOne.getRequestID());
        assertEquals(updatedOne.getRequesterName(), correctOne.getRequesterName());
        assertEquals(updatedOne.getLocation(), correctOne.getLocation());
        assertEquals(updatedOne.getMealType(), correctOne.getMealType());
        assertEquals(updatedOne.getStaffMember(), correctOne.getStaffMember());
        assertEquals(updatedOne.getAdditionalNotes(), correctOne.getAdditionalNotes());
        assertEquals(updatedOne.getRequestDate().toString(), correctOne.getRequestDate().toString());
        assertEquals(updatedOne.getRequestStatus().toString(), correctOne.getRequestStatus().toString());
        aFoodRequestDAO.deleteFoodRequests(900, null, null, null, null, null, null, null);
    }
    @Test
    void selectFoodRequestsTests() throws SQLException, ClassNotFoundException{
        Timestamp testLocalDateTime1 = Timestamp.valueOf(LocalDateTime.of(2023, Month.MAY, 10, 10, 10, 10));
        Timestamp testLocalDateTime2 = Timestamp.valueOf(LocalDateTime.of(2023, Month.JUNE, 11, 11, 11, 11));
        Timestamp testLocalDateTime3 = Timestamp.valueOf(LocalDateTime.of(2023, Month.JULY, 12, 12, 12, 12));
        Timestamp testLocalDateTime4 = Timestamp.valueOf(LocalDateTime.of(2023, Month.AUGUST, 8, 8, 8, 8));
        aFoodRequestDAO.addFoodRequest(
                17,
                "Blue",
                "Deep inside of me",
                "Ooga-Chaka Ooga-Ooga",
                "I'm high on believing",
                "I can't stop this feeling",
                testLocalDateTime1,
                RequestStatus.In_Progress);
        aFoodRequestDAO.addFoodRequest(
                18,
                "They might be giants",
                "Istanbul was Constantinople",
                "Turkish delight",
                "Lives in Istanbul, not Constantinople",
                "Every gal in Constantinople",
                testLocalDateTime2,
                RequestStatus.Unstarted);
        aFoodRequestDAO.addFoodRequest(
                19,
                "Queen",
                "Down the street",
                "Ain't no sound but the sound of his feet. Machine guns ready to go",
                "Are you ready hey are you ready for this?",
                "Another one bites the dust",
                testLocalDateTime3,
                RequestStatus.In_Progress);
        aFoodRequestDAO.addFoodRequest(
                20,
                "Electric Light Orchestra",
                "Sun is shinin' in the sky",
                "There ain't a cloud in sight",
                "Mr. Blue Sky please tell us why",
                "You had to hide away for so long (so long). Where did we go wrong?",
                testLocalDateTime4,
                RequestStatus.In_Progress);
        ArrayList<FoodRequest> tempFoodRequestList = aFoodRequestDAO.selectFoodRequests(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(tempFoodRequestList.size(), 5);
        tempFoodRequestList = aFoodRequestDAO.selectFoodRequests(
                null,
                null,
                "Sun is shinin' in the sky",
                null,
                null,
                null,
                null,
                null);
        assertEquals(tempFoodRequestList.size(), 1);
        tempFoodRequestList = aFoodRequestDAO.selectFoodRequests(
                null,
                null,
                "Sun is shinin' in the sky",
                null,
                null,
                "Tomato",
                null,
                null);
        assertEquals(tempFoodRequestList.size(), 0);
        tempFoodRequestList = aFoodRequestDAO.selectFoodRequests(
                null,
                null,
                "",
                null,
                null,
                null,
                null,
                null);
        assertEquals(tempFoodRequestList.size(), 0);
        tempFoodRequestList = aFoodRequestDAO.selectFoodRequests(
                33,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertEquals(tempFoodRequestList.size(), 1);
    }
}