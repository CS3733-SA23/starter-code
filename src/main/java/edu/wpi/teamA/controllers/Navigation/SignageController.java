package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;

public class SignageController implements IPageController {

  @Override
  public void initialize() {}

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }
}
