package edu.wpi.teamA.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  MAP("views/Map.fxml"),
  SIGNAGE("views/Signage.fxml"),
  PATHFINDING("views/Pathfinding.fxml"),
  HEADER("views/Header.fxml"),
  FLOWER_REQUEST("views/FlowerRequest.fxml"),
  CONFERENCE_REQUEST("views/ConferenceRequest.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
