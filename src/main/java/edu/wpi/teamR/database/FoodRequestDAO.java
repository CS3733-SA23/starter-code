package edu.wpi.teamR.database;

import java.util.ArrayList;

public class FoodRequestDAO {
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

    public FoodRequest addFoodRequest(String requesterName, String location, String mealType, String staffMember, String additionalNotes, )
}
