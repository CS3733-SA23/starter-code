package edu.wpi.teamc.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  SIGNAGE("views/SignagePage.fxml"),
  ELEVATOR_SIGNAGE("views/ElevatorSignage.fxml"),
  HOME("views/Home.fxml"),
  MEAL("views/Meal.fxml"),
  CONFERENCE("views/Conference.fxml"),
  FLOWER("views/Flower.fxml"),
  FURNITURE("views/Furniture.fxml"),
  OFFICE_SUPPLY("views/OfficeSupply.fxml"),
  CONGRATS_PAGE("views/congratsPage.fxml"),
  ;

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}