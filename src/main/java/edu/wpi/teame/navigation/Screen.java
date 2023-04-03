package edu.wpi.teame.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  SIGNAGE_TEXT("views/Signage.fxml"),
  MEAL_REQUEST("views/MealRequest.fxml"),
  FLOWER_REQUEST("views/FlowerRequest.fxml"),
  GROUND_FLOOR("views/GroundFloorMapNav.fxml"),
  LOWERR_LEVEL_ONE("views/LoweLevelOneMapNav.fxml"),
  LOWERR_LEVEL_TWO("views/LowerLevelTwoMapNav.fxml"),
  FLOOR_ONE("views/FloorOneMapNav.fxml"),
  FLOOR_TWO("views/FloorTwoMapNav.fxml"),
  FLOOR_THREE("views/FloorThreeMapNav.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
