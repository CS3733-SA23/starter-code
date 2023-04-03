package edu.wpi.teame.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/HomePage.fxml"),

  SERVICE_REQUESTS("views/ServiceRequestPage.fxml"),

  SIGNAGE_TEXT("views/Signage.fxml"),
  MEAL_REQUEST("views/MealRequest.fxml"),
  FLOWER_REQUEST("views/FlowerRequest.fxml"),

  LOWER_ONE("views/LoweLevelOneMapNav.fxml"),
  LOWER_TWO("views/LowerLevelTwoMapNav.fxml"),
  FLOOR_ONE("views/FloorOneMapNav.fxml"),
  FLOOR_TWO("views/FloorTwoMapNav.fxml"),
  FLOOR_THREE("views/FloorThreeMapNav.fxml"),
  GROUND_FLOOR("views/GroundFloorMapForNavigation.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
