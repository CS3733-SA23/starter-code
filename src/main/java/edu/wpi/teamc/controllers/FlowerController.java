package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class FlowerController {
  @FXML private MFXButton goHome;
  @FXML private MFXButton submit;

  @FXML private MenuItem chocie0;

  @FXML private MenuItem chocie1;

  @FXML private MenuItem chocie2;

  @FXML private MenuItem chocie3;

  @FXML private MenuItem choice4;

  @FXML private MenuButton menuButton;

  @FXML
  void getChoice0() {
    menuButton.setText("--Please Select Bouquet Option--");
  }

  @FXML
  void getChoice1() {
    menuButton.setText("Roses");
  }

  @FXML
  void getChoice2() {
    menuButton.setText("White Roses");
  }

  @FXML
  void getChoice3() {
    menuButton.setText("Random Bouquet");
  }

  @FXML
  void getChoice4() {
    menuButton.setText("Ian's Holiday Special");
  }

  @FXML
  public void getGoHome() {
    goHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void getClear() {}

  @FXML
  void getSubmit() {
    submit.setOnMouseClicked(event -> Navigation.navigate(Screen.CONGRATS_PAGE));
  }
}
