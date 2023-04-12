package edu.wpi.teame.utilities;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/HomePage.fxml"),

  SERVICE_REQUESTS("views/ServiceRequestPage.fxml"),

  SIGNAGE_TEXT("views/SignagePage.fxml"),
  MEAL_REQUEST("views/MealRequest.fxml"),
  FLOWER_REQUEST("views/FlowerRequest.fxml"),
  OFFICE_SUPPLIES_REQUEST("views/OfficeSuppliesRequest.fxml"),
  MAP("views/Map.fxml"),
  DATABASE_VIEW("views/DatabaseEditor.fxml"),
  MAP_DATA_EDITOR("views/MapDataEditor.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
