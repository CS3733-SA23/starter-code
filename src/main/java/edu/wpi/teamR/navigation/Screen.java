package edu.wpi.teamR.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  MEAL_REQUEST("views/MealRequest.fxml"),
  SIGNAGE("views/Signage.fxml"),
  FURNITURE_REQUEST("views/FurnitureRequest.fxml"),
  HELP("views/Help.fxml"),
  SORT("views/sortOrders.fxml"),
  UPDATE_NODES("views/UpdateNodes.fxml"),
  READCSV("views/CSVReader.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
