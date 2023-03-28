package edu.wpi.teamA.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  MAP("views/Map.fxml"),
  OFFICE_MOVE("views/OfficeMove.fxml"),
  SIGNAGE("views/Signage.fxml"),
  ABOUT("views/About.fxml"),
  HELP("views/Help.fxml"),
  FLOWER_REQUEST("views/FlowerRequest.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
