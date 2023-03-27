package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {
  // @FXML MFXButton signageButton;

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
  void getRoomReservationPage(ActionEvent event) {}

  @FXML
  void getSignagePage(ActionEvent event) {}

  @FXML
  public void goToSignagePage() {
    signagePage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
  }

  /** Method run when controller is initialized */
  @FXML
  public void initialize() {
    roomReservationPage.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
  }
}
