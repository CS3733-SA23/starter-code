package edu.wpi.teamname.system;

public enum Screen {
  ROOT("../views/Root.fxml"),
  HOME("../views/Home.fxml"),
  MAP("../views/Map.fxml"),
  SERVICE_REQUEST("../views/ServiceRequest.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
