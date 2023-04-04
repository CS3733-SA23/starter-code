package edu.wpi.teamc.serviceRequest;

public interface IServiceRequest {
  public void addRequest();

  public void updateRequest();

  public enum status {
    PENDING,
    INPROGRESS,
    COMPLETE
  }
}
