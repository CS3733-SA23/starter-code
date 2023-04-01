package edu.wpi.teamA.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  MAP("views/Map.fxml"),
  OFFICE_MOVES("views/OfficeMoves.fxml"),
  SIGNAGE("views/Signage.fxml"),
  PATHFINDING("views/Pathfinding.fxml"),
  HEADER("views/Header.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
