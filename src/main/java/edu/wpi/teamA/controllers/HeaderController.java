package edu.wpi.teamA.controllers;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class HeaderController {

  @FXML MFXButton backButton;
  @FXML MenuButton menuButton;
  @FXML MenuItem serviceRequestMenu;
  @FXML MenuItem mapMenu;
  @FXML MenuItem officeMovesMenu;
  @FXML MenuItem signageMenu;
  @FXML Text title;
  @FXML ImageView bwhLogo;
  @FXML Circle profile;

  @FXML
  public void initialize() {
    // setPageTitle("New page title");
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    // exitButton.setOnMouseClicked(event -> App.stop());
    serviceRequestMenu.setOnAction(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    mapMenu.setOnAction(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    officeMovesMenu.setOnAction(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    signageMenu.setOnAction(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    bwhLogo.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    // profile.setOnMouseClicked();
  }

  public void setPageTitle(String newTitle) {
    title.setText(newTitle);
  }
}
