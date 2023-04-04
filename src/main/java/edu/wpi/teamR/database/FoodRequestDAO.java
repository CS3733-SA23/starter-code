package edu.wpi.teamR.database;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FoodRequestDAO { // TODO: make sure tablename is the view
  private static FoodRequestDAO instance;
  private ArrayList<FoodRequest> foodRequests;
  private String longName, username, password, tableName, schemaName, connectionURL;

  private FoodRequestDAO(
      String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
    this.username = username;
    this.password = password;
    this.tableName = tableName;
    this.schemaName = schemaName;
    this.connectionURL = connectionURL;

    foodRequests = new ArrayList<FoodRequest>();
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM "+schemaName+"."+tableName+";");
    while (resultSet.next()) {
      Integer requestID = resultSet.getInt("requestid");
      String requesterName = resultSet.getString("requestername");
      String staffMember = resultSet.getString("staffmember");
      String location = resultSet.getString("location");
      Timestamp requestDate = resultSet.getTimestamp("requestdate");
      String additionalNotes = resultSet.getString("additionalnotes");
      String mealType = resultSet.getString("mealtype");
      String requestStatus = resultSet.getString("requeststatus");
      FoodRequest foodRequest = new FoodRequest(requestID, requesterName, location, mealType, staffMember, additionalNotes, requestDate, RequestStatus.valueOf(requestStatus));
      foodRequests.add(foodRequest);
    }
  }

  public static FoodRequestDAO createInstance(
      String username, String password, String tableName, String schemaName, String connectionURL) throws SQLException, ClassNotFoundException {
    if (FoodRequestDAO.instance == null)
      FoodRequestDAO.instance =
          new FoodRequestDAO(username, password, tableName, schemaName, connectionURL);
    return FoodRequestDAO.instance;
  }

  public static FoodRequestDAO getInstance() {
    return FoodRequestDAO.instance;
  }

  public ArrayList<FoodRequest> getFoodRequests() {
    return foodRequests;
  };

  public FoodRequest addFoodRequest(
          int requestID,
          String requesterName,
          String location,
          String mealType,
          String staffMember,
          String additionalNotes,
          Timestamp requestDate,
          RequestStatus requestStatus)
      throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    String sqlInsert =
        "INSERT INTO "
            + schemaName
            + "."
            + tableName
            + "(requestID, requesterName, location, requestDate, additionalNotes, mealType, requestStatus, staffmember) ";
    sqlInsert +=
        "VALUES("
            + requestID
            + ",\'"
            + requesterName
            + "\',\'"
            + location
            + "\',\'"
            + requestDate
            + "\',\'"
            + additionalNotes
            + "\',\'"
            + mealType
            + "\',\'"
            + requestStatus.toString()
            + "\',\'"
            + staffMember
            + "\');";
    FoodRequest aFoodRequest =
        new FoodRequest(
            requestID,
            requesterName,
            location,
            mealType,
            staffMember,
            additionalNotes,
            requestDate,
            requestStatus);
    statement.executeUpdate(sqlInsert);
    foodRequests.add(aFoodRequest);
    closeConnection(connection);
    return aFoodRequest;
  }

  public void deleteFoodRequests(
      Integer requestID,
      String requesterName,
      String location,
      String mealType,
      String staffMember,
      String additionalNotes,
      Timestamp requestDate,
      RequestStatus requestStatus)
      throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    String sqlDelete;
    if (requestID == null
        && requesterName == null
        && location == null
        && mealType == null
        && staffMember == null
        && additionalNotes == null
        && requestDate == null
        && requestStatus == null) {
      sqlDelete = "DELETE FROM " + schemaName + "." + tableName + ";";
    } else {
      sqlDelete = "DELETE FROM " + schemaName + "." + tableName + " WHERE ";
      int count = 0;
      if (requestID != null) {
        count++;
        sqlDelete += "requestID = " + requestID;
      }
      if (requesterName != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        count++;
        sqlDelete += "requestername = '" + requesterName + "'";
      }
      if (location != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        count++;
        sqlDelete += "location = '" + location + "'";
      }
      if (mealType != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        count++;
        sqlDelete += "mealType   = " + "'" + mealType + "'";
      }
      if (staffMember != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        sqlDelete += "staffMember = " + "'" + staffMember + "'";
      }
      if (additionalNotes != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        count++;
        sqlDelete += "additionalNotes = " + "'" + additionalNotes + "'";
      }
      if (requestDate != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        count++;
        sqlDelete += "requestDate = " + "'" + requestDate + "'";
      }
      if (requestStatus != null) {
        if (count == 0) {
          sqlDelete += " AND ";
        }
        sqlDelete += "requestStatus = " + "'" + requestStatus + "'";
      }
      sqlDelete += ";";
    }
    statement.executeUpdate(sqlDelete);
    closeConnection(connection);
    for (int i = 0; i < foodRequests.size(); i++) {
      Boolean requestIDCheck = requestID == null || requestID.equals(foodRequests.get(i).getRequestID());
      Boolean requesterNameCheck =
          requesterName == null || requesterName.equals(foodRequests.get(i).getRequesterName());
      Boolean locationCheck =
          location == null || location.equals(foodRequests.get(i).getLocation());
      Boolean mealTypeCheck =
          mealType == null || mealType.equals(foodRequests.get(i).getMealType());
      Boolean staffMemberCheck =
          staffMember == null || staffMember.equals(foodRequests.get(i).getStaffMember());
      Boolean additionalNotesCheck =
          additionalNotes == null
              || additionalNotes.equals(foodRequests.get(i).getAdditionalNotes());
      Boolean requestDateCheck =
          requestDate == null
              || requestDate.toString().equals(foodRequests.get(i).getRequestDate().toString());
      Boolean requestStatusCheck =
          requestStatus == null
              || requestStatus.toString().equals(foodRequests.get(i).getRequestStatus().toString());
      if (requestIDCheck
          && requesterNameCheck
          && locationCheck
          && mealTypeCheck
          && staffMemberCheck
          && additionalNotesCheck
          && requestDateCheck
          && requestStatusCheck) {
        foodRequests.remove(i);
        i--;
      }
    }
  }

  public void modifyFoodRequestByID(
          Integer requestID,
          String requesterName,
          String location,
          String mealType,
          String staffMember,
          String additionalNotes,
          Timestamp requestDate,
          RequestStatus requestStatus)
      throws SQLException, ClassNotFoundException {
    Connection connection = createConnection();
    Statement statement = connection.createStatement();
    String sqlUpdate =
        "UPDATE " + schemaName + "." + tableName + " SET requesterName = '" + requesterName + "'";
    sqlUpdate +=
            ", location = '" + location + "', mealType = '" + mealType + "' , staffMember = '";
    sqlUpdate +=
        staffMember + "' , additionalNotes = '" + additionalNotes + "' , requestDate = '";
    sqlUpdate += requestDate.toString() + "' , requestStatus = '" + requestStatus.toString();
    sqlUpdate += "' WHERE requestID = " + requestID + ";";
    statement.executeUpdate(sqlUpdate);
    closeConnection(connection);
    FoodRequest aFoodRequest =
        selectFoodRequests(requestID, null, null, null, null, null, null, null).get(0);
    aFoodRequest.setRequesterName(requesterName);
    aFoodRequest.setLocation(location);
    aFoodRequest.setMealType(mealType);
    aFoodRequest.setStaffMember(staffMember);
    aFoodRequest.setAdditionalNotes(additionalNotes);
    aFoodRequest.setRequestDate(requestDate);
    aFoodRequest.setRequestStatus(requestStatus);
  }

  public ArrayList<FoodRequest> selectFoodRequests(
      Integer requestID,
      String requesterName,
      String location,
      String mealType,
      String staffMember,
      String additionalNotes,
      Timestamp requestDate,
      RequestStatus requestStatus) {
    ArrayList<FoodRequest> aList = new ArrayList<FoodRequest>();
    for (FoodRequest foodRequest : foodRequests) {
      Boolean requestIDCheck = requestID == null || requestID.equals(foodRequest.getRequestID());
      Boolean requesterNameCheck =
              requesterName == null || requesterName.equals(foodRequest.getRequesterName());
      Boolean locationCheck =
              location == null || location.equals(foodRequest.getLocation());
      Boolean mealTypeCheck =
              mealType == null || mealType.equals(foodRequest.getMealType());
      Boolean staffMemberCheck =
              staffMember == null || staffMember.equals(foodRequest.getStaffMember());
      Boolean additionalNotesCheck =
              additionalNotes == null
                      || additionalNotes.equals(foodRequest.getAdditionalNotes());
      Boolean requestDateCheck =
              requestDate == null
                      || requestDate.toString().equals(foodRequest.getRequestDate().toString());
      Boolean requestStatusCheck =
              requestStatus == null
                      || requestStatus.toString().equals(foodRequest.getRequestStatus().toString());
      if (requestIDCheck
              && requesterNameCheck
              && locationCheck
              && mealTypeCheck
              && staffMemberCheck
              && additionalNotesCheck
              && requestDateCheck
              && requestStatusCheck) {
        aList.add(foodRequest);
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
