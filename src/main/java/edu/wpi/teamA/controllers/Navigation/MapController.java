package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import javafx.fxml.FXML;

public class MapController implements IPageController {

  @FXML
  public void initialize() {}

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }
}
