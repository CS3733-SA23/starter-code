package edu.wpi.teamc.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  SIGNAGE_PAGE("views/SignagePage.fxml"),
  ELEVATOR_SIGNAGE("views/ElevatorSignage.fxml"),
  HOME("views/HomeUI.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
