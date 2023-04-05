package edu.wpi.teame.entities;

import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class ServiceRequestData {
  public enum Status {
    PENDING,
    IN_PROGRESS,
    DONE;

    public static String statusToString(Status st) {
      switch (st) {
        case PENDING:
          return "PENDING";
        case IN_PROGRESS:
          return "IN_PROGRESS";
        case DONE:
          return "DONE";
        default:
          throw new NoSuchElementException("No such status found");
      }
    }

    public static Status stringToStatus(String st) {
      switch (st) {
        case "PENDING":
          return PENDING;
        case "IN_PROGRESS":
          return IN_PROGRESS;
        case "DONE":
          return DONE;
        default:
          throw new NoSuchElementException("No such status found");
      }
    }
  }

  public enum RequestType {
    MEALDELIVERY,
    FLOWERDELIVERY;

    public static RequestType stringToRequestType(String rt) {
      switch (rt) {
        case "MEALDELIVERY":
          return MEALDELIVERY;
        case "FLOWERDELIVERY":
          return FLOWERDELIVERY;
        default:
          throw new NoSuchElementException("No such service request found");
      }
    }

    public static String requestTypeToString(RequestType rt) {
      switch (rt) {
        case MEALDELIVERY:
          return "MEALDELIVERY";
        case FLOWERDELIVERY:
          return "FLOWERDELIVERY";
        default:
          throw new NoSuchElementException("No such service request found");
      }
    }
  }

  @Getter @Setter private JSONObject requestData;
  @Getter @Setter private RequestType requestType;
  @Getter @Setter private Status requestStatus;
  @Getter @Setter private String assignedStaff;

  public ServiceRequestData(
      RequestType requestType, JSONObject requestData, Status requestStatus, String assignedStaff) {
    this.requestData = requestData;
    this.requestType = requestType;
    this.requestStatus = requestStatus;
    this.assignedStaff = assignedStaff;
  }
}
