package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {
  @FXML MFXButton signageButton2;

  @FXML private Button flowerDeliveryPage;

  @FXML private Button furnitureDeliveryPage;

  @FXML private Button helpPage;

  @FXML private Button mealDeliveryPage;

  @FXML private Button officeSuppliesPage;

  @FXML private Button roomReservationPage;

  @FXML private Button signagePage;

  @FXML
  void getFlowerDeliveryPage(ActionEvent event) {}

  @FXML
  void getFurnitureDeliveryPage(ActionEvent event) {}

  @FXML
  void getHelpPage(ActionEvent event) {}

  @FXML
  void getMealDeliveryPage(ActionEvent event) {}

  @FXML
  void getOfficeSuppliesPage(ActionEvent event) {}

  @FXML
  void getRoomReservationPage(ActionEvent event) {
    Navigation.navigate(Screen.CONFERENCE);
  }

  @FXML
  void getSignagePage(ActionEvent event) {}

  @FXML
  public void goToSignagePage() {
    signagePage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {
    mealDeliveryPage.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL));
  }

  @FXML
  public void goToSignagePage2() {
    signageButton2.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
  }
}
