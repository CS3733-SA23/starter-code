package edu.wpi.teame.controllers;

        import edu.wpi.teame.navigation.Navigation;
        import edu.wpi.teame.navigation.Screen;
        import io.github.palexdev.materialfx.controls.MFXButton;
        import javafx.fxml.FXML;

public class ServiceRequestPageController {

  @FXML MFXButton returnButtonService;
  @FXML MFXButton flowerRequest;
  @FXML MFXButton mealRequest;

  @FXML
  public void initialize() {
    flowerRequest.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));
    mealRequest.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
    returnButtonService.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}