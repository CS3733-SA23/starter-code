package edu.wpi.teamc.serviceRequest;

public class ServiceRequest {
  public String
      username; // This is just a temporary User. We should change it into "user" type later
  public String selection;

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
