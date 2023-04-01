package edu.wpi.romanticraijuu.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  MEAL_REQUEST("views/MealRequest.fxml"),
  SIGNAGE("views/Signage.fxml"),
  FURNITURE_REQUEST("views/FurnitureRequest.fxml"),
  HELP("views/Help.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
