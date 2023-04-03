package edu.wpi.teamR.database;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FurnitureRequestDAO {
    private static FurnitureRequestDAO instance;
    private ArrayList<FurnitureRequest> furnitureRequests;
    private String username, password, tableName, schemaName, connectionURL;
    private FurnitureRequestDAO(String username, String password, String tableName, String schemaName, String connectionURL){
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.connectionURL = connectionURL;
    }

    public static FurnitureRequestDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL) {
        if (FurnitureRequestDAO.instance == null)
            FurnitureRequestDAO.instance = new FurnitureRequestDAO(username, password, tableName, schemaName, connectionURL);
        return FurnitureRequestDAO.instance;
    }
    public static FurnitureRequestDAO getInstance(){
        return FurnitureRequestDAO.instance;
    }
    public ArrayList<FurnitureRequest> getFurnitureRequests(){
        return furnitureRequests;
    }
    public FurnitureRequest addFurnitureRequest(int requestID, String requesterName, String location, String furnitureType,
                                                String staffMember, String additionalNotes, Timestamp requestDate,
                                                RequestStatus requestStatus) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlInsert = "INSERT INTO "+schemaName+"."+tableName+"(furnitureID, requesterName, location, furnitureType, staffMember, additionalNotes, requestDate, requestStatus) ";
        sqlInsert+= "VALUES("+requestID+",\'"+requesterName+"\',\'"+location+"\',\'"+furnitureType+"\',\'"+staffMember+"\',\""+additionalNotes+
            "\',\'"+requestDate.toString()+"\',\'"+requestStatus.toString()+"\'"+requestID+");";
        statement.executeUpdate(sqlInsert);
        FurnitureRequest aReq = new FurnitureRequest(requestID, requesterName, location, furnitureType, staffMember, additionalNotes, requestDate, requestStatus);
        furnitureRequests.add(aReq);
        closeConnection(connection);
        return aReq;
    }
    public void deleteFurnitureRequests(Integer requestID, String requesterName, String location, String furnitureType,
                            String staffMember, String additionalNotes, Timestamp requestDate,
                            RequestStatus requestStatus) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        if(requesterName == null && location == null && furnitureType == null && staffMember == null && additionalNotes == null
                && requestDate == null && requestStatus == null && requestID == null){
            String sqlDeleteALL = "DELETE FROM " + schemaName + "." + tableName + ";";
        } else{
            String sqlDelete = "DELETE FROM " + schemaName + "." + tableName + "WHERE ";
            int count = 0;
            if(requesterName != null){
                count++;
                sqlDelete += "requesterName = " + "\'" + requesterName + "\'";
            }
            if(location != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                count++;
                sqlDelete += "location = " + "\'" + location + "\'";
            }
            if(furnitureType != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                count++;
                sqlDelete += "furnitureType = " + "\'" + furnitureType + "\'";
            }
            if(staffMember != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                count++;
                sqlDelete += "staffMember = " + "\'" + staffMember+ "\'";
            }
            if(additionalNotes != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                sqlDelete += "additionalNotes = " + "\'" + additionalNotes+ "\'";
            }
            if(requestDate != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                sqlDelete += "requestDate = " + "\'" + requestDate.toString()+ "\'";
            }
            if(requestStatus != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                sqlDelete += "requestStatus = " + "\'" + requestStatus.toString()+ "\'";
            }
            if(requestID != null){
                if(count == 0){
                    sqlDelete += " AND ";
                }
                sqlDelete += "requestID = " + requestID;
            }
            sqlDelete += ";";
            statement.executeUpdate(sqlDelete);
            closeConnection(connection);
        }

        for(int i = 0; i<furnitureRequests.size(); i++){
            Boolean requesterNameCheck = requesterName == null || requesterName == furnitureRequests.get(i).getRequesterName();
            Boolean locationCheck = location == null || location == furnitureRequests.get(i).getLocation();
            Boolean furnitureTypeCheck = furnitureType == null || furnitureType == furnitureRequests.get(i).getFurnitureType();
            Boolean staffMemberCheck = staffMember == null || staffMember == furnitureRequests.get(i).getStaffMember();
            Boolean additionalNotesCheck = additionalNotes == null || additionalNotes == furnitureRequests.get(i).getAdditionalNotes();
            Boolean requestDateCheck = requestDate == null || requestDate == furnitureRequests.get(i).getRequestDate();
            Boolean requestIDCheck = requestID == null || requestID == furnitureRequests.get(i).getRequestID();
            if(requesterNameCheck && locationCheck && furnitureTypeCheck && staffMemberCheck && additionalNotesCheck && requestDateCheck && requestIDCheck){
                furnitureRequests.remove(i);
            }
        }
    }

    //TODO timestamp is not correct
    public void modifyFurnitureRequestByID(Integer requestID, String requesterName, String location, String furnitureType,
                               String staffMember, String additionalNotes, Timestamp requestDate,
                               RequestStatus requestStatus) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlUpdate = "UPDATE " + schemaName + "." + tableName + " SET requesterName = \'" + requesterName + "\'";
        sqlUpdate += ", location = \'" + location + "\', furnitureType = \'" + furnitureType+ "\', staffMember = \'" + staffMember +
                "\', additionalNotes = \'" + additionalNotes + "\', requestDate = \'" + requestDate +
                    "\', requestStatus = \'" + requestStatus + "\' WHERE requestID = " + requestID;
        statement.executeUpdate(sqlUpdate);
        closeConnection(connection);
        FurnitureRequest aReq = selectFurnitureRequests(requestID, null, null, null, null, null, null, null).get(0);
        aReq.setRequestID(requestID);
        aReq.setRequesterName(requesterName);
        aReq.setLocation(location);
        aReq.setFurnitureType(furnitureType);
        aReq.setStaffMember(staffMember);
        aReq.setAdditionalNotes(additionalNotes);
        aReq.setRequestDate(requestDate);
        aReq.setRequestStatus(requestStatus);
    }

    public ArrayList<FurnitureRequest> selectFurnitureRequests(Integer requestID, String requesterName, String location, String furnitureType,
                                          String staffMember, String additionalNotes, Timestamp requestDate,
                                          RequestStatus requestStatus){
        ArrayList<FurnitureRequest> aList = new ArrayList<FurnitureRequest>();
        for(int i = 0; i<furnitureRequests.size(); i++){
            Boolean requesterNameCheck = requesterName == null || requesterName == furnitureRequests.get(i).getRequesterName();
            Boolean locationCheck = location == null || location == furnitureRequests.get(i).getLocation();
            Boolean furnitureTypeCheck = furnitureType == null || furnitureType == furnitureRequests.get(i).getFurnitureType();
            Boolean staffMemberCheck = staffMember == null || staffMember == furnitureRequests.get(i).getStaffMember();
            Boolean additionalNotesCheck = additionalNotes == null || additionalNotes == furnitureRequests.get(i).getAdditionalNotes();
            Boolean requestDateCheck = requestDate == null || requestDate == furnitureRequests.get(i).getRequestDate();
            Boolean requestIDCheck = requestID == null || requestID == furnitureRequests.get(i).getRequestID();
            if(requesterNameCheck && locationCheck && furnitureTypeCheck && staffMemberCheck && additionalNotesCheck && requestDateCheck && requestIDCheck){
                aList.add(furnitureRequests.get(i));
            }
        }
        return aList;
    }



    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(connectionURL, username, password);
    }

    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
