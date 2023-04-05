package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController implements IPageController {

  @FXML private MFXButton flowerDelivery;
  @FXML private MFXButton conferenceRoom;

  @FXML
  public void initialize() {
    flowerDelivery.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));
    conferenceRoom.setOnMouseClicked(event -> Navigation.navigate(Screen.CONFERENCE_REQUEST));
  }

  public void back() {
    Navigation.navigate(Screen.HOME);
  }
}
