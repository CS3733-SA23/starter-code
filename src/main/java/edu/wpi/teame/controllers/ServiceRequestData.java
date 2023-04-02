package edu.wpi.teame.controllers;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class ServiceRequestData {
  enum Status {
    PENDING,
    IN_PROGRESS,
    DONE
  }
  enum RequestType {
    MEALDELIVERY,
    FLOWERDELIVERY,
  }

  @Getter @Setter JSONObject requestData;
  @Getter @Setter RequestType requestType;
  @Getter @Setter Status requestStatus;
  @Getter @Setter String assignedStaff;

  public ServiceRequestData(RequestType requestType, JSONObject requestData) {
    this.requestData = requestData;
    this.requestType = requestType;
    requestStatus = Status.PENDING;
    assignedStaff = "None";
  }

  public ServiceRequestData(RequestType requestType, JSONObject requestData, Status currentStatus, String assignedStaff) {
    this.requestData = requestData;
    this.requestType = requestType;
    requestStatus = Status.PENDING;
    assignedStaff = "None";
  }
}
