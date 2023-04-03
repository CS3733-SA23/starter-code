package edu.wpi.teamR.database;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoodRequest {
    private String requesterName, location, mealType, staffMember, additionalNotes;
    private Timestamp requestDate; //TODO: test localdatetime vs timestamp
    private RequestStatus requestStatus;
    private Integer requestID;

    public FoodRequest(Integer requestID, String requesterName, String location, String mealType, String staffMember,
                       String additionalNotes, Timestamp requestDate, RequestStatus requestStatus) {
        this.requesterName = requesterName;
        this.location = location;
        this.mealType = mealType;
        this.staffMember = staffMember;
        this.additionalNotes = additionalNotes;
        this.requestDate = requestDate;
        this.requestStatus = requestStatus;
        this.requestID = requestID;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(String staffMember) {
        this.staffMember = staffMember;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getRequestID(){ return requestID;}

    public void setRequestID(Integer requestID){this.requestID = requestID;}
}
