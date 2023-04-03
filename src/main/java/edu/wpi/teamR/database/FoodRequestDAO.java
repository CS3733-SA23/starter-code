package edu.wpi.teamR.database;

import java.sql.*;
import java.util.ArrayList;

public class FoodRequestDAO { //TODO: make sure tablename is the view
    private static FoodRequestDAO instance;
    private ArrayList<FoodRequest> foodRequests;
    private String longName, username, password, tableName, schemaName, connectionURL;
    private FoodRequestDAO(String username, String password, String tableName, String schemaName, String connectionURL){
        this.username = username;
        this.password = password;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.connectionURL = connectionURL;
    }
    public static FoodRequestDAO createInstance(String username, String password, String tableName, String schemaName, String connectionURL) {
        if (FoodRequestDAO.instance == null)
            FoodRequestDAO.instance = new FoodRequestDAO(username, password, tableName, schemaName, connectionURL);
        return FoodRequestDAO.instance;
    }
    public static FoodRequestDAO getInstance(){return FoodRequestDAO.instance;}
    public ArrayList<FoodRequest> getFoodRequests(){return foodRequests;};
    public FoodRequest addFoodRequest(int requestID, String requesterName, String location, String mealType, String staffMember, String additionalNotes, Timestamp requestDate, RequestStatus requestStatus) throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        String sqlInsert = "INSERT INTO "+schemaName+"."+tableName+"(requestID, requesterName, location, requestDate, additionalNotes, mealType)";
        sqlInsert+= "VALUES("+requestID+",\'"+requesterName+"\',\'"+location+"\',\'"+requestDate+"\',\'"+additionalNotes+"\',\'"+mealType+"\');";
        FoodRequest aFoodRequest = new FoodRequest(requestID, requesterName, location, mealType, staffMember, additionalNotes, requestDate, requestStatus);
        foodRequests.add()
        closeConnection(connection);
    }
    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(connectionURL, username, password);
    }
    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
