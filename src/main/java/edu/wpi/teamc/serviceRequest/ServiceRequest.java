package edu.wpi.teamc.serviceRequest;

public class ServiceRequest {

  private int requestId;
  private String selection;

  private enum status {
    PENDING,
    INPROGRESS,
    COMPLETE
  }

  public ServiceRequest(int id, String select) {
    this.requestId = id;
    this.selection = select;
  }

  /*
  This function is used to switch between different Request page.
  PS. maybe this function is useless since we have it in Controllers
  */
  public void request(String select) {
    switch (select) {
      case "Meal":
        // Open Meal Page;
        break;
      case "Booking":
        // Open Booking Room Page;
        break;
    }
  }
}
