package edu.wpi.teamc.serviceRequest;

public interface IServiceRequest {
  public enum STATUS {
    PENDING,
    INPROGRESS,
    COMPLETE;
  }

  public enum TYPE {
    MEAL,
    CONFERENCEROOM
  }
}
