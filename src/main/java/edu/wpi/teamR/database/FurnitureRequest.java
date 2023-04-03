package edu.wpi.teamR.database;

import java.sql.Timestamp;

public class FurnitureRequest {
    private String requesterName, location, furnitureType, staffMember, additionalNotes;

    private int requestID;
    private Timestamp requestDate;
    private RequestStatus requestStatus;

    public FurnitureRequest(Integer requestID, String requesterName, String location, String furnitureType, String staffMember,
                            String additionalNotes, Timestamp requestDate, RequestStatus requestStatus) {
        this.requesterName = requesterName;
        this.location = location;
        this.furnitureType = furnitureType;
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

    public String getFurnitureType() {
        return furnitureType;
    }

    public void setFurnitureType(String furnitureType) {
        this.furnitureType = furnitureType;
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
    public int getRequestID() { return requestID; }

    public void setRequestID(int requestID) { this.requestID = requestID; }
}
