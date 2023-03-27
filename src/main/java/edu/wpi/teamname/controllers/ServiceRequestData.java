package edu.wpi.teamname.controllers;
import org.json.JSONObject;
public class ServiceRequestData {

     enum RequestType {
        MEALDELIVERY,
        FLOWERDELIVERY,
    }
    private JSONObject requestData;
    private RequestType requestType;

    public ServiceRequestData(RequestType requestType, JSONObject requestData) {
        this.requestData = requestData;
        this.requestType = requestType;
    }

}
