package edu.wpi.teame.controllers;

import org.json.JSONObject;

public class ServiceRequestData {

  enum RequestType {
    MEALDELIVERY,
    FLOWERDELIVERY,
  }

  JSONObject requestData;
  RequestType requestType;

  public ServiceRequestData(RequestType requestType, JSONObject requestData) {
    this.requestData = requestData;
    this.requestType = requestType;
  }
}
