package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class MealController {
  @FXML private MFXButton MEAL_backtohome;
  @FXML private MFXButton MEAL_submit;
  @FXML private Label MEAL_outputtext;
  @FXML private MenuButton MEAL_Menu;
  @FXML private MenuItem MEAL_Default;
  @FXML private MenuItem MEAL_Keto;
  @FXML private MenuItem MEAL_Vegan;

  @FXML
  void MEAL_Choice0() {
    MEAL_Menu.setText("-Please Select Meal-");
  }

  @FXML
  void MEAL_Choice1() {
    MEAL_Menu.setText("Keto");
  }

  @FXML
  void MEAL_Choice2() {
    MEAL_Menu.setText("Vegan");

  @FXML
  public void goHome() {
    MEAL_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  /*@FXML
  void SubmitRequest() {
    MEAL_outputtext.setText("Submitted");
  }*/

  @FXML
  void SubmitRequest() {
    MEAL_submit.setOnMouseClicked(event -> Navigation.navigate(Screen.CONGRATS_PAGE));
  }
}
