package edu.wpi.teame.entities;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class ServiceRequestData {
  public enum Status {
    PENDING,
    IN_PROGRESS,
    DONE
  }
  public enum RequestType {
    MEALDELIVERY,
    FLOWERDELIVERY,
  }

  @Getter @Setter private JSONObject requestData;
  @Getter @Setter private RequestType requestType;
  @Getter @Setter private Status requestStatus;
  @Getter @Setter private String assignedStaff;

  public ServiceRequestData(RequestType requestType, JSONObject requestData) {
    this.requestData = requestData;
    this.requestType = requestType;
    requestStatus = Status.PENDING;
    assignedStaff = "None";
  }

  public ServiceRequestData(RequestType requestType, JSONObject requestData, Status requestStatus, String assignedStaff) {
    this.requestData = requestData;
    this.requestType = requestType;
    this.requestStatus = requestStatus;
    this.assignedStaff = assignedStaff;
  }
}
