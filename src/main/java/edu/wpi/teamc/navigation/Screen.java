package edu.wpi.teamc.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
<<<<<<< HEAD
  HOME("views/Home.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  SIGNAGE_PAGE("views/SignagePage.fxml"),
  ELEVATOR_SIGNAGE("views/ElevatorSignage.fxml");
=======
  HOME("views/HomeUI.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml");
>>>>>>> ae5af00f4b68ad609beb52daebe0bc6cd3652b18

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
